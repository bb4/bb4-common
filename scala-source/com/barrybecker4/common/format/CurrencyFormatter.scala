/** Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format

import com.barrybecker4.common.app.AppContext

import java.util.{Currency, Locale}

object CurrencyFormatter {
  def currencySymbol(locale: Locale = AppContext.getLocale): String =
    Currency.getInstance(locale).getSymbol

  def CURRENCY_SYMBOL: String = currencySymbol()
}

final class CurrencyFormatter(val locale: Locale = AppContext.getLocale) extends INumberFormatter {
  private val currencySymbol: String = CurrencyFormatter.currencySymbol(locale)

  inline override def format(number: Double): String =
    currencySymbol + FormatUtil.formatNumber(number)
}
