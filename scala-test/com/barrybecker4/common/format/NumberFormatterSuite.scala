/* Copyright by Barry G. Becker, 2000-2026. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.format

import org.scalatest.funsuite.AnyFunSuite

class NumberFormatterSuite extends AnyFunSuite {

  test("default matches DefaultNumberFormatter") {
    val legacy = new DefaultNumberFormatter
    assertResult(legacy.format(1234.5)) {
      NumberFormatter.default.format(1234.5)
    }
  }

  test("integer truncates before formatting") {
    assertResult("1,234") {
      NumberFormatter.integer.format(1234.9)
    }
  }

  test("currency prefixes US symbol") {
    assertResult("$5.0") {
      NumberFormatter.currency.format(5.0)
    }
  }

  test("INumberFormatter adapter uses formatter") {
    val traitApi = INumberFormatter(NumberFormatter.integer)
    assertResult("42") {
      traitApi.format(42.9)
    }
  }
}
