/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.i18n

import org.scalatest.funsuite.AnyFunSuite

import java.util.Locale


/**
  * Tests for LocaleType enum and fromString parsing.
  * @author Barry Becker
  */
class LocaleTypeSuite extends AnyFunSuite {

  test("ENGLISH has correct locale") {
    val locale = LocaleType.ENGLISH.locale
    assertResult("en") { locale.getLanguage }
    assertResult("US") { locale.getCountry }
  }

  test("GERMAN has correct locale") {
    val locale = LocaleType.GERMAN.locale
    assertResult("de") { locale.getLanguage }
    assertResult("DE") { locale.getCountry }
  }

  test("JAPANESE has correct locale") {
    val locale = LocaleType.JAPANESE.locale
    assertResult("ja") { locale.getLanguage }
    assertResult("JP") { locale.getCountry }
  }

  test("VIETNAMESE has correct locale") {
    val locale = LocaleType.VIETNAMESE.locale
    assertResult("vi") { locale.getLanguage }
    assertResult("") { locale.getCountry }
  }

  test("VALUES contains all locale types") {
    val values = LocaleType.VALUES
    assertResult(4) { values.length }
    assert(values.contains(LocaleType.ENGLISH))
    assert(values.contains(LocaleType.GERMAN))
    assert(values.contains(LocaleType.JAPANESE))
    assert(values.contains(LocaleType.VIETNAMESE))
  }

  test("values and VALUES are equivalent") {
    val enumValues = LocaleType.values
    val constantValues = LocaleType.VALUES
    assertResult(enumValues.toSeq) { constantValues.toSeq }
  }

  test("fromString with valid name returns Some") {
    assertResult(Some(LocaleType.ENGLISH)) { LocaleType.fromString("ENGLISH") }
    assertResult(Some(LocaleType.GERMAN)) { LocaleType.fromString("GERMAN") }
    assertResult(Some(LocaleType.JAPANESE)) { LocaleType.fromString("JAPANESE") }
    assertResult(Some(LocaleType.VIETNAMESE)) { LocaleType.fromString("VIETNAMESE") }
  }

  test("fromString with lowercase name returns None") {
    assertResult(None) { LocaleType.fromString("english") }
    assertResult(None) { LocaleType.fromString("german") }
  }

  test("fromString with mixed case returns None") {
    assertResult(None) { LocaleType.fromString("English") }
    assertResult(None) { LocaleType.fromString("German") }
  }

  test("fromString with invalid name returns None") {
    assertResult(None) { LocaleType.fromString("FRENCH") }
    assertResult(None) { LocaleType.fromString("SPANISH") }
    assertResult(None) { LocaleType.fromString("INVALID") }
  }

  test("fromString with empty string returns None") {
    assertResult(None) { LocaleType.fromString("") }
  }

  test("fromString with whitespace-only string returns None") {
    assertResult(None) { LocaleType.fromString("   ") }
  }

  test("fromString trims leading whitespace") {
    assertResult(Some(LocaleType.ENGLISH)) { LocaleType.fromString("  ENGLISH") }
  }

  test("fromString trims trailing whitespace") {
    assertResult(Some(LocaleType.ENGLISH)) { LocaleType.fromString("ENGLISH  ") }
  }

  test("fromString trims both leading and trailing whitespace") {
    assertResult(Some(LocaleType.GERMAN)) { LocaleType.fromString("  GERMAN  ") }
  }

  test("fromString with null returns None") {
    assertResult(None) { LocaleType.fromString(null) }
  }

  test("fromString with special characters returns None") {
    assertResult(None) { LocaleType.fromString("ENGLISH!") }
    assertResult(None) { LocaleType.fromString("GER-MAN") }
  }

  test("fromString with numbers returns None") {
    assertResult(None) { LocaleType.fromString("123") }
    assertResult(None) { LocaleType.fromString("ENGLISH123") }
  }

  test("LocaleType enum can be used in pattern matching") {
    def matchLocale(locale: LocaleType): String = locale match {
      case LocaleType.ENGLISH => "en"
      case LocaleType.GERMAN => "de"
      case LocaleType.JAPANESE => "ja"
      case LocaleType.VIETNAMESE => "vi"
    }
    
    assertResult("en") { matchLocale(LocaleType.ENGLISH) }
    assertResult("de") { matchLocale(LocaleType.GERMAN) }
  }

  test("LocaleType instances are distinct") {
    assert(LocaleType.ENGLISH ne LocaleType.GERMAN)
    assert(LocaleType.JAPANESE ne LocaleType.VIETNAMESE)
  }

  test("LocaleType ordinal values") {
    // Enum ordinals are in declaration order
    assert(LocaleType.ENGLISH.ordinal == 0)
    assert(LocaleType.GERMAN.ordinal == 1)
    assert(LocaleType.JAPANESE.ordinal == 2)
    assert(LocaleType.VIETNAMESE.ordinal == 3)
  }
}
