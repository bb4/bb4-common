/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.xml.sax.{ErrorHandler, SAXException, SAXParseException}

/**
  * Exception to use for XML related problems.
  * @author Barry Becker
  */
@SuppressWarnings(Array("HardCodedStringLiteral"))
object XmlErrorHandler {
  private def handleException(theType: String, exception: SAXParseException): Unit = {
    println(theType + " parsing at line " + exception.getLineNumber + " column " + exception.getColumnNumber)
    exception.printStackTrace()
  }
}

class XmlErrorHandler extends ErrorHandler {

  @throws[SAXException]
  override def warning(exception: SAXParseException): Unit =
    XmlErrorHandler.handleException("Warning", exception)

  @throws[SAXException]
  override def error(exception: SAXParseException): Unit =
    XmlErrorHandler.handleException("Error", exception)

  @throws[SAXException]
  override def fatalError(exception: SAXParseException): Unit =
    XmlErrorHandler.handleException("Fatal Error while", exception)
}
