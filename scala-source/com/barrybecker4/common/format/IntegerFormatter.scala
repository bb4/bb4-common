/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.format


/**
  * @author Barry Becker
  */
class IntegerFormatter extends INumberFormatter {
  override def format(number: Double): String = FormatUtil.formatNumber(number.toInt)
}