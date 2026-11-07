/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import com.barrybecker4.common.app.AppContext
import org.xml.sax.{ErrorHandler, SAXException, SAXParseException}

/**
  * Exception to use for XML related problems.
  */
object XmlErrorHandler {
  private def logException(theType: String, exception: SAXParseException): Unit = {
    // Pass numbers as strings so MessageFormat does not insert locale grouping separators.
    val line = String.valueOf(exception.getLineNumber)
    val column = String.valueOf(exception.getColumnNumber)
    println(AppContext.getLabel("XML_PARSE_LOG", Array(theType, line, column)))
    exception.printStackTrace()
  }
}

class XmlErrorHandler extends ErrorHandler {

  @throws[SAXException]
  override def warning(exception: SAXParseException): Unit =
    XmlErrorHandler.logException(AppContext.getLabel("XML_SAX_WARNING"), exception)

  @throws[SAXException]
  override def error(exception: SAXParseException): Unit = {
    XmlErrorHandler.logException(AppContext.getLabel("XML_SAX_ERROR"), exception)
    throw exception
  }

  @throws[SAXException]
  override def fatalError(exception: SAXParseException): Unit = {
    XmlErrorHandler.logException(AppContext.getLabel("XML_SAX_FATAL"), exception)
    throw exception
  }
}
