/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

/**
  * Use to interpolate between values on a function defined only at discrete points.
  * @author Barry Becker
  */
abstract class AbstractInterpolator(val function: Array[Double]) extends Interpolator