/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.app

import com.barrybecker4.common.testsupport.i18n.StubMessageContext
import org.scalatest.funsuite.AnyFunSuite

/**
  */
class AppContextSuite extends AnyFunSuite {

  /**
    * When no MessageContext was injected, fall back to the common bundle in the JAR.
    */
  test("GetLabelWhenNoMessageContext") {
    AppContext.injectMessageContext(null)
    assertResult("bar") { AppContext.getLabel("FOO") }
  }

  test("GetLabel") {
    AppContext.injectMessageContext(new StubMessageContext)
    val label = AppContext.getLabel("FOO")
    assertResult("foo") { label }
    AppContext.injectMessageContext(null)
  }

  test("GetLabelWithParams(") {
    AppContext.injectMessageContext(new StubMessageContext)
    val label = AppContext.getLabel("FOO_BAR", Array("param1", "param2"))
    assertResult("foo_bar") { label }
    AppContext.injectMessageContext(null)
  }
}
