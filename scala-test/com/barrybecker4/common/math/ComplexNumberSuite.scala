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
    assertResult("10.0") {ComplexNumber(10.0).toString}
    assertResult("0.0") {ComplexNumber(0).toString}
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

  test("5^(3+2i)") {
    assertResult(ComplexNumber(-124.62689272475588, -9.650782857996035)) {
      ComplexNumber.pow(5, ComplexNumber(3, 2)) }
  }
  test("4^(3+0i)") {
    assertResult(ComplexNumber(64, 0)) { ComplexNumber.pow(4, ComplexNumber(3)) }
  }
  test("3^(0+i)") {
    assertResult(ComplexNumber(0.4548324228266097, 0.8905770416677471)) {
      ComplexNumber.pow(3, ComplexNumber(0, 1)) }
  }
  test("2^(1+(pi/2)i)") {
    assertResult(ComplexNumber(0.9271102968159963, 1.7721361396737427)) {
      ComplexNumber.pow(2, ComplexNumber(1, Math.PI / 2.0)) }
  }
  test("1^(0+(pi)i)") {
    assertResult(ComplexNumber(1, 0)) { ComplexNumber.pow(1, ComplexNumber(0, Math.PI)) }
  }

  test("magnitude") {
    assertResult(8.0) { ComplexNumber(2, 2).magnitude}
  }

  test("reciprocal") {
    assertResult(ComplexNumber(0.15384615384615385, -0.23076923076923078)) {
      ComplexNumber(2, 3).reciprocal}
  }

  test("1 / (2, 3i) divide") {
    assertResult(ComplexNumber(0.15384615384615385, -0.23076923076923078)) {
      ComplexNumber(1, 0).divide(ComplexNumber(2, 3)) }
  }

  test("(2 + 2i) / (2, 3i) divide") {
    assertResult(ComplexNumber(0.7692307692307693, -0.15384615384615385)) {
      ComplexNumber(2, 2).divide(ComplexNumber(2, 3)) }
  }

  test("2 / (2, 3i) divide") {
    assertResult(ComplexNumber(0.3076923076923077, -0.46153846153846156)) {
      ComplexNumber(2, 0).divide(ComplexNumber(2, 3)) }
  }

  test("(3 + 3i) / (2, 3i) divide") {
    assertResult(ComplexNumber(1.1538461538461537, -0.23076923076923073)) {
      ComplexNumber(3, 3).divide(ComplexNumber(2, 3)) }
  }

  test("(30 + 30i) / (2, 3i) divide") {
    assertResult(ComplexNumber(11.53846153846154, -2.3076923076923075)) {
      ComplexNumber(30, 30).divide(ComplexNumber(2, 3)) }
  }

  test("(30 + 29.9i) / (2, 3i) divide") {
    assertResult(ComplexNumber(11.515384615384615, -2.3230769230769237)) {
      ComplexNumber(30, 29.9).divide(ComplexNumber(2, 3)) }
  }

  test("(3 + 2i) / (2, 3i) divide ") {
    assertResult(ComplexNumber(0.9230769230769231, -0.3846153846153846)) {
      ComplexNumber(3, 2).divide(ComplexNumber(2, 3)) }
  }

  test("3 ^ 2 ") {
    assertResult(ComplexNumber(9, 0)) {
      ComplexNumber(3).pow(2) }
  }

  test("(3 + 0.1i) ^ 3 ") {
    assertResult(ComplexNumber(26.91, 2.6990000000000003)) {
      ComplexNumber(3, 0.1).pow(3) }
  }

  test("i ^ i = e ^ -pi/2") {
    assertResult(ComplexNumber.pow(ComplexNumber.i, ComplexNumber.i)) {
      ComplexNumber(Math.exp(-Math.PI/2.0))
    }
  }

  test("(2 + 0.1i) ^ (2 + 0.1i) ") {
    val v = ComplexNumber(2, 0.1)
    assertResult(ComplexNumber(3.932933294546521, 0.6725091090273414)) {
      ComplexNumber.pow(v, v)
    }
  }

  test("Complex sin(pi/2, pi/2)") {
    val v = ComplexNumber(Math.PI / 2.0, Math.PI / 2.0)
    assertResult(ComplexNumber(2.5091784786580567, 1.409139167295973E-16)) {
      ComplexNumber.sin(v)
    }
  }

  test("Complex sin(pi/2)") {
    val v = ComplexNumber(Math.PI / 2.0)
    assertResult(ComplexNumber(1)) {
      ComplexNumber.sin(v)
    }
  }
}
