/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

object WaveType {
  val VALUES = Array(SINE_WAVE, SQUARE_WAVE, SAWTOOTH_WAVE, TRIANGLE_WAVE3)
}

/**
  * Different sorts of wave forms.
  * @author Barry Becker
  */
sealed abstract class WaveType(name: String) {
  def calculateOffset(theta: Double): Double
  override def toString: String = name
}

case object SINE_WAVE extends WaveType("Sine Wave") {
  override def calculateOffset(theta: Double): Double = Math.sin(theta)
}

case object SQUARE_WAVE extends WaveType("Square Wave") {
  override def calculateOffset(theta: Double): Double = if (Math.sin(theta) > 0.0) 1.0 else -1.0
}

case object SAWTOOTH_WAVE extends WaveType("Sawtooth Wave") {
  override def calculateOffset(theta: Double): Double = {
    val t = theta / Math.PI / 2
    2 * (t - Math.floor(t + 0.5))
  }
}

case object TRIANGLE_WAVE3 extends WaveType("Triangle Wave") {
  override def calculateOffset(theta: Double): Double = Math.abs(SAWTOOTH_WAVE.calculateOffset(theta))
}
