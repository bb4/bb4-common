/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.format

import org.scalatest.FunSuite


class FormatUtilSuite extends FunSuite {

  test("small number formatting") {
    assertResult("0.00000003456")  { FormatUtil.formatNumber(0.00000003456) }
    assertResult("-0.0035")  { FormatUtil.formatNumber(-0.0034983456) }
  }

  test("medium number formatting") {
    assertResult("0.3456")  { FormatUtil.formatNumber(0.3456) }
    assertResult("23,909")  { FormatUtil.formatNumber(23909.034983456) }
  }

  test("large number formatting") {
    assertResult("9,239,909")  { FormatUtil.formatNumber(9239909.034983456) }
    assertResult("1.235E22")  { FormatUtil.formatNumber(12349812898798975329290.3456) }
  }
}
