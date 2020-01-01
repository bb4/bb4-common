/* Copyright by Barry G. Becker, 2000-2019. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

/**
  * A Complex number is comprised of real and imaginary parts of the form a + bi.
  * @author Barry Becker
  */
object ComplexNumber {
  val i: ComplexNumber = ComplexNumber(0, 1.0)
  val NaN: ComplexNumber = ComplexNumber(Double.NaN, Double.NaN)

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

  /** See https://www.math.toronto.edu/mathnet/questionCorner/complexexp.html
    * @return the result of raising a to some complex number c. Here, a is a real number.
    */
  def pow(a: Double, c: ComplexNumber): ComplexNumber = {
    val a1 = Math.pow(a, c.real)
    val clna = c.imaginary * Math.log(a)
    ComplexNumber(a1 * Math.cos(clna), a1 * Math.sin(clna))
  }

  /**
    * See http://mathworld.wolfram.com/ComplexExponentiation.html
    * @return the result of raising z to some complex number s. Here, z is a complex number.
    */
  def pow(z: ComplexNumber, s: ComplexNumber): ComplexNumber = {
    if (z.imaginary == 0) pow(z.real, s)
    else {
      val a = z.real
      val b = z.imaginary
      val c = s.real
      val d = s.imaginary
      val asqpbsq = a * a + b * b
      val compArg = arg(z)
      val coef = Math.pow(asqpbsq, c / 2.0) * Math.exp(-d * compArg)
      val term = c * compArg + 0.5 * d * Math.log(asqpbsq)
      val realPart = coef * Math.cos(term)
      val imgPart = coef * Math.sin(term)
      ComplexNumber(realPart, imgPart)
    }
  }

  /** See http://mathworld.wolfram.com/ComplexArgument.html
    * @return the complex argument of z
    */
  def arg(z: ComplexNumber): Double =
    Math.atan(z.imaginary / z.real)

  /** See https://proofwiki.org/wiki/Sine_of_Complex_Number
    * sin(a+bi) = sina coshb+icosa sinhb
    */
  def sin(c: ComplexNumber): ComplexNumber = {
    val realPart = Math.sin(c.real) * Math.cosh(c.imaginary)
    val imgPart = Math.cos(c.real) * Math.sinh(c.imaginary)
    ComplexNumber(realPart, imgPart)
  }
}

case class ComplexNumber(real: Double, imaginary: Double = 0) {

  def this(c: ComplexNumber) { this(c.real, c.imaginary) }

  /** @return the magnitude of a complex number. ie.e distance from origin */
  def getMagnitude: Double = Math.sqrt(real * real + imaginary * imaginary)
  def negate: ComplexNumber = ComplexNumber(-real, -imaginary)

  /** @return the sum of this number plus another. */
  def add(other: ComplexNumber): ComplexNumber = ComplexNumber.add(this, other)
  def add(other: Double): ComplexNumber = ComplexNumber(real + other, imaginary)

  /** @return result of subtracting another ComplexNumber from this one. */
  def subtract(other: ComplexNumber): ComplexNumber = ComplexNumber.subtract(this, other)
  def subtract(other: Double): ComplexNumber = ComplexNumber(real - other, imaginary)

  /** @return result of multiplying this Complex times another one.  */
  def multiply(other: ComplexNumber): ComplexNumber = ComplexNumber.multiply(this, other)
  def multiply(other: Double): ComplexNumber = ComplexNumber(real * other, imaginary * other)

  /** @return result of dividing this Complex times another one.  */
  def divide(other: ComplexNumber): ComplexNumber = ComplexNumber.multiply(this, other.reciprocal)
  def divide(other: Double): ComplexNumber = ComplexNumber(real / other, imaginary / other)

  /** @return this complex number raised to the power n, where n is an integer */
  def pow(n: Int): ComplexNumber = {
    var v = this
    for (_ <- 1 until n)
      v = v.multiply(this)
    v
  }

  /** @return the distance from the origin on the complex plane */
  def magnitude: Double = real * real + imaginary * imaginary

  /** @return the reciprocal - which is 1/c */
  def reciprocal: ComplexNumber = ComplexNumber(real / magnitude, -imaginary / magnitude)

  def isNaN: Boolean = real.isNaN || imaginary.isNaN
  def isInfinite: Boolean = real.isInfinite || imaginary.isInfinite

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
