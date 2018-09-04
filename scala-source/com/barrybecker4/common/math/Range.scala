/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

/**
  * A double precision range.
  * @author Barry Becker
  */
case class Range(min: Double = Double.NaN, max: Double = Double.NaN) {

  assert(min.isNaN && max.isNaN || min <= max)

  def this(range: Range) { this(range.min, range.max) }

  /** @return new range extending the current one by specified range. */
  def add(range: Range): Range = add(range.min).add(range.max)

  /** @return a new range extending this range by the value argument */
  def add(value: Double): Range = {
    if (min.isNaN) Range(value, value)
    else if (value < min) Range(value, max)
    else if (value > max) Range(min, value)
    else this
  }

  /** @return the max minus the min. */
  def getExtent: Double = {
    if (min.isInfinite || max.isInfinite || (min.isNaN && max.isNaN)) return Double.NaN
    max - min
  }

  /** @return true if the range is completely contained by us. */
  def inRange(range: Range): Boolean = range.min >= min && range.max <= max

  /** @param value value to normalize on unit scale.
    * @return normalized value assuming 0 for min. 1 for max.
    */
  def mapToUnitScale(value: Double): Double = {
    val range = getExtent
    if (range == 0) return 0
    (value - min) / getExtent
  }

  override def toString: String = this.min + " to " + this.max
}