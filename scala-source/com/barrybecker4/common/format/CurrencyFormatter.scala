/** Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format

object CurrencyFormatter {
  val CURRENCY_SYMBOL: String = NumberFormatter.US_CURRENCY_SYMBOL
}

final class CurrencyFormatter extends INumberFormatter {
  inline override def format(number: Double): String =
    NumberFormatter.currency.format(number)
}
