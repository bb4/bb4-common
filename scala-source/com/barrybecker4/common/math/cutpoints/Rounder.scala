/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.cutpoints

import com.barrybecker4.common.math.MathUtil

/**
  * Rounds numbers in a "nice" way so that they are easy to read.
  * From an article by Paul Heckbert in Graphics Gems 1.
  * @author Barry Becker
  */
object Rounder {

  /** Find a "nice" number approximately equal to the numberToRound (rounding up).
    * Corresponds to the "nicenum" method in graphics gems (page 659).
    * @param numberToRound the value to round
    * @return nice rounded number. Something that has a final significant digit of 1, 2, or 5 x10&caret;j
    */
  def roundUp(numberToRound: Double): Double = round(numberToRound, roundDown = false)
  def roundDown(numberToRound: Double): Double = round(numberToRound, roundDown = true)

  /**
    * Find a "nice" number approximately equal to the numberToRound.
    * Round the number down if round is true, round up if round is false.
    * Corresponds to the "nicenum" method in graphics gems (page 659).
    * @param numberToRound the value to round
    * @param roundDown     if true, then round the number down. If false, take the ceil of it.
    * @return nice rounded number. Something that has a final significant digit of 1, 2, or 5 x10&caret;j
    */
  private def round(numberToRound: Double, roundDown: Boolean) = {
    val exp = Math.floor(MathUtil.log10(numberToRound)).toInt
    val normalizedNumber = numberToRound / MathUtil.exp10(exp)
    val niceNum = if (roundDown) roundNumberDown(normalizedNumber)
    else roundNumberUp(normalizedNumber)
    niceNum * MathUtil.exp10(exp)
  }

  private def roundNumberUp(num: Double) = {
    if (num < 1.0) 1.0
    else if (num <= 2.0) 2.0
    else if (num < 5.0)  5.0
    else 10.0
  }

  private def roundNumberDown(num: Double) = {
    if (num < 1.5) 1.0
    else if (num < 3.0)  2.0
    else if (num < 7.0)  5.0
    else 10.0
  }
}