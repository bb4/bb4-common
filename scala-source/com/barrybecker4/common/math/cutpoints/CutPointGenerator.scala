/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.cutpoints

import java.text.DecimalFormat

import com.barrybecker4.common.math.{MathUtil, Range}
import AbstractCutPointFinder.MIN_RANGE

/**
  * Calculates nicely rounded intervals for a specified range.
  * From an article by Paul Heckbert in Graphics Gems 1.
  * @param useTightLabeling whether or not to use tight labeling.
  * @param formatter method for formatting the label values.
  * @author Barry Becker
  */
class CutPointGenerator(val useTightLabeling: Boolean, val formatter: DecimalFormat) {

  setUseTightLabeling(useTightLabeling)
  /** If true, show the precise min/max values at the extreme cut points (tight), else loose labels */
  private var cutPointFinder: AbstractCutPointFinder = _

  def this() {
    this(true, new DecimalFormat("###,###.##"))
  }

  def setUseTightLabeling(useTight: Boolean): Unit =
    cutPointFinder = if (useTight) new TightCutPointFinder else new LooseCutPointFinder

  /** Retrieve the cut point values.
    * If its a really small range include both min and max to avoid having just one label.
    * @param range       range to be divided into intervals.
    * @param maxNumTicks upper limit on number of cut points to return.
    * @return the cut points
    */
  def getCutPoints(range: Range, maxNumTicks: Int): Array[Double] = cutPointFinder.getCutPoints(range, maxNumTicks)

  /** Labels for the found cut points.
    * @param range    tickmark range.
    * @param maxTicks upper limit on the number of cuts.
    * @return cut point labels
    */
  def getCutPointLabels(range: Range, maxTicks: Int): Array[String] = {
    val cutPoints = cutPointFinder.getCutPoints(range, maxTicks)
    var maxFracDigits = getNumberOfFractionDigits(range, maxTicks)
    val useTight = cutPointFinder.isInstanceOf[TightCutPointFinder]
    val length = cutPoints.length
    val labels = Array.ofDim[String](length)

    for (i <- 0 until length) {
      // show a little more precision for the tight labels.
      maxFracDigits += (if (useTight && (i == 0 || i == length - 1)) 1 else 0)
      formatter.setMaximumFractionDigits(maxFracDigits)
      labels(i) = formatter.format(cutPoints(i))
    }
    labels
  }

  /** Determine the number of fractional digits to show in the nice numbered cut points.
    * @param range       the range to check.
    * @param maxNumTicks no more than this many cut points.
    * @return Recommended number of fractional digits to display. The cut points: eg. 0, 1, 2, etc.
    */
  private[cutpoints] def getNumberOfFractionDigits(range: Range, maxNumTicks: Int) = {
    var max1 = range.max
    if (range.getExtent <= MIN_RANGE) max1 = range.min + MIN_RANGE
    val extent = Rounder.roundUp(max1 - range.min)
    val d = Rounder.roundDown(extent / (maxNumTicks - 1))
    Math.max(-Math.floor(MathUtil.log10(d)), 0).toInt
  }
}