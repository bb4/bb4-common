/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import org.scalatest.FunSuite
import com.barrybecker4.common.math.{MathUtil, Range}

/**
  * @author Barry Becker
  */
class FunctionInverterSuite extends FunSuite {
  /** instance under test. */
  private var inverter: FunctionInverter = _

  test("InvertTrivialFunction") {
    val func = Array[Double](0, 1.0)
    verify(func, Array(0.0, 1.0))
  }

  test("InvertSimple3Function") {
    val func = Array[Double](0, 0.1, 1.0)
    verify(func, Array(0.0, 0.72222, 1.0))
  }

  test("InvertSimple6Function") {
    val func = Array[Double](0, 0.1, 0.1, 0.3, 0.7, 1.0)
    verify(func, Array(0.0, 0.5, 0.65, 0.75, 0.866666, 1.0))
  }

  test("InvertSimple10Function") {
    val func = Array[Double](0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.1, 0.2, 0.3, 1.0)
    verify(func, Array(0.0, 0.7, 0.8, 0.9, 0.9142, 0.9285, 0.9428, 0.95714, 0.971428, 0.9857, 1.0))
  }

  def testLinearFunction() {
    val func = Array[Double](0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
    verify(func, Array(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0))
  }

  /** Negative test.: We should get an exception if not monotonic.*/
  test("Monotonic") {
    val func = Array[Double](0, 0.9, 0.1, 1.0)
    inverter = new FunctionInverter(func)
    assertThrows[IllegalStateException] {
      inverter.createInverseFunction(Range(0, 1.0))
    }
  }

  private def verify(func: Array[Double], expInverse: Array[Double]): Unit = {
    inverter = new FunctionInverter(func)
    val inverse = inverter.createInverseFunction(Range(0, 1.0))
    assertFunctionsEqual(expInverse, inverse)
  }

  /** Assert two function arrays are equal. */
  private def assertFunctionsEqual(expFunc: Array[Double], func: Array[Double]): Unit = {
    assertResult(expFunc.length) { func.length }
    var matched: Boolean = true
    for (i <- expFunc.indices)
      if (Math.abs(expFunc(i) - func(i)) > MathUtil.EPS_BIG) {
        System.out.println("Mismatch at entry " + i + ". Expected " + expFunc(i) + " but got " + func(i))
        matched = false
      }
    assert(matched, "Expected " + expFunc.mkString(", ") + " but got " + func.mkString(", "))
  }
}
