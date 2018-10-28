/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.app

import com.barrybecker4.common.i18n.StubMessageContext
import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class AppContextSuite extends FunSuite {

  /**
    * Verify that we get an error if the AppContext was not stubbed with a MessageContext.
    */
  test("GetLabelWhenNoMessageContext") {
      assertResult("FOO") {AppContext.getLabel("FOO")}
//    assertThrows[NullPointerException] {
//      AppContext.getLabel("FOO")
//    }
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
