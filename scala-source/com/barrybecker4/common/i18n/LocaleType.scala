/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.i18n

import java.util.Locale

object LocaleType {

  val VALUES = Seq(ENGLISH, GERMAN, JAPANESE, VIETNAMESE
  )
  def valueOf(theType: String): LocaleType = {
    theType match {
      case "ENGLISH" => ENGLISH
      case "GERMAN" => GERMAN
      case "JAPANESE" => JAPANESE
      case "VIETNAMESE" => VIETNAMESE
    }
  }
}

/**
  * Enum for the currently supported locales.
  * These are probably the most challenging to support. That is one of the reasons why they were selected.
  * @author Barry Becker
  */
sealed class LocaleType(val locale: Locale) {

}

case object ENGLISH extends LocaleType(new Locale("en", "US"))
case object GERMAN extends LocaleType(new Locale("de", "DE"))
case object JAPANESE extends LocaleType(new Locale("ja", "JP"))
case object VIETNAMESE extends LocaleType(new Locale("vi"))



