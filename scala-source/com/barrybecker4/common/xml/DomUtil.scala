/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.w3c.dom.{Document, NamedNodeMap, Node}

import java.io.File
import java.net.URL

/**
  * Convenience facade for common DOM operations. See [[DomBuilder]], [[DomParser]], [[DomQuery]],
  * [[DomTransformer]], and [[DomWriter]] for focused APIs.
  */
object DomUtil {

  def buildDom: Document = DomBuilder.buildDom

  def createNewDocument: Option[Document] = DomBuilder.createNewDocument

  def getAttribute(node: Node, attribName: String): String =
    DomQuery.getAttribute(node, attribName)

  def getAttribute(node: Node, attribName: String, defaultValue: String): String =
    DomQuery.getAttribute(node, attribName, defaultValue)

  def getAttributeList(attributeMap: NamedNodeMap): String =
    DomQuery.getAttributeList(attributeMap)

  def asString(root: Node, level: Int): String =
    DomQuery.asString(root, level)

  def parseXML(url: URL): Document =
    DomParser.parseXML(url)

  def parseXMLFile(file: File): Option[Document] =
    DomParser.parseXMLFile(file)

  def writeXMLFile(destinationFileName: String, document: Document, schema: String): Unit =
    DomWriter.writeXMLFile(destinationFileName, document, schema)
}
