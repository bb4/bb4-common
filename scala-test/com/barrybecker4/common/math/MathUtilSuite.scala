/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import javax.vecmath.Point2d
import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class MathUtilSuite extends FunSuite {
  test("PositiveGCD") {
    assertResult(21) {MathUtil.gcd(21, 42)}
    assertResult(1) {MathUtil.gcd(21, 4L)}
    assertResult(2) {MathUtil.gcd(2L, 4L)}
    assertResult(1) {MathUtil.gcd(41, 2L)}
    assertResult(20) {MathUtil.gcd(420L, 40L)}
    assertResult(20L) {MathUtil.gcd(40L, 420L)}
  }

  test("NegativeGCD") {
    assertResult(2L) { MathUtil.gcd(2L, 0L) }
    assertResult(2L) { MathUtil.gcd(0L, -2L) }
    assertResult(1L) { MathUtil.gcd(423L, -40L) }
    assertResult(40L) { MathUtil.gcd(440L, -120L) }
  }

  test("IntNeg") {
    assertResult(2) { 2.1.toInt }
    assertResult(0) { -0.1.toInt }
    assertResult(-2) { -2.1.toInt }
    assertResult(-2) { -2.9.toInt }
  }

  test("Factorial4") {
    assertResult(24L) { MathUtil.factorial(4) }
  }

  test("Factorial40") {
    assertResult(2432902008176640000L) { MathUtil.factorial(20) }
  }

  test("FactorialRatio4d3") {
    assertResult(4L) { MathUtil.permutation(4, 3) }
  }

  test("FactorialRatio7d4") {
    assertResult(210L) {MathUtil.permutation(7, 4)}
  }

  test("BigFactorialRatio7d4") {
    assertResult("210") { MathUtil.bigPermutation(7, 4).toString }
  }

  test("BigFactorialRatio9d4")  {
    assertResult("15120") { MathUtil.bigPermutation(9, 4).toString }
  }

  test("BigFactorialRatio9d7")  {
    assertResult("72") { MathUtil.bigPermutation(9, 7).toString }
  }

  test("BigFactorialRatio9d9")  {
    assertResult("1") { MathUtil.bigPermutation(9, 9).toString }
  }

  test("BigFactorialRatio9d1")  {
    assertResult("362880") { MathUtil.bigPermutation(9, 1).toString }
  }

  test("BigFactorialRatio9d10")  {
    assertThrows[IllegalArgumentException] { MathUtil.bigPermutation(9, 10).toString }
  }

  test("BigFactorialRatio40d20") {
    assertResult( "335367096786357081410764800000") { MathUtil.bigPermutation(40, 20).toString }
  }

  test("BigFactorialRatio70d20") {
    assertResult("4923573423718507525892570413923319470803578288313732111773416409792512000000000000") {
      MathUtil.bigPermutation(70, 20).toString
    }
  }

  test("Combination4_3") {
    assertResult("4") { MathUtil.combination(4, 3).toString}
  }

  test("Combination40_30") {
    assertResult("847660528") {
      MathUtil.combination(40, 30).toString
    }
  }

  test("FindAngle") {
    val point = new Point2d(1.0, 1.0)
    var x: Double = 0
    while (x < 2.0 * Math.PI) {
      val toPoint = new Point2d(point.x + Math.cos(x), point.y + Math.sin(x))
      //System.out.println("angle to " + toPoint + " is " + MathUtil.getDirectionTo(point, toPoint))
      x += 0.3
    }
    assertResult(Math.PI / 4.0) { MathUtil.getDirectionTo(point, new Point2d(2.0, 2.0))}
    assertResult(3.0 * Math.PI / 4.0) { MathUtil.getDirectionTo(point, new Point2d(0.0, 2.0)) }
    assertResult(5.0 * Math.PI / 4.0 - 2.0 * Math.PI) {
      MathUtil.getDirectionTo(point, new Point2d(0.0, 0.0))
    }
    assertResult(-Math.PI / 4.0) { MathUtil.getDirectionTo(point, new Point2d(2.0, 0.0)) }
  }
}
