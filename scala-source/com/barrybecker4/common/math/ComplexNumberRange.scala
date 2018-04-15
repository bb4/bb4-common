/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

/**
  * A complex number range represents a box on the complex plane.
  * @param point1 one value for range
  * @param point2 other value for range
  * @author Barry Becker
  */
class ComplexNumberRange(point1: ComplexNumber, point2: ComplexNumber) {

  private var extent = point2.subtract(point1)

  /** If params are outside 0, 1, then the interpolated point will be outside the range.
    * @param realRatio      between 0 and 1 in real direction
    * @param imaginaryRatio between 0 and 1 in imaginary direction
    * @return interpolated position.
    */
  def getInterpolatedPosition(realRatio: Double, imaginaryRatio: Double) =
    new ComplexNumber(point1.real + extent.real * realRatio, point1.imaginary + extent.imaginary * imaginaryRatio)

  override def toString: String = point1 + " to " + point2
}