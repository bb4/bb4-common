/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite
import com.barrybecker4.common.math.Range


/**
  * @author Barry Becker
  */
class LinearFunctionSuite extends FunSuite {
  /** instance under test */
  private var func: LinearFunction = _
  implicit val doubleEq: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.0000001)

  test("TypicalFunc") {
    func = new LinearFunction(1 / 10.0)
    assert(0 === func.getValue(0))
    assert(1.0 === func.getValue(10))
    assert(8.0 === func.getValue(80))
  }

  test("FuncWithOffset") {
    func = new LinearFunction(2.0, 5.0)
    assert(5 === func.getValue(0))
    assert(11.0 === func.getValue(3))
    assert(165.0 === func.getValue(80))
    assert(-15.0 === func.getValue(-10))
  }

  test("FuncFromRangeToBins") {
    func = new LinearFunction(Range(-1000, 4000), 10)
    assert(2 === func.getValue(0))
    assert(2.006 === func.getValue(3))
    assert(2.16 === func.getValue(80))
    assert(6 === func.getValue(2000))
    assert(12 === func.getValue(5000))
    assert(1.98 === func.getValue(-10))
    assert(-0.02 === func.getValue(-1010))
  }

  test("FuncFromRangeToBinsSmall") {
    func = new LinearFunction(Range(-100, 500), 15)
    assert(2.5 === func.getValue(0))
    assert(2.575 === func.getValue(3))
    assert(0 === func.getValue(-100))
    assert(52.5 === func.getValue(2000))
    assert(15 === func.getValue(500))
    assert(-22.75 === func.getValue(-1010))
  }

  test("FuncFromRangeToBinsPositiveOffset") {
    func = new LinearFunction(Range(100, 500), 15)
    assert(-3.75 === func.getValue(0))
    assert(-7.5 === func.getValue(-100))
    assert(0 === func.getValue(100))
    assert(15 === func.getValue(500))
    assert(-41.625 === func.getValue(-1010))
  }

  test("FuncWithCustomDomain(") {
    func = new LinearFunction(Range(100, 500), 15)
    assert(-3.75 === func.getValue(0))
    assert(-7.5 === func.getValue(-100))
    assert(0 === func.getValue(100))
    assert(15 === func.getValue(500))
    assert(-41.625 === func.getValue(-1010))
  }

  test("FuncWithZeroScale") {
    assertThrows[IllegalArgumentException] {
      new LinearFunction(0.0, 5.0)
    }
  }
}
