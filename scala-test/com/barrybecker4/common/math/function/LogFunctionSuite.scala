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
  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(0.000001)

  test("TypicalFunc") {
    func = new LogFunction(10.0)
    assert(0 === func.getValue(1))
    assert(10.0 === func.getValue(10))
    assert(19.0309 === func.getValue(80))
    assert(20.0 === func.getValue(100))
    assert(30.0 === func.getValue(1000))
  }

  test("Base2FuncWithSmallValues") {
    func = new LogFunction(0.1, 2.0, false)
    assert(0 === func.getValue(1))
    assert(0.3321928 === func.getValue(10))
    assert(0.3 === func.getValue(8))
    assert(0.4 === func.getValue(16))
    assert(-0.3 === func.getValue(0.125))
    assert(0.664385618 === func.getValue(100))
    assert(0.996578428 === func.getValue(1000))
  }

  test("Base2FuncNeverLessThan0") {
    func = new LogFunction(0.1, 2.0, true)
    assert(0 === func.getValue(1))
    assert(0.4 === func.getValue(16))
    assert(0.0 === func.getValue(0.125))
    assert(0.6643856 === func.getValue(100))
  }

  test("FuncWithNegativeValuesPassedIn") {
    func = new LogFunction(10.0)
    assert(0 === func.getValue(1))
    assert(-10.0 === func.getValue(-10))
    assert(-19.0309 === func.getValue(-80))
    assert(-20.0 === func.getValue(-100))
    assert(30.0 === func.getValue(1000))
  }

  test("FuncWithZeroScale") {
    func = new LogFunction(0.0)
    assert(-0.0 === func.getValue(10))
  }
}
