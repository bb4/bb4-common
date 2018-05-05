/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.Range


/**
  * Piecewise linear function representation.
  * These parallel arrays define the piecewise function map.
  * @param xValues x function values
  * @param yValues y function values
  * @author Barry Becker
  */
class PiecewiseFunction(val xValues: Array[Double], val yValues: Array[Double]) extends InvertibleFunction {
  assert(this.xValues.length == this.yValues.length)

  override def getValue(value: Double): Double = getInterpolatedValue(value)

  /** @param value y value to get x for
    * @return inverse function value.
    */
  override def getInverseValue(value: Double): Double = getInterpolatedValue(value)
  override def getDomain = Range(xValues(0), xValues(xValues.length - 1))

  /** @param value x value to get interpolated y for.
    * @return the interpolated y value based on the key points in the arrays.
    */
  private def getInterpolatedValue(value: Double): Double = { // first find the x value
    var i = 0
    while (value > xValues(i)) i += 1

    // return the linearly interpolated y value
    if (i == 0) return yValues(0)
    val xValm1 = xValues(i - 1)
    val denom = xValues(i) - xValm1
    if (denom == 0) yValues(i - 1)
    else {
      val ratio = (value - xValm1) / denom
      val yValm1 = yValues(i - 1)
      yValm1 + ratio * (yValues(i) - yValm1)
    }
  }

  override def toString: String = {
    val bldr = new StringBuilder("PiecewiseFunction: ")
    for (i <- xValues.indices)
      bldr.append("x=").append(xValues(i)).append(" y=").append(yValues(i))
    bldr.toString
  }
}