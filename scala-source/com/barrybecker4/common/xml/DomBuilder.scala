/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.w3c.dom.Document

import java.util.logging.{Level, Logger}
import javax.xml.parsers.{DocumentBuilderFactory, ParserConfigurationException}

/** Building and creating new DOM documents. */
object DomBuilder {
  private val ROOT_ELEMENT = "rootElement"

  /** Initialize a dom document structure.
    * @return dom Document
    */
  def buildDom: Document = {
    val document = createNewDocument.getOrElse(
      throw new IllegalStateException("Could not create XML document"))
    val root = document.createElement(ROOT_ELEMENT)
    document.appendChild(root)
    document.getDocumentElement.normalize()
    document
  }

  /** @return a new empty document, or None if there was an error creating one */
  def createNewDocument: Option[Document] =
    try {
      val documentBuilderFactory = DocumentBuilderFactory.newInstance
      val documentBuilder = documentBuilderFactory.newDocumentBuilder
      Some(documentBuilder.newDocument)
    } catch {
      case ex: ParserConfigurationException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
        None
    }
}
