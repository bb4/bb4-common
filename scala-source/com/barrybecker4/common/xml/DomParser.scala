/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.w3c.dom.Document
import org.xml.sax.{InputSource, SAXException}

import java.io.{File, FileInputStream, FileNotFoundException, IOException}
import java.net.URL
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory, ParserConfigurationException}
import scala.util.Using

/** Parsing XML from URLs, files, and streams. */
object DomParser {

  private def newDocumentBuilder(xsdUri: Option[String]): DocumentBuilder = {
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
    xsdUri.foreach { uri =>
      factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
        "http://www.w3.org/2001/XMLSchema")
      factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", uri)
    }
    factory.newDocumentBuilder
  }

  private def parseXML(inputSource: InputSource, replaceUseWithDeepCopy: Boolean, xsdUri: Option[String]): Document = {
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
          val cause = Option(sxe.getException).getOrElse(sxe)
          throw new IllegalStateException("XML parse failed", cause)
        case ioe: IOException =>
          throw new IllegalStateException("XML read failed", ioe)
      }
    DomTransformer.postProcessDocument(document, document, replaceUseWithDeepCopy)
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
    parseXML(inputSource, replaceUseWithDeepCopy = true, None)
  } catch {
    case e: IOException => throw new IllegalArgumentException("Failed to open " + url.getPath, e)
  }

  /** @param file the file to parse */
  def parseXMLFile(file: File): Option[Document] = parseXMLFile(file, replaceUseWithDeepCopy = true)

  /** @param file   the file to parse
    * @param replaceUseWithDeepCopy if true, replace element references with deep copies.
    */
  private def parseXMLFile(file: File, replaceUseWithDeepCopy: Boolean): Option[Document] =
    try
      Some(
        Using.resource(new FileInputStream(file)) { str =>
          val inputSource = new InputSource(str)
          inputSource.setSystemId(file.toURI.toString)
          parseXML(inputSource, replaceUseWithDeepCopy, None)
        }
      )
    catch {
      case _: FileNotFoundException => None
    }
}
