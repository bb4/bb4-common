/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation


/**
  * @author Barry Becker
  */
class StepInterpolator(function: Array[Double]) extends AbstractInterpolator(function) {

  override def interpolate(value: Double): Double = {
    if (value < 0 || value > 1.0) throw new IllegalArgumentException("value out of range [0, 1] :" + value)
    if (value == 1.0) function.last else function((value * function.length).toInt)
  }
}
