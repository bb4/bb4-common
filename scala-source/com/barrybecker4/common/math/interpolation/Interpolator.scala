/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */

package com.barrybecker4.common.math.interpolation

/**
  * Defines a way to interpolate between 2 points in function that is defined by an array of y values.
  * @author Barry Becker
  */
trait Interpolator {

  /** Given an x value, returns f(x)   (i.e. y)
    * @param value value to find interpolated function value for.
    * @return the interpolated value.
    */
  def interpolate(value: Double): Double
}