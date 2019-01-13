/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class LogFunctionSuite extends FunSuite {
  /** instance under test */
  private var func: LogFunction = _
  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(0.00001)

  test("TypicalFunc") {
    func = new LogFunction(10.0)
    assertResult(0) { func.getValue(1)}
    assertResult(10.0){ func.getValue(10) }
    assertResult(19.030899869919434){ func.getValue(80) }
    assertResult(20.0){ func.getValue(100) }
    assertResult(29.999999999999996){ func.getValue(1000) }
  }

  test("Base2FuncWithSmallValues") {
    func = new LogFunction(0.1, 2.0, false)
    assertResult(0){ func.getValue(1) }
    assertResult(0.3321928094887363){ func.getValue(10) }
    assertResult(0.3){ func.getValue(8) }
    assertResult(0.39999999999999997){ func.getValue(16) }
    assertResult(-0.3){ func.getValue(0.125) }
    assertResult(0.6643856189774726){ func.getValue(100) }
    assertResult(0.9965784284662088){ func.getValue(1000) }
  }

  test("Base2FuncNeverLessThan0") {
    func = new LogFunction(0.1, 2.0, true)
    assertResult(0){ func.getValue(1) }
    assertResult(0.39999999999999997){ func.getValue(16) }
    assertResult(0.0){ func.getValue(0.125) }
    assertResult(0.6643856189774726){ func.getValue(100) }
  }

  test("Base3Func positive only") {
    func = new LogFunction(1.0, 3.0, true)
    assertResult(0){ func.getValue(0.333) }
    assert(func.getValue(-0.333).isNaN)
    assert(func.getValue(-27).isNaN)
    assert(func.getValue(0).isNaN)
  }

  test("FuncWithNegativeValuesPassedIn") {
    func = new LogFunction(10.0)
    assertResult(0){ func.getValue(1) }
    assertResult(-10.0) { func.getValue(-10) }
    assertResult(-19.030899869919434) { func.getValue(-80) }
    assertResult(-20.0) { func.getValue(-100) }
    assertResult(29.999999999999996){ func.getValue(1000) }
  }

  test("FuncWithZeroScale") {
    func = new LogFunction(0.0)
    assertResult(-0.0){ func.getValue(10) }
  }
}
