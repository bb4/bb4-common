/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.cutpoints

import com.barrybecker4.common.math.Range
import scala.collection.mutable.ArrayBuffer
import TightCutPointFinder._

/**
  * The min and max cut-points will be specific full precision numbers.
  *
  * @author Barry Becker
  */
object TightCutPointFinder {
  /** Labels should not get closer to each other than this percentage of total. */
  private val MIN_LABEL_SEPARATION_PCT = 0.2
}

class TightCutPointFinder extends AbstractCutPointFinder {

  override protected def addPoints(positions: ArrayBuffer[Double],
                                   roundedRange: Range, finalRange: Range, d: Double): Unit = {
    positions.append(checkSmallNumber(finalRange.min))
    var initialInc = d
    var pct = (roundedRange.min + d - finalRange.min) / d
    if (pct < MIN_LABEL_SEPARATION_PCT) initialInc = 2 * d
    var finalInc = 0.5 * d
    pct = (finalRange.max - (roundedRange.max - d)) / d
    if (pct < MIN_LABEL_SEPARATION_PCT) finalInc = 1.5 * d
    val stop = roundedRange.max - finalInc

    for (x <- scala.collection.immutable.Range.BigDecimal(roundedRange.min + initialInc, stop, d))
      positions.append(checkSmallNumber(x.toDouble))

    positions.append(checkSmallNumber(finalRange.max))
  }
}