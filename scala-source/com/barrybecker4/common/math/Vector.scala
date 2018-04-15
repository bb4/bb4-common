/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

/**
  * Represents an n dimensional vector class.
  * @author Barry Becker
  */
class Vector(initialData: Array[Double]) {
  /** the vector values */
  private var data: Array[Double] = initialData
  assert(data.length > 0)

  def this(length: Int) { this(Array.ofDim[Double](length)) }

  def set(i: Int, value: Double): Unit =
    this.data(i) = value

  def get(i: Int): Double = data(i)

  def copyFrom(b: Vector): Unit = System.arraycopy(this.data, 0, b.data, 0, size)

  /** Find the dot product of ourselves with another vector.
    * @return the dot product with another vector
    */
  def dot(b: Vector): Double = {
    checkDims(b)
    data.zip(b.data).map { case (x, y) => x * y }.sum
  }

  /** Find the normalized dot product with range [-1, 1].
    * @param b vector to dot product with
    * @return the normalized dot product.
    */
  def normalizedDot(b: Vector): Double = {
    val magB = b.magnitude
    val magThis = this.magnitude
    //println("for v1=" + this + "v2=" + b + " magThis=" + magThis + " magB=" + magB)

    var divisor = magThis * magB
    divisor = if (divisor == 0) 1.0
    else divisor
    val dot = this.dot(b)
    val normalizedDotProduct = dot / divisor
    assert(normalizedDotProduct >= (-1.0 - MathUtil.EPS_MEDIUM) && normalizedDotProduct <= (1 + MathUtil.EPS_MEDIUM),
      "Normalized Dot product, " + normalizedDotProduct + ", was outside expected range.\nDot=" + dot + " div=" +
        divisor + "\nfor v1=" + this + "v2=" + b + "\nmagThis=" + magThis + " magB=" + magB)
    normalizedDotProduct
  }

  /** @param factor amount to scale by.
    * @return this Vector, scaled by a constant factor
    */
  def scale(factor: Double): Vector = new Vector(data.map(_ * factor))

  /** @return pairwise sum of this Vector a and b */
  def plus(b: Vector): Vector = {
    checkDims(b)
    new Vector(data.zip(b.data).map { case (x, y) => x + y })
  }

  /** @param b vector to find distance to.
    * @return Euclidean distance between this Vector and b
    */
  def distanceTo(b: Vector): Double = {
    checkDims(b)
    Math.sqrt(data.zip(b.data).map {case (x, y) => (x - y) * (x - y)}.sum)
  }

  /** @return magnitude of the vector. */
  def magnitude: Double = Math.sqrt(data.map(x => x * x).sum)

  /** @return a vector in the same direction as vec, but with unit magnitude. */
  def normalize: Vector = {
    val mag = this.magnitude
    new Vector(data.map(_ / mag))
  }

  def size: Int = data.length

  private def checkDims(b: Vector): Unit =
    if (this.size != b.size) throw new IllegalArgumentException("Dimensions don't match")

  /** @return a string representation of the vector */
  override def toString: String = data.foldLeft("") {(acc, x) => acc + (x + " ")}
}