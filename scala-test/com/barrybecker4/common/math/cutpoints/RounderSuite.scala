/*
 * Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
 */

package com.barrybecker4.common.math.cutpoints

import org.scalatest.FunSuite
import RounderSuite.EPS
import org.scalactic.TolerantNumerics

/**
  * @author Barry Becker
  */
object RounderSuite {
  private val EPS = 0.00001
}

class RounderSuite extends FunSuite {

  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(EPS)

  test("RoundSmallUp") {
    assert(2 === Rounder.roundUp(1.234))
  }

  test("RoundSmallDown") {
    assert(1 === Rounder.roundDown(1.234))
  }

  test("RoundVerySmallUp") {
    assert(0.05 === Rounder.roundUp(0.034))
  }

  test("RoundVerySmallDown") {
    assert(0.05 === Rounder.roundDown(0.034))
  }

  def testRoundMedium1Up() {
    assert(50 === Rounder.roundUp(34.5))
  }

  test("RoundMedium1Down(") {
    assert(50 === Rounder.roundDown(34.5))
  }

  test("RoundMedium2Up") {
    assert(100 === Rounder.roundUp(74.5))
  }

  test("RoundMedium2Down") {
    assert(100 === Rounder.roundDown(74.5))
  }

  test("RoundMedium3Up") {
    assert(200 === Rounder.roundUp(140.1))
  }

  test("RoundMedium3Down") {
    assert(100 === Rounder.roundDown(140.1))
  }

  test("RoundLargeUp") {
    assert(1000 === Rounder.roundUp(874.5))
  }

  test("RoundLargeDown") {
    assert(1000 === Rounder.roundDown(874.5))
  }

  test("RoundVeryLargeUp") {
    assert(2000000 === Rounder.roundUp(1812874.51234567))
  }

  test("RoundVeryLargeDown") {
    assert(2000000 === Rounder.roundDown(1812874.51234567))
  }
}
