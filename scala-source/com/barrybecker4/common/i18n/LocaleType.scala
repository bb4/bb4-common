/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.i18n

import java.util.Locale

import scala.util.Try

/**
  * Supported locales for i18n.
  * These are probably the most challenging to support. That is one of the reasons why they were selected.
  * @author Barry Becker
  */
enum LocaleType(val locale: Locale):
  case ENGLISH extends LocaleType(new Locale("en", "US"))
  case GERMAN extends LocaleType(new Locale("de", "DE"))
  case JAPANESE extends LocaleType(new Locale("ja", "JP"))
  case VIETNAMESE extends LocaleType(new Locale("vi"))

object LocaleType {

  /** Same as [[values]] (Scala enum), kept for existing call sites. */
  val VALUES: Array[LocaleType] = values

  /** @return locale constant matching enum name (e.g. ENGLISH), or None */
  def fromString(name: String): Option[LocaleType] =
    Try(LocaleType.valueOf(name.trim)).toOption
}
