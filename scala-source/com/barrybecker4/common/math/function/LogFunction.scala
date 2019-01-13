/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.Range


object LogFunction {
  private val DEFAULT_BASE = 10
}

/**
  * The function takes the log of a value in the specified base, then scales it.
  * @param base         logarithm base.
  * @param scale        amount to scale after taking the logarithm.
  * @param positiveOnly If true then log of a non-positive number is NaN and -log(x) when x between 0 and 1.
  *                     If false, then return log(-x)
  * @author Barry Becker
  */
class LogFunction(val scale: Double,
                  val base: Double = LogFunction.DEFAULT_BASE,
                  val positiveOnly: Boolean = false) extends InvertibleFunction {

  private val baseConverter = Math.log(base)

  override def getValue(value: Double): Double = {
    val logValue =
      if (value <= 0) {
        if (positiveOnly) Double.NaN
        else Math.signum(value) * Math.log(-value)
      }
      else {
        if (positiveOnly) Math.max(0, Math.log(value)) else Math.log(value)
      }
    scale * logValue / baseConverter
  }

  override def getInverseValue(value: Double): Double = Math.pow(base, value / scale)
  override def getDomain = Range(0, Double.MaxValue)
}
