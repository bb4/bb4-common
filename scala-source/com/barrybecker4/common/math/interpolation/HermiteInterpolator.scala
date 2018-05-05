/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation


/**
  * @author Barry Becker
  */
class HermiteInterpolator(function: Array[Double], var tension: Double, var bias: Double)
  extends AbstractSmoothInterpolator(function) {

  def this(function: Array[Double]) {
    this(function, 0, 0)
  }

  override protected def smoothInterpolate(y0: Double, y1: Double, y2: Double, y3: Double, mu: Double): Double = {
    var m0 = .0
    var m1 = .0
    var a0 = .0
    var a1 = .0
    var a2 = .0
    var a3 = .0
    val mu2 = mu * mu
    val mu3 = mu2 * mu
    m0 = (y1 - y0) * (1 + bias) * (1 - tension) / 2
    m0 += (y2 - y1) * (1 - bias) * (1 - tension) / 2
    m1 = (y2 - y1) * (1 + bias) * (1 - tension) / 2
    m1 += (y3 - y2) * (1 - bias) * (1 - tension) / 2
    a0 = 2 * mu3 - 3 * mu2 + 1
    a1 = mu3 - 2 * mu2 + mu
    a2 = mu3 - mu2
    a3 = -2 * mu3 + 3 * mu2
    a0 * y1 + a1 * m0 + a2 * m1 + a3 * y2
  }
}