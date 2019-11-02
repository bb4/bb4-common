/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite
import com.barrybecker4.common.math.Range

/**
  * @author Barry Becker
  */
class HeightFunctionSuite extends FunSuite {

  implicit val doubleEq: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.000001)
  /** instance under test */
  private var func: HeightFunction = _

  test("TypicalFunc(") {
    func = new HeightFunction(Array[Double](.2, 0.3, 0.6, 0.7, 0.5, 0.3, 0.11, 0.04, -0.1, -0.15), Range(1, 20))
    assert(0.2 === func.getValue(1))
    assert(0.4473684 === func.getValue(10))
    assert(0.065789 === func.getValue(15))
    assert(-0.15 === func.getValue(20))
  }

  test("Base0Func") {
    func = new HeightFunction(Array[Double](.2, 0.31, 0.6, 0.7, 0.5, 0.3, 0.11, 0.04, -0.1, -0.15), Range(0, 9))
    assert(0.2 === func.getValue(0))
    assert(0.31 === func.getValue(1))
    assert(0.5 === func.getValue(4))
    assert(0.04 === func.getValue(7))
    assert(-0.15 === func.getValue(9))
  }

  test("FuncFromRangeToBinsPositiveOffset") {
    func = new HeightFunction(Array[Double](.2, 0.3, 0.6, 0.7, 0.5, 0.2), Range(100, 500))
    assert(0.2 === func.getValue(100))
    assert(0.225 === func.getValue(120))
    assert(0.3 === func.getValue(180))
    assert(0.20375 === func.getValue(499))
    assert(0.4875 === func.getValue(230))
    assert(0.2 === func.getValue(500))
  }

  test("FuncFromRangeToBinsOutOfRange") {
    func = new HeightFunction(Array[Double](.2, 0.3, 0.6, 0.7, 0.5, 0.2), Range(100, 500))
    assertThrows[IllegalArgumentException] {
      func.getValue(501) // 0.2
    }
  }

  test("FuncFromRangeToBins") {
    func = new HeightFunction(Array[Double](.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.1, 0.5, 0.4, 0.3), Range(0, 9))
    assert(0.2 === func.getValue(0))
    assert(0.21 === func.getValue(0.1))
    assert(0.3 === func.getValue(1))
    assert(0.5 === func.getValue(4))
    assert(0.2 === func.getValue(5))
    assert(0.4 === func.getValue(8))
    assert(0.3 === func.getValue(9))
  }

  test("FuncFromAutoRangeToBins") {
    func = new HeightFunction(Array[Double](.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.1, 0.5, 0.4, 0.3))
    assert(0.2 === func.getValue(0))
    assert(0.29 === func.getValue(0.1))
    assert(0.405 === func.getValue(0.15))
    assert(0.432 === func.getValue(0.16))
    assert(0.16 === func.getValue(0.6))
    assert(0.39 === func.getValue(0.9))
    assert(0.327 === func.getValue(0.97))
    assert(0.3 === func.getValue(1.0))
  }
}
