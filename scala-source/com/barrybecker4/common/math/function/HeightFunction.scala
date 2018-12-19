/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.Range
import com.barrybecker4.common.math.interpolation.LinearInterpolator


/**
  * Represents a general y values function. It does not have to be monotonic or 1-1.
  * @param yValues the y values.
  * @param domain the extent of the regularly spaced x axis values
  * @author Barry Becker
  */
class HeightFunction(val yValues: Array[Double],
                     var domain: Range = Range(0, 1.0)) extends Function {

  private val domainToBinFunc = new LinearFunction(1.0 / domain.getExtent, -domain.min / domain.getExtent)

  // a function that maps from the domain to indices within yValues.
  private[function] val interpolator = new LinearInterpolator(yValues)

  /** X axis domain */
  override def getDomain: Range = domain

  /** @param xValue x value to get y value for.
    * @return y value
    */
  override def getValue(xValue: Double): Double = interpolator.interpolate(domainToBinFunc.getValue(xValue))
}
