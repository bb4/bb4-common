/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform._
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.io._
import java.net.URL
import org.w3c.dom._
import org.xml.sax.{SAXException, SAXParseException}

import java.util.logging.Level
import java.util.logging.Logger
import scala.collection.mutable.ListBuffer


/**
  * Static utility methods for manipulating an XML dom.
  * @author Barry Becker
  */
object DomUtil {
  /** This URL is where I keep all my published xsd's (xml schemas) and dtd's (doc type definitions) */
  private val SCHEMA_LOCATION = "http://barrybecker4.com/bb4-projects/schema/"

  private val ROOT_ELEMENT = "rootElement"
  private val USE_ELEMENT = "use"

  /** Initialize a dom document structure.
    * @return dom Document
    */
  def buildDom: Document = {
    var document: Document = null
    val factory = DocumentBuilderFactory.newInstance
    try {
      val builder = factory.newDocumentBuilder
      document = builder.newDocument // Create from whole cloth

      val root = document.createElement(ROOT_ELEMENT)
      document.appendChild(root)
      // normalize text representation
      // getDocumentElement() returns the document's root node
      document.getDocumentElement.normalize()
    } catch {
      case pce: ParserConfigurationException =>
        // Parser with specified options can't be built
        pce.printStackTrace()
    }
    document
  }

  /** @return a new document (or null if there was an error creating one) */
  def createNewDocument: Document = {
    val documentBuilderFactory = DocumentBuilderFactory.newInstance
    var documentBuilder: DocumentBuilder = null
    try {
      documentBuilder = documentBuilderFactory.newDocumentBuilder
      return documentBuilder.newDocument
    } catch {
      case ex: ParserConfigurationException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
    null
  }

  /** Go through the dom hierarchy and remove spurious text nodes and also
    * replace "use" nodes with a deep copy of what they refer to.
    * @param root     root of document
    * @param document the xml document
    */
  private def postProcessDocument(root: Node, document: Document, replaceUseWithDeepCopy: Boolean): Unit = {
    val l = root.getChildNodes
    val deleteList = ListBuffer[Node]()
    var i = 0
    while (i < l.getLength) {
      val n = l.item(i)
      val name = n.getNodeName
      if (name != null && name.startsWith("#text")) { // delete if nothing by whitespace
        val text = n.getNodeValue
        if (text.matches("[ \\t\n\\x0B\\f\\r]*"))
          deleteList.append(n)
      }
      postProcessDocument(n, document, replaceUseWithDeepCopy)
      if (name != null && USE_ELEMENT == name) { // substitute the element with the specified id
        val attrs = n.getAttributes
        val attr = attrs.item(0)
        assert("ref" == attr.getNodeName, "attr name=" + attr.getNodeName)
        val attrValue = attr.getNodeValue
        val element = document.getElementById(attrValue)
        val clonedElement = element.cloneNode(replaceUseWithDeepCopy)
        // Still need to recursively clean the node that was replaced
        // since it might also contain use nodes.
        postProcessDocument(clonedElement, document, replaceUseWithDeepCopy)
        root.replaceChild(clonedElement, n)
      }
      i += 1
    }
    deleteList.foreach(c => root.removeChild(c))
  }

  /** Get the value for an attribute.
    * Error if the attribute does not exist.
    * @param node       node to get attribute from
    * @param attribName attribute to get
    */
  def getAttribute(node: Node, attribName: String): String = {
    val attributeVal = getAttribute(node, attribName, null)
    assert(attributeVal != null,
      s"no attribute named '$attribName' for node '${node.getNodeName}' val='${node.getNodeValue}'")
    attributeVal
  }

  /** Get the value for an attribute. If not found, defaultValue is used.
    * @param node         node to get attribute on
    * @param attribName   name of attribute to get
    * @param defaultValue the default to use if requested attribute not there
    */
  def getAttribute(node: Node, attribName: String, defaultValue: String): String = {
    val attribMap = node.getAttributes
    var attributeVal: String = null
    if (attribMap == null) return null
    var i = 0
    while (i < attribMap.getLength) {
      val attr = attribMap.item(i)
      if (attr.getNodeName == attribName) attributeVal = attr.getNodeValue
      i += 1
    }
    if (attributeVal == null) attributeVal = defaultValue
    attributeVal
  }

  /** A concatenated list of the node's attributes.
    * @param attributeMap maps names to nodes
    * @return list of attributes
    */
  def getAttributeList(attributeMap: NamedNodeMap): String = {
    var attribs = ""
    if (attributeMap != null) {
      attributeMap.getLength
      var i = 0
      while (i < attributeMap.getLength) {
        val n = attributeMap.item(i)
        attribs += n.getNodeName + "=\"" + n.getNodeValue + "\"  "
        i += 1
      }
    }
    attribs
  }

  /** Create a String representation of the dom hierarchy.
    * @param root  document root node
    * @param level level to print to
    * @return the DOM formatted as a string
    */
  def asString(root: Node, level: Int): String = {
    val l = root.getChildNodes
    var result = ""
    for (i <- 0 until level) result += "    "

    val attribMap = root.getAttributes
    val attribs = getAttributeList(attribMap)
    result += "Node: <" + root.getNodeName + ">  " + attribs + "\n"
    for (i <- 0 until l.getLength)
      result += asString(l.item(i), level + 1)
    result
  }

  /** Parse an xml file and return a cleaned up Document object.
    * Set replaceUseWithDeepCopy to false if you are in a debug mode and don't want to see a lot of redundant subtrees.
    * @param stream some input stream.
    * @param replaceUseWithDeepCopy if true then replace each use with a deep copy of what it refers to
    * @param xsdUri location of the schema to use for validation.
    * @return the parsed file as a Document
    */
  private def parseXML(stream: InputStream, replaceUseWithDeepCopy: Boolean, xsdUri: String) = {
    var document: Document = null
    val factory = DocumentBuilderFactory.newInstance
    factory.setIgnoringComments(true)
    try {
      factory.setNamespaceAware(true)
      factory.setValidating(false) //was true, but does not work with xsd
      if (xsdUri != null) {
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
          "http://www.w3.org/2001/XMLSchema")
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", xsdUri)
      }
      val builder = factory.newDocumentBuilder
      builder.setErrorHandler(new XmlErrorHandler)
      document = builder.parse(stream)
      postProcessDocument(document, document, replaceUseWithDeepCopy)
      //printTree(document, 0);
    } catch {
      case sxe: SAXException =>
        // Error generated during parsing)
        var x: Exception = sxe
        if (sxe.getException != null) x = sxe.getException
        //x.printStackTrace()
      case pce@(_: ParserConfigurationException | _: IOException) =>
        pce.printStackTrace()
    }
    document
  }

