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
  * @param positiveOnly if true then clamp negative log values at 0.
  * @author Barry Becker
  */
class LogFunction(val scale: Double,
                  val base: Double = LogFunction.DEFAULT_BASE,
                  val positiveOnly: Boolean = false) extends InvertibleFunction {

  private val baseConverter = Math.log(base)

  override def getValue(value: Double): Double = {
    var logValue = .0
    if (value <= 0) {
      if (positiveOnly)
        throw new IllegalArgumentException("Cannot take the log of a number (" + value + ") that is <=0")
      logValue = Math.signum(value) * Math.log(-value)
    }
    else logValue = if (positiveOnly) Math.max(0, Math.log(value))
    else Math.log(value)
    scale * logValue / baseConverter
  }

  override def getInverseValue(value: Double): Double = Math.pow(base, value / scale)
  override def getDomain = Range(0, Double.MaxValue)
}