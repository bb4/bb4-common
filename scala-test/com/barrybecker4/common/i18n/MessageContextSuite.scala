/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT*/
package com.barrybecker4.common.i18n

import java.io.FileNotFoundException
import java.util.MissingResourceException

import com.barrybecker4.common.app.ILog
import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class MessageContextSuite extends AnyFunSuite {

  test("ConstructionWithValidPath") {
    val context = new MessageContext("com.barrybecker4.common.i18n.message")
    assertResult("bar") { context.getLabel("FOO") }
  }

  test("GetLabelWhenLabelMissing") {
    assertThrows[MissingResourceException] {
      val context = new MessageContext("com.barrybecker4.common.i18n.message")
      context.setLogger(new MyLogger)
      context.getLabel("INVALID")
    }
  }

  test("ConstructionWithInvalidPath") {
    assertThrows[MissingResourceException] {
      val context = new MessageContext("com.barrybecker4.common.invalid.message")
      context.getLabel("FOO")
    }
  }

  test("getLocale valid enum name") {
    val context = new MessageContext("com.barrybecker4.common.i18n.message")
    context.setLogger(new MyLogger)
    assertResult(LocaleType.GERMAN) { context.getLocale("GERMAN", finf = true) }
  }

  test("getLocale unknown with finf false returns English") {
    val context = new MessageContext("com.barrybecker4.common.i18n.message")
    context.setLogger(new MyLogger)
    assertResult(LocaleType.ENGLISH) { context.getLocale("NOSUCH", finf = false) }
  }

  test("getLocale unknown with finf true throws") {
    val context = new MessageContext("com.barrybecker4.common.i18n.message")
    context.setLogger(new MyLogger)
    assertThrows[IllegalArgumentException] {
      context.getLocale("NOSUCH", finf = true)
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