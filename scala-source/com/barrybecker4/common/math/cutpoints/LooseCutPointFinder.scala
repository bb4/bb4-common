/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.cutpoints

import com.barrybecker4.common.math.Range
import scala.collection.mutable.ArrayBuffer

/**
  * The min and max cut-points will be nice round numbers.
  * @author Barry Becker
  */
class LooseCutPointFinder extends AbstractCutPointFinder {

  override protected def addPoints(positions: ArrayBuffer[Double],
                                   roundedRange: Range, finalRange: Range, d: Double): Unit = {

    val stop: Double = roundedRange.max + 0.5 * d

    for (x <- roundedRange.min until stop by d)
      positions.append(checkSmallNumber(x))
  }
}