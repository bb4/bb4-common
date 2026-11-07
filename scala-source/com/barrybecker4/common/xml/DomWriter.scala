/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.w3c.dom.Document

import java.io.{BufferedOutputStream, FileNotFoundException, FileOutputStream, OutputStream}
import java.util.logging.{Level, Logger}
import javax.xml.transform._
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import scala.util.Using

/** Serializing DOM documents to XML. */
object DomWriter {
  /** This URL is where I keep all my published xsd's (xml schemas) and dtd's (doc type definitions) */
  private val SCHEMA_LOCATION = "http://barrybecker4.com/bb4-projects/schema/"

  /** Write out the xml document to a file. */
  def writeXMLFile(destinationFileName: String, document: Document, schema: String): Unit = {
    try {
      Using.resource(new BufferedOutputStream(new FileOutputStream(destinationFileName))) { output =>
        writeXML(output, document, Option(schema).filter(_.nonEmpty))
      }
    } catch {
      case ex: FileNotFoundException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
  }

  private def newTransformer(schema: Option[String]): Transformer = {
    val transformerFactory = TransformerFactory.newInstance
    try {
      val transformer = transformerFactory.newTransformer
      transformer.setOutputProperty(OutputKeys.INDENT, "yes")
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
      schema.foreach(s => transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, SCHEMA_LOCATION + s))
      transformer
    } catch {
      case ex: TransformerConfigurationException =>
        Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
        throw ex
    }
  }

  private def writeXML(oStream: OutputStream, document: Document, schema: Option[String]): Unit = {
    val transformer = newTransformer(schema)
    val source = new DOMSource(document)
    val result = new StreamResult(oStream)
    try transformer.transform(source, result)
    catch {
      case ex: TransformerException => Logger.getLogger(getClass.getName).log(Level.SEVERE, null, ex)
    }
  }
}