  // for debugging
  private def printInputStream(iStream: InputStream): Unit = {
    import java.io.BufferedReader
    val in = new BufferedReader(new InputStreamReader(iStream))
    var line = in.readLine()
    println("-----  <start> -----")
    while (line != null) {
      println(line)
      line = in.readLine()
    }
    println("-----  <end> -----")
  }

  /** @param url url that points to the xml document to parse
    * @return parsed Document
    */
  def parseXML(url: URL): Document = try {
    val urlc = url.openConnection
    val is = urlc.getInputStream
    parseXML(is, replaceUseWithDeepCopy = true, null)
  } catch {
    case e: IOException => throw new IllegalArgumentException("Failed to open " + url.getPath, e)
  }

  /** @param file the file to parse
    * @return parsed Document
    */
  def parseXMLFile(file: File): Document = parseXMLFile(file, replaceUseWithDeepCopy = true)

  /** @param file   the file to parse
    * @param replaceUseWithDeepCopy if true, replace element references with deep copies.
    * @return the xml document DOM object
    */
  def parseXMLFile(file: File, replaceUseWithDeepCopy: Boolean): Document = {
    try {
      val str = new FileInputStream(file)
      return parseXML(str, replaceUseWithDeepCopy, null)
    } catch {
      case e: FileNotFoundException =>
        e.printStackTrace()
    }
    null
  }

  /** Write out the xml document to a file.
    * @param destinationFileName file to write xml to
    * @param document            xml document to write.
    * @param schema              of the schema to use if any (e.g. script.dtd of games.xsd). May be null.
    */
  def writeXMLFile(destinationFileName: String, document: Document, schema: String): Unit = {
    var output: OutputStream = null
    try {
      output = new BufferedOutputStream(new FileOutputStream(destinationFileName))
      writeXML(output, document, schema)
    } catch {
      case ex: FileNotFoundException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
  }

  /** @param oStream  stream to write xml to.
    * @param document the xml document to be written to the specified output stream
    * @param schema   of the schema to use if any (e.g. script.dtd of games.xsd). May be null.
    */
  private def writeXML(oStream: OutputStream, document: Document, schema: String): Unit = {
    val transformerFactory = TransformerFactory.newInstance
    var transformer: Transformer = null
    try {
      transformer = transformerFactory.newTransformer
      transformer.setOutputProperty(OutputKeys.INDENT, "yes")
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
      if (schema != null) transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, DomUtil.SCHEMA_LOCATION + schema)
    } catch {
      case ex: TransformerConfigurationException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
    val source = new DOMSource(document)
    // takes some OutputStream or Writer
    val result = new StreamResult(oStream) // replace out with FileOutputStream
    assert(transformer != null)
    try // replace out with FileOutputStream  // System.out
      transformer.transform(source, result)
    catch {
      case ex: TransformerException => Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
  }
}
