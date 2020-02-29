/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.format

import org.scalatest.funsuite.AnyFunSuite

class CurrencyFormatterSuite extends AnyFunSuite {

  val fmtr = new CurrencyFormatter

  test("small currency values") {
    assertResult("$0.01") {fmtr.format(0.01)}
    assertResult("$0.00123") {fmtr.format(0.00123456)}
    assertResult("$1.054") {fmtr.format(1.0543)}
  }
  test("negative currency values") {
    assertResult("$-10.023") {fmtr.format(-10.023)}
    assertResult("$-50.0") {fmtr.format(-50.0)}
    assertResult("$-200,050") {fmtr.format(-200050.0)}
  }
  test("medium currency values") {
    assertResult("$5.0") {fmtr.format(5.0)}
    assertResult("$250.0") {fmtr.format(250.0)}
    assertResult("$1,050") {fmtr.format(1050.0)}
  }
  test("large currency values") {
    assertResult("$1.235E8") {fmtr.format(123456750.0)}
    assertResult("$3.249E15") {fmtr.format(3249139980984750.0)}
    assertResult("$2.139E24") {fmtr.format(2139042130948210394213050.0)}
  }

}
