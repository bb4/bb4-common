/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

/**
  * A Complex number is comprised of real and imaginary parts of the form a + bi.
  * @author Barry Becker
  */
object ComplexNumber {
  /** @return the result of dividing c1 by c2. */
  def divide(c1: ComplexNumber, c2: ComplexNumber): ComplexNumber = {
    val r = (c1.real * c2.real + c1.imaginary * c2.imaginary) / (c2.real * c2.real + c2.imaginary * c2.imaginary)
    val i = (c1.imaginary * c2.real - c1.real * c2.imaginary) / (c2.real * c2.real + c2.imaginary * c2.imaginary)
    ComplexNumber(r, i)
  }

  /** @return the sum of two complex numbers.*/
  def add(c1: ComplexNumber, c2: ComplexNumber) = ComplexNumber(c1.real + c2.real, c1.imaginary + c2.imaginary)

  /** @return result of subtracting two ComplexNumber from this one.*/
  def subtract(c1: ComplexNumber, c2: ComplexNumber) = ComplexNumber(c1.real - c2.real, c1.imaginary - c2.imaginary)

  /** @return result of Multiplying two complex numbers.*/
  def multiply(c1: ComplexNumber, c2: ComplexNumber) =
    ComplexNumber(c1.real * c2.real - c1.imaginary * c2.imaginary, c1.real * c2.imaginary + c1.imaginary * c2.real)
}

case class ComplexNumber(real: Double, imaginary: Double) {

  def this(c: ComplexNumber) { this(c.real, c.imaginary) }

  /** @return the magnitude of a complex number. ie.e distance from origin */
  def getMagnitude: Double = Math.sqrt(real * real + imaginary * imaginary)

  /** @return the sum of this number plus another. */
  def add(other: ComplexNumber): ComplexNumber = ComplexNumber.add(this, other)

  /** @return result of subtracting another ComplexNumber from this one. */
  def subtract(other: ComplexNumber): ComplexNumber = ComplexNumber.subtract(this, other)

  /** @return result of multiplying this Complex times another one.  */
  def multiply(other: ComplexNumber): ComplexNumber = ComplexNumber.multiply(this, other)

  /** @param exponent integer power to raise to
    * @return raise this complex number to the specified exponent (power).
    */
  def power(exponent: Int): ComplexNumber = {
    var current = this
    for (i <- 1 until exponent)
      current = current.multiply(this)
    current
  }

  override def toString: String = {
    if (imaginary == 0) s"$real"
    else if (real == 0) s"${imaginary}i"
    else if (imaginary < 0) s"$real - ${-imaginary}i"
    else s"$real + ${imaginary}i"
  }
}