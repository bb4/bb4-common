/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

object InterpolationMethod {
  val VALUES = Array(STEP, LINEAR, CUBIC, COSINE, HERMITE)
}

/**
  * Supported interpolators
  * @author Barry Becker
  */
abstract class InterpolationMethod {

  /**
    * Factory method for creating the search strategy to use.
    * Do not call the constructor directly.
    * @return the search method to use
    */
  def createInterpolator(function: Array[Double]): Interpolator
}


// val STEP, LINEAR, CUBIC, COSINE, HERMITE = Value
case object STEP extends InterpolationMethod() {
  override def createInterpolator(function: Array[Double]) = new StepInterpolator(function)
}

case object LINEAR extends InterpolationMethod() {
  override def createInterpolator(function: Array[Double]) = new LinearInterpolator(function)
}

case object CUBIC extends InterpolationMethod() {
  override def createInterpolator(function: Array[Double]) = new CubicInterpolator(function)
}

case object COSINE extends InterpolationMethod() {
  override def createInterpolator(function: Array[Double]) = new CosineInterpolator(function)
}

case object HERMITE extends InterpolationMethod() {
  override def createInterpolator(function: Array[Double]) = new HermiteInterpolator(function)
}

