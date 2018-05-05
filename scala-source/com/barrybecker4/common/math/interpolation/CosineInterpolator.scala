/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

/**
  * @author Barry Becker
  */
class CosineInterpolator(function: Array[Double]) extends AbstractSmoothInterpolator(function) {
  /**
    * Cosine interpolate between 2 values in the function that is defined as a double array.
    *
    * @param value x value in range [0,1] to determine y value for using specified function.
    * @return interpolated value.
    */
  override def interpolate(value: Double): Double = {
    if (value < 0 || value > 1.0) throw new IllegalArgumentException("value out of range [0, 1] :" + value)
    val len = function.length - 1
    val x = value * len.toDouble
    val index0 = x.toInt
    var index1 = index0 + 1
    if (len == 0) index1 = len
    val xdiff = x - index0
    smoothInterpolate(xdiff, function(index0), function(index1), 0, 0)
  }

  override protected def smoothInterpolate(mu: Double, y0: Double, y1: Double, y2: Double, y3: Double): Double = {
    val mu2 = (1.0 - Math.cos(mu * Math.PI)) / 2.0
    y0 * (1.0 - mu2) + y1 * mu2
  }
}