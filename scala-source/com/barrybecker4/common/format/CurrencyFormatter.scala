/** Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format

import java.util.{Currency, Locale}


object CurrencyFormatter {
  private val CURRENCY_SYMBOL = Currency.getInstance(Locale.US).getSymbol
}

/**
  * @author Barry Becker
  */
class CurrencyFormatter extends INumberFormatter {

  override def format(number: Double): String = {
    val formattedNumber = FormatUtil.formatNumber(number)
    CurrencyFormatter.CURRENCY_SYMBOL + formattedNumber
  }
}