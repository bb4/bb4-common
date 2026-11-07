/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.scalatest.funsuite.AnyFunSuite
import org.xml.sax.SAXParseException

import java.io.{ByteArrayOutputStream, PrintStream}


class XmlErrorHandlerSuite extends AnyFunSuite {

  private def captureOutput(testCode: => Unit): String = {
    val outputStream = new ByteArrayOutputStream()
    val printStream = new PrintStream(outputStream)
    val originalOut = System.out
    try {
      System.setOut(printStream)
      testCode
      printStream.flush()
      outputStream.toString("UTF-8")
    } finally {
      System.setOut(originalOut)
    }
  }

  private def captureStderr(testCode: => Unit): String = {
    val errorStream = new ByteArrayOutputStream()
    val printStream = new PrintStream(errorStream)
    val originalErr = System.err
    try {
      System.setErr(printStream)
      testCode
      printStream.flush()
      errorStream.toString("UTF-8")
    } finally {
      System.setErr(originalErr)
    }
  }

  private def captureBoth(testCode: => Unit): (String, String) = {
    val outStream = new ByteArrayOutputStream()
    val errStream = new ByteArrayOutputStream()
    val outPrint = new PrintStream(outStream)
    val errPrint = new PrintStream(errStream)
    val originalOut = System.out
    val originalErr = System.err
    try {
      System.setOut(outPrint)
      System.setErr(errPrint)
      testCode
      outPrint.flush()
      errPrint.flush()
      (outStream.toString("UTF-8"), errStream.toString("UTF-8"))
    } finally {
      System.setOut(originalOut)
      System.setErr(originalErr)
    }
  }

  private def createException(message: String, lineNumber: Int, columnNumber: Int): SAXParseException = {
    new SAXParseException(message, null, null, lineNumber, columnNumber)
  }

  test("warning prints warning message") {
    val handler = new XmlErrorHandler
    val exception = createException("Test warning", 10, 5)
    
    val output = captureOutput {
      handler.warning(exception)
    }
    
    assert(output.contains("Warning"))
    assert(output.contains("line 10"))
    assert(output.contains("column 5"))
  }

  test("warning includes stack trace on stderr") {
    val handler = new XmlErrorHandler
    val exception = createException("Test warning", 10, 5)
    
    val (stdout, stderr) = captureBoth {
      handler.warning(exception)
    }
    
    assert(stderr.contains("org.xml.sax.SAXParseException"))
  }

  test("error prints error message") {
    val handler = new XmlErrorHandler
    val exception = createException("Test error", 20, 15)
    
    val output = captureOutput {
      handler.error(exception)
    }
    
    assert(output.contains("Error"))
    assert(output.contains("line 20"))
    assert(output.contains("column 15"))
  }

  test("error includes stack trace on stderr") {
    val handler = new XmlErrorHandler
    val exception = createException("Test error", 20, 15)
    
    val (stdout, stderr) = captureBoth {
      handler.error(exception)
    }
    
    assert(stderr.contains("org.xml.sax.SAXParseException"))
  }

  test("fatalError prints fatal error message") {
    val handler = new XmlErrorHandler
    val exception = createException("Test fatal error", 30, 25)
    
    val output = captureOutput {
      handler.fatalError(exception)
    }
    
    assert(output.contains("Fatal Error while"))
    assert(output.contains("line 30"))
    assert(output.contains("column 25"))
  }

  test("fatalError includes stack trace on stderr") {
    val handler = new XmlErrorHandler
    val exception = createException("Test fatal error", 30, 25)
    
    val (stdout, stderr) = captureBoth {
      handler.fatalError(exception)
    }
    
    assert(stderr.contains("org.xml.sax.SAXParseException"))
  }

  test("warning does not throw exception") {
    val handler = new XmlErrorHandler
    val exception = createException("Test warning", 10, 5)
    
    captureOutput {
      handler.warning(exception)
    }
  }

  test("error does not throw exception") {
    val handler = new XmlErrorHandler
    val exception = createException("Test error", 20, 15)
    
    captureOutput {
      handler.error(exception)
    }
  }

  test("fatalError does not throw exception") {
    val handler = new XmlErrorHandler
    val exception = createException("Test fatal error", 30, 25)
    
    captureOutput {
      handler.fatalError(exception)
    }
  }

  test("warning with line number 0") {
    val handler = new XmlErrorHandler
    val exception = createException("Test warning", 0, 0)
    
    val output = captureOutput {
      handler.warning(exception)
    }
    
    assert(output.contains("line 0"))
    assert(output.contains("column 0"))
  }

  test("error with large line and column numbers") {
    val handler = new XmlErrorHandler
    val exception = createException("Test error", 99999, 88888)
    
    val output = captureOutput {
      handler.error(exception)
    }
    
    assert(output.contains("line 99999"))
    assert(output.contains("column 88888"))
  }

  test("multiple warnings can be handled") {
    val handler = new XmlErrorHandler
    
    val output = captureOutput {
      handler.warning(createException("Warning 1", 1, 1))
      handler.warning(createException("Warning 2", 2, 2))
      handler.warning(createException("Warning 3", 3, 3))
    }
    
    assert(output.contains("line 1"))
    assert(output.contains("line 2"))
    assert(output.contains("line 3"))
  }

  test("different error types can be mixed") {
    val handler = new XmlErrorHandler
    
    val output = captureOutput {
      handler.warning(createException("A warning", 5, 1))
      handler.error(createException("An error", 10, 2))
      handler.fatalError(createException("A fatal error", 15, 3))
    }
    
    assert(output.contains("Warning"))
    assert(output.contains("Error"))
    assert(output.contains("Fatal Error while"))
  }

  test("exception message is printed in stack trace on stderr") {
    val handler = new XmlErrorHandler
    val exception = createException("Custom error message", 42, 7)
    
    val (stdout, stderr) = captureBoth {
      handler.error(exception)
    }
    
    assert(stderr.contains("Custom error message"))
  }
}
