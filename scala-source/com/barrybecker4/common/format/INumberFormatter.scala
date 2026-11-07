/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.format

import java.util.{Currency, Locale}

/**
  * Scala 3 number formatting as a function type (no trait dispatch for call sites that use this API).
  */
object NumberFormatter {
  opaque type Formatter = Double => String

  private[format] val US_CURRENCY_SYMBOL: String = Currency.getInstance(Locale.US).getSymbol

  private def apply(fn: Double => String): Formatter = fn

  def default: Formatter = FormatUtil.formatNumber(_)

  def integer: Formatter = d => FormatUtil.formatNumber(d.toInt)

  def currency: Formatter = d => US_CURRENCY_SYMBOL + FormatUtil.formatNumber(d)

  extension (f: Formatter)
    inline def format(d: Double): String = f(d)
}

/**
  * Knows how to format something.
  *
  * For new code, prefer [[NumberFormatter]] (`NumberFormatter.default`, etc.).
  */
trait INumberFormatter {
  def format(number: Double): String
}

object INumberFormatter {
  /** Adapts a [[NumberFormatter.Formatter]] for APIs that expect this trait. */
  def apply(f: NumberFormatter.Formatter): INumberFormatter =
    d => f.format(d)
}
