/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
abstract class InterpolatorSuiteBase extends FunSuite {
  /** interpolation class under test. */
  protected var interpolator: Interpolator = _
  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(0.000001)

  protected def createInterpolator(func: Array[Double]): Interpolator

  test("SimpleInterpolation0_1") {
    val func = Array(0.0, 1.0, 2.0)
    interpolator = createInterpolator(func)
    var y = interpolator.interpolate(0.1)
    assert(getExpectedSimpleInterpolation0_1 === y)
    y = interpolator.interpolate(0.9)
    assert(getExpectedSimpleInterpolation0_9 === y)
  }

  protected def getExpectedSimpleInterpolation0_1: Double
  protected def getExpectedSimpleInterpolation0_9: Double

  test("TypicalInterpolate") {
    val func = Array(1.0, 2.0, 3.0, 4.0)
    interpolator = createInterpolator(func)
    var y = interpolator.interpolate(0.1)
    assert(getExpectedTypicalInterpolation0_1 === y)
    y = interpolator.interpolate(0.9)
    assert(getExpectedTypicalInterpolation0_9 === y)
  }

  protected def getExpectedTypicalInterpolation0_1: Double
  protected def getExpectedTypicalInterpolation0_4: Double
  protected def getExpectedTypicalInterpolation0_5: Double
  protected def getExpectedTypicalInterpolation0_9: Double

  test("InterpolateOnePoint") {
    val func = Array(1.0)
    interpolator = createInterpolator(func)
    val y = interpolator.interpolate(0.0)
    assert(1.0 === y)
  }

  protected def getExpectedOnePointInterpolation: Double

  test("Interpolate2Points") {
    val func = Array(0.0, 1.0)
    interpolator = createInterpolator(func)
    val y = interpolator.interpolate(0.1)
    assert(getExpectedInterpolation2Points0_1 === y)
  }

  protected def getExpectedInterpolation2Points0_1: Double // 0.1

  test("InterpolateOutOfRangeClosePositive") {
    val func = Array(1.0, 2.0)
    interpolator = createInterpolator(func)
    assertThrows[IllegalArgumentException] {
      interpolator.interpolate(1.1)
    }
  }

  test("InterpolateOutOfRangeFar") {
    val func = Array(1.0, 2.0)
    interpolator = createInterpolator(func)
    assertThrows[IllegalArgumentException] {
      interpolator.interpolate(2.1)
    }
  }

  test("InterpolateOutOfRangeNegative") {
    val func = Array(1.0, 2.0)
    interpolator = createInterpolator(func)
    assertThrows[IllegalArgumentException] {
      interpolator.interpolate(-1.1)
    }
  }
}
