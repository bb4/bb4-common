/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.w3c.dom.*
import org.xml.sax.{InputSource, SAXException}

import java.io.*
import java.net.URL
import java.util.logging.{Level, Logger}
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory, ParserConfigurationException}
import javax.xml.transform.*
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
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
      document.getDocumentElement.normalize()
    } catch {
      case pce: ParserConfigurationException =>
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

  private def isWhitespaceOnlyTextNode(n: Node): Boolean = {
    val name = n.getNodeName
    name != null && name.startsWith("#text") && {
      val text = n.getNodeValue
      text != null && text.matches("[ \\t\n\\x0B\\f\\r]*")
    }
  }

  /** Go through the dom hierarchy and remove spurious text nodes and also
    * replace "use" nodes with a deep copy of what they refer to.
    */
  private def postProcessDocument(root: Node, document: Document, replaceUseWithDeepCopy: Boolean): Unit = {
    val l = root.getChildNodes
    val deleteList = ListBuffer[Node]()
    var i = 0
    while (i < l.getLength) {
      val n = l.item(i)
      val name = n.getNodeName
      if (isWhitespaceOnlyTextNode(n))
        deleteList.append(n)
      postProcessDocument(n, document, replaceUseWithDeepCopy)
      if (name != null && USE_ELEMENT == name)
        substituteUseElement(root, n, document, replaceUseWithDeepCopy)
      i += 1
    }
    deleteList.foreach(c => root.removeChild(c))
  }

  private def substituteUseElement(
      root: Node,
      n: Node,
      document: Document,
      replaceUseWithDeepCopy: Boolean): Unit = {
    val attrs = n.getAttributes
    val attr = attrs.item(0)
    assert("ref" == attr.getNodeName, "attr name=" + attr.getNodeName)
    val attrValue = attr.getNodeValue
    val element = findElementById(document, attrValue)
    if (element == null)
      throw new IllegalStateException(
        "No element with id '" + attrValue + "' for <use> reference")
    val clonedElement = element.cloneNode(replaceUseWithDeepCopy)
    postProcessDocument(clonedElement, document, replaceUseWithDeepCopy)
    root.replaceChild(clonedElement, n)
  }

  /**
    * Resolve an id to an element. Prefers Document.getElementById when a DTD/schema
    * typed the attribute as ID; otherwise walks the tree matching an "id" attribute.
    * Needed because we disable loading external DTDs (see newDocumentBuilder).
    */
  private def findElementById(document: Document, id: String): Element = {
    val typed = document.getElementById(id)
    if (typed != null) typed
    else findElementByIdAttribute(document.getDocumentElement, id)
  }

  private def findElementByIdAttribute(node: Node, id: String): Element = {
    if (node == null) return null
    if (node.getNodeType == Node.ELEMENT_NODE) {
      val element = node.asInstanceOf[Element]
      if (id == element.getAttribute("id")) return element
    }
    val children = node.getChildNodes
    var i = 0
    while (i < children.getLength) {
      val found = findElementByIdAttribute(children.item(i), id)
      if (found != null) return found
      i += 1
    }
    null
  }

  /** Get the value for an attribute.
    * Error if the attribute does not exist.
    */
  def getAttribute(node: Node, attribName: String): String = {
    val attributeVal = getAttribute(node, attribName, null)
    assert(attributeVal != null,
      s"no attribute named '$attribName' for node '${node.getNodeName}' val='${node.getNodeValue}'")
    attributeVal
  }

  /** Get the value for an attribute. If not found, defaultValue is used. */
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

  /** A concatenated list of the node's attributes. */
  def getAttributeList(attributeMap: NamedNodeMap): String = {
    var attribs = ""
    if (attributeMap != null) {
      var i = 0
      while (i < attributeMap.getLength) {
        val n = attributeMap.item(i)
        attribs += n.getNodeName + "=\"" + n.getNodeValue + "\"  "
        i += 1
      }
    }
    attribs
  }

  private def indentPrefix(level: Int): String = {
    val b = new StringBuilder
    for (_ <- 0 until level) b.append("    ")
    b.toString
  }

  /** Create a String representation of the dom hierarchy. */
  def asString(root: Node, level: Int): String = {
    val l = root.getChildNodes
    var result = indentPrefix(level)
    val attribMap = root.getAttributes
    val attribs = getAttributeList(attribMap)
    result += "Node: <" + root.getNodeName + ">  " + attribs + "\n"
    for (i <- 0 until l.getLength)
      result += asString(l.item(i), level + 1)
    result
  }

  private def newDocumentBuilder(xsdUri: String): DocumentBuilder = {
    val factory = DocumentBuilderFactory.newInstance
    factory.setIgnoringComments(true)
    factory.setNamespaceAware(true)
    factory.setValidating(false)
    // Otherwise the parser still fetches external DTDs (DOCTYPE SYSTEM) and mis-resolves relative URIs
    // from bare InputStreams or jar: URLs — see JDK/Xerces nonvalidating/load-external-dtd.
    try {
      factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    } catch {
      case _: ParserConfigurationException => ()
    }
    if (xsdUri != null) {
      factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
        "http://www.w3.org/2001/XMLSchema")
      factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", xsdUri)
    }
    factory.newDocumentBuilder
  }

  private def parseXML(inputSource: InputSource, replaceUseWithDeepCopy: Boolean, xsdUri: String): Document = {
    val builder =
      try newDocumentBuilder(xsdUri)
      catch {
        case pce: ParserConfigurationException =>
          throw new IllegalStateException("XML parser configuration failed", pce)
      }
    builder.setErrorHandler(new XmlErrorHandler)
    val document =
      try builder.parse(inputSource)
      catch {
        case sxe: SAXException =>
          val cause = if (sxe.getException != null) sxe.getException else sxe
          throw new IllegalStateException("XML parse failed", cause)
        case ioe: IOException =>
          throw new IllegalStateException("XML read failed", ioe)
      }
    postProcessDocument(document, document, replaceUseWithDeepCopy)
    document
  }

  /** @param url url that points to the xml document to parse
    * @return parsed Document
    */
  def parseXML(url: URL): Document = try {
    val urlc = url.openConnection
    val is = urlc.getInputStream
    val inputSource = new InputSource(is)
    inputSource.setSystemId(url.toExternalForm)
    parseXML(inputSource, replaceUseWithDeepCopy = true, null)
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
  private def parseXMLFile(file: File, replaceUseWithDeepCopy: Boolean): Document = {
    try {
      val str = new FileInputStream(file)
      val inputSource = new InputSource(str)
      inputSource.setSystemId(file.toURI.toString)
      return parseXML(inputSource, replaceUseWithDeepCopy, null)
    } catch {
      case e: FileNotFoundException =>
        e.printStackTrace()
    }
    null
  }

  /** Write out the xml document to a file. */
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

  private def newTransformer(schema: String): Transformer = {
    val transformerFactory = TransformerFactory.newInstance
    try {
      val transformer = transformerFactory.newTransformer
      transformer.setOutputProperty(OutputKeys.INDENT, "yes")
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
      if (schema != null) transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, DomUtil.SCHEMA_LOCATION + schema)
      transformer
    } catch {
      case ex: TransformerConfigurationException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
        throw ex
    }
  }

  private def writeXML(oStream: OutputStream, document: Document, schema: String): Unit = {
    val transformer = newTransformer(schema)
    val source = new DOMSource(document)
    val result = new StreamResult(oStream)
    try transformer.transform(source, result)
    catch {
      case ex: TransformerException => Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
  }
}
