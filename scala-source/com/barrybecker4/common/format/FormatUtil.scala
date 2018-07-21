/** Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format

import java.text.DecimalFormat


/**
  * Miscellaneous commonly used static utility methods.
  * @author Barry Becker
  */
object FormatUtil {
  private val EXP_FORMAT = new DecimalFormat("0.###E0") //NON-NLS
  private val NUM_FORMAT = new DecimalFormat("###,###.##")
  private val INT_FORMAT = new DecimalFormat("#,###")

  /**
    * Show a reasonable number of significant digits.
    * Synchronized because if changes the fract digits in the global format.
    * @param num the number to format.
    * @return a nicely formatted string representation of the number.
    */
  def formatNumber(num: Double): String = synchronized {
    val absnum = Math.abs(num)
    if (absnum == 0) return "0"
    if (absnum > 10000000.0 || absnum < 0.000000001) return EXP_FORMAT.format(num)
    if (absnum > 1000.0) {
      NUM_FORMAT.setMinimumFractionDigits(0)
      NUM_FORMAT.setMaximumFractionDigits(0)
    }
    else if (absnum > 100.0) {
      NUM_FORMAT.setMinimumFractionDigits(1)
      NUM_FORMAT.setMaximumFractionDigits(1)
    }
    else if (absnum > 1.0) {
      NUM_FORMAT.setMinimumFractionDigits(1)
      NUM_FORMAT.setMaximumFractionDigits(3)
    }
    else if (absnum > 0.0001) {
      NUM_FORMAT.setMinimumFractionDigits(2)
      NUM_FORMAT.setMaximumFractionDigits(5)
    }
    else if (absnum > 0.000001) {
      NUM_FORMAT.setMinimumFractionDigits(3)
      NUM_FORMAT.setMaximumFractionDigits(8)
    }
    else {
      NUM_FORMAT.setMinimumFractionDigits(6)
      NUM_FORMAT.setMaximumFractionDigits(11)
    }
    NUM_FORMAT.format(num)
  }

  /**
    * @param num the number to format.
    * @return a nicely formatted string representation of the number.
    */
  def formatNumber(num: Long): String = INT_FORMAT.format(num)
  def formatNumber(num: Int): String = INT_FORMAT.format(num)
}
