/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT*/
package com.barrybecker4.common.i18n

import java.io.FileNotFoundException
import java.util.MissingResourceException
import com.barrybecker4.common.app.ILog
import org.scalatest.FunSuite


/**
  * @author Barry Becker
  */
class MessageContextSuite extends FunSuite {
  /** instance under test */
  private var context: MessageContext = _

  test("ConstructionWithValidPath") {
    context = new MessageContext("com.barrybecker4.common.i18n.message")
    assertResult("bar") { context.getLabel("FOO") }
  }

  test("GetLabelWhenLabelMissing") {
    assertThrows[MissingResourceException] {
      context = new MessageContext("com.barrybecker4.common.i18n.message")
      context.setLogger(new MyLogger)
      context.getLabel("INVALID")
    }
  }

  test("ConstructionWithInvalidPath") {
    assertThrows[MissingResourceException] {
      context = new MessageContext("com.barrybecker4.common.invalid.message")
      context.getLabel("FOO")
    }
  }

  private class MyLogger extends ILog {
    override def setDestination(logDestination: Int): Unit = {}
    override def getDestination = 0

    @throws[FileNotFoundException]
    override def setLogFile(fileName: String): Unit = {}
    override def setStringBuilder(bldr: StringBuilder): Unit = {}
    override def print(logLevel: Int, appLogLevel: Int, message: String): Unit = System.out.print(message)
    override def println(logLevel: Int, appLogLevel: Int, message: String): Unit = System.out.println(message)
    override def print(message: String): Unit = System.out.print(message)
    override def println(message: String): Unit = System.out.println(message)
  }
}