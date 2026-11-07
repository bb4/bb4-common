/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.xml.sax.{ErrorHandler, SAXException, SAXParseException}

/**
  * Exception to use for XML related problems.
  */
@SuppressWarnings(Array("HardCodedStringLiteral"))
object XmlErrorHandler {
  private def logException(theType: String, exception: SAXParseException): Unit = {
    println(theType + " parsing at line " + exception.getLineNumber + " column " + exception.getColumnNumber)
    exception.printStackTrace()
  }
}

class XmlErrorHandler extends ErrorHandler {

  @throws[SAXException]
  override def warning(exception: SAXParseException): Unit =
    XmlErrorHandler.logException("Warning", exception)

  @throws[SAXException]
  override def error(exception: SAXParseException): Unit = {
    XmlErrorHandler.logException("Error", exception)
    throw exception
  }

  @throws[SAXException]
  override def fatalError(exception: SAXParseException): Unit = {
    XmlErrorHandler.logException("Fatal Error while", exception)
    throw exception
  }
}
