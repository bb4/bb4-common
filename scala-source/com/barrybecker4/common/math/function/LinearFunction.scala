/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.Range


object LinearFunction {
  private val DOMAIN = Range(Double.MinValue, Double.MaxValue)
}

/**
  * The function defines a line. It scales and offsets values.
  * @param scale amount to multiply/scale the value by
  * @param offset amount to add after scaling.
  * @author Barry Becker
  */
class LinearFunction(scale: Double, offset: Double = 0) extends InvertibleFunction {

  if (scale == 0) throw new IllegalArgumentException("scale cannot be 0.")

  /** Constructor that creates a linear mapping from a range to a set of bin indices that go from 0 to numBins.
    * @param range the range of the domain. From min  to max value.
    * @param numBins number of bins to map to.
    */
  def this(range: Range, numBins: Int) {
    this(numBins / range.getExtent, -range.min * (numBins / range.getExtent))
    if (numBins == 0) throw new IllegalArgumentException("numBins cannot be 0.")
    if (scale == 0) throw new IllegalArgumentException("scale cannot be 0.")
  }

  override def getValue(value: Double): Double = scale * value + offset
  override def getInverseValue(value: Double): Double = (value - offset) / scale
  override def getDomain: Range = LinearFunction.DOMAIN
}