/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.cutpoints

import com.barrybecker4.common.math.Range

import scala.collection.mutable.ArrayBuffer


/**
  * Base class for cut point finders.
  * @author Barry Becker
  */
object AbstractCutPointFinder {
  /** The range should not get smaller than this. */
  private[cutpoints] val MIN_RANGE: Double = 1.0E-10
}

abstract class AbstractCutPointFinder {
  /**
    * Retrieve the cut point values.
    * If its a really small range include both min and max to avoid having just one label.
    * @param range       range to be divided into intervals.
    * @param maxNumTicks upper limit on number of cut points to return.
    * @return the cut points
    */
  def getCutPoints(range: Range, maxNumTicks: Int): Array[Double] = {
    validateArguments(range)
    var finalRange: Range = new Range(range)
    if (range.getExtent <= AbstractCutPointFinder.MIN_RANGE)
      finalRange = finalRange.add(range.min + AbstractCutPointFinder.MIN_RANGE)
    var positions: ArrayBuffer[Double] = new ArrayBuffer[Double]()
    if (finalRange.getExtent < AbstractCutPointFinder.MIN_RANGE)
      positions :+= finalRange.min
    else determineCutPoints(maxNumTicks, finalRange, positions)
    val result: Array[Double] = new Array[Double](positions.size)
    positions.toArray
  }

  private def determineCutPoints(maxTicks: Int, finalRange: Range, positions: ArrayBuffer[Double]): Unit = {
    val extent: Double = Rounder.roundUp(finalRange.getExtent)
    val d: Double = Rounder.roundDown(extent / (maxTicks - 1))
    val roundedRange: Range = Range(Math.floor(finalRange.min / d) * d, Math.ceil(finalRange.max / d) * d)
    addPoints(positions, roundedRange, finalRange, d)
  }

  protected def addPoints(positions: ArrayBuffer[Double], roundedRange: Range, finalRange: Range, d: Double): Unit

  /** Verify that the min and max are valid.
    * @param range range to check for NaN values.
    */
  private def validateArguments(range: Range): Unit = {
    if (range.getExtent.isNaN) throw new IllegalArgumentException("Min cannot be greater than max for " + range)
    if (range.min.isNaN || range.min.isInfinite || range.max.isNaN || range.max.isInfinite)
      throw new IllegalArgumentException("Min or max of the range [" + range + "] is not a number.")
  }

  /** If real small just assume it is zero.
    * @param value the value to check.
    * @return zero if value is below the smallest allowed range, else return value.
    */
  private[cutpoints] def checkSmallNumber(value: Double): Double =
    if (Math.abs(value) < AbstractCutPointFinder.MIN_RANGE) 0 else value
}