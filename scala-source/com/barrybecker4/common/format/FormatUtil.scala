/** Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format

import java.text.DecimalFormat
import java.util.function.Supplier


/**
  * Miscellaneous commonly used static utility methods.
  */
object FormatUtil {
  private def threadLocalDecimalFormat(pattern: String): ThreadLocal[DecimalFormat] =
    ThreadLocal.withInitial(new Supplier[DecimalFormat] {
      override def get(): DecimalFormat = new DecimalFormat(pattern)
    })

  private val EXP_FORMAT = threadLocalDecimalFormat("0.###E0") //NON-NLS
  private val NUM_FORMAT = threadLocalDecimalFormat("###,###.##")
  private val INT_FORMAT = threadLocalDecimalFormat("#,###")

  /** Minimum and maximum fraction digits for double formatting for the given magnitude. */
  private def fractionDigitsFor(absnum: Double): (Int, Int) = {
    if (absnum > 1000.0) (0, 0)
    else if (absnum > 100.0) (1, 1)
    else if (absnum > 1.0) (1, 3)
    else if (absnum > 0.0001) (2, 5)
    else if (absnum > 0.000001) (3, 8)
    else (6, 11)
  }

  /**
    * Show a reasonable number of significant digits.
    * @param num the number to format.
    * @return a nicely formatted string representation of the number.
    */
  def formatNumber(num: Double): String = {
    val absnum = Math.abs(num)
    if (absnum == 0) return "0"
    if (absnum > 10000000.0 || absnum < 0.000000001) return EXP_FORMAT.get.format(num)
    val (minFract, maxFract) = fractionDigitsFor(absnum)
    val numFormat = NUM_FORMAT.get
    numFormat.setMinimumFractionDigits(minFract)
    numFormat.setMaximumFractionDigits(maxFract)
    numFormat.format(num)
  }

  /**
    * @param num the number to format.
    * @return a nicely formatted string representation of the number.
    */
  def formatNumber(num: Long): String = INT_FORMAT.get.format(num)
  def formatNumber(num: Int): String = INT_FORMAT.get.format(num)
}
