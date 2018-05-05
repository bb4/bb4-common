/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class ComplexNumberSuite extends FunSuite {

  test("construction") {
    assertResult("1.0 + 1.0i") {ComplexNumber(1, 1).toString}
    assertResult("1.3 - 2.3i") {ComplexNumber(1.3, -2.3).toString}
    assertResult("0.1i") {ComplexNumber(0, 0.1).toString}
    assertResult("10.0") {ComplexNumber(10.0, 0).toString}
    assertResult("0.0") {ComplexNumber(0, 0).toString}
  }

  test("times") {  // should be 2i
    assertResult(ComplexNumber(0, 2.0)) {ComplexNumber(1, 1).multiply(ComplexNumber(1, 1))}
  }

  test("power ^ 2 as number") {  // should be 2i
    assertResult(ComplexNumber(0.0, 2.0)) {ComplexNumber(1, 1).power(2)}
  }

  test("power ^ 2 as string") { // should be 2i
    assertResult("2.0i") {ComplexNumber(1, 1).power(2).toString}
  }

  test("power ^ 3 as string") { // should be 2i
    assertResult("-2.0 + 2.0i") {ComplexNumber(1, 1).power(3).toString}
  }

  test("(1.1 + 1.2i) ^ 3") { // should be -3.421+2.628i
    assertResult("-3.421 + 2.6280000000000006i") {ComplexNumber(1.1, 1.2).power(3).toString}
  }
}
