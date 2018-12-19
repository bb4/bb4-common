/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.Range
import scala.collection.mutable.ListBuffer


object CountFunction {
  /** When we get more than this many x values, scroll to the right instead of compressing the domain. */
  private val DEFAULT_MAX_X_VALUES: Int = 500
}

/**
  * Tracks a positive integer over time. For example, it might be used to track a population count.
  * @param initialYValue y value for x=0
  * @author Barry Becker
  */
class CountFunction(val initialYValue: Double) extends Function {
  /** These parallel arrays define the piecewise function map. */
  protected val xValues: ListBuffer[Double] = new ListBuffer[Double]()
  protected val yValues: ListBuffer[Double] = new ListBuffer[Double]()
  private var maxXValues: Int = CountFunction.DEFAULT_MAX_X_VALUES
  setInitialValue(initialYValue)

  def setInitialValue(initialYValue: Double): Unit = {
    xValues.clear()
    yValues.clear()
    xValues.append(0.0)
    yValues.append(initialYValue)
  }

  def addValue(x: Double, y: Double): Unit = {
    if (xValues.size > maxXValues) {
      xValues.remove(0)
      yValues.remove(0)
    }
    xValues.append(x)
    yValues.append(y)
    // keep x values in range 0-1
    val len: Int = xValues.size
    for (i <- 0 until len)
      xValues(i) = i.toDouble / (len - 1)
  }

  def setMaxXValues(max: Int): Unit = {
    maxXValues = max
  }

  /** X axis domain */
  override def getDomain: Range = Range(xValues.head, xValues.last)

  /** @param xValue x value to get y value for.
    * @return y value
    */
  override def getValue(xValue: Double): Double = getValue(xValue, xValues, yValues)

  /** @param xValue x value
    * @return an interpolated y values for a specified x
    */
  private def getValue(xValue: Double, xVals: Seq[Double], yVals: Seq[Double]): Double = {
    // first find the x value
    var i: Int = 0
    while (i < xVals.size && xValue > xVals(i)) {
      i += 1
    }
    if (i == 0 || xVals.size == 1) return yVals.head
    val xValm1: Double = xVals(i - 1)
    val denom: Double = xVals(i) - xValm1
    if (denom == 0) yVals(i - 1)
    else {
      val ratio: Double = (xValue - xValm1) / denom
      val yValm1: Double = yVals(i - 1)
      yValm1 + ratio * (yVals(i) - yValm1)
    }
  }

  override def toString: String = {
    val bldr: StringBuilder = new StringBuilder("CountFunction: ")
    for (i <- xValues.indices)
      bldr.append("\nx=").append(xValues(i)).append(" y=").append(yValues(i))
    bldr.toString
  }
}

