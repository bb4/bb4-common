/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation


/**
  * @author Barry Becker
  */
class CubicInterpolator(function: Array[Double]) extends AbstractSmoothInterpolator(function) {

  override protected def smoothInterpolate(y0: Double, y1: Double, y2: Double, y3: Double, mu: Double): Double = {
    val mu2 = mu * mu
    val a0 = y3 - y2 - y0 + y1
    val a1 = y0 - y1 - a0
    val a2 = y2 - y0
    a0 * mu * mu2 + a1 * mu2 + a2 * mu + y1
  }
}