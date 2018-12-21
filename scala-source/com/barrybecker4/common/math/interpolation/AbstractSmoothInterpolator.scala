/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

/**
  * @author Barry Becker
  */
abstract class AbstractSmoothInterpolator private[interpolation](function: Array[Double])
  extends AbstractInterpolator(function) {

  override def interpolate(value: Double): Double = {
    if (value < 0 || value > 1.0) throw new IllegalArgumentException("value out of range [0, 1] :" + value)
    val len = function.length - 1
    val x = value * len.toDouble
    val index0 = x.toInt
    val index1 = index0 + 1
    val xdiff = x - index0
    // Produce the 4 points to use for interpolation
    val y1 = function(index0)
    var y0 = y1
    var y2 = y1
    if (len > 0) y2 = if (index1 > len) function(len) else function(index1)
    var y3 = y2
    if (index0 > 0) y0 = function(index0 - 1)
    if (index1 < len) y3 = function(index1 + 1)
    //System.out.println("y0=" + y0 + " y1=" + y1 + " y2=" + y2 + " y3=" + y3 + "  xdiff=" + xdiff);
    smoothInterpolate(y0, y1, y2, y3, xdiff)
  }

  protected def smoothInterpolate(y0: Double, y1: Double, y2: Double, y3: Double, mu: Double): Double
}
