/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.cutpoints

import org.scalatest.FunSuite
import com.barrybecker4.common.math.Range
import NiceNumbersSuite._

/**
  * @author Barry Becker
  */
object NiceNumbersSuite {
  private val LOOSE = "loose "
  private val TIGHT = "tight "
  private val EXPECTED_LOOSE_CUTS = Array("0", "20", "40", "60", "80", "100", "120")
  private val EXPECTED_TIGHT_CUTS = Array("11", "20", "40", "60", "80", "101")
  private val EXPECTED_LOOSE_CUTS2 = Array("11.05", "11.1", "11.15", "11.2", "11.25")
  private val EXPECTED_TIGHT_CUTS2 = Array("11.1", "11.15", "11.2", "11.23")
  private val EXPECTED_LOOSE_CUTS3 = Array("-2", "0", "2", "4", "6")
  private val EXPECTED_TIGHT_CUTS3 = Array("-1.5", "0", "2", "4", "4.6")
  private val EXPECTED_LOOSE_CUTS4 = Array("-1.5", "-1", "-0.5", "0", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5")
  private val EXPECTED_TIGHT_CUTS4 = Array("-1.48", "-1", "-0.5", "0", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "4.61")
}

class NiceNumbersSuite extends FunSuite {
  /** instance under test */
  private val generator = new CutPointGenerator

  test("NiceNumbers1") {
    val range = Range(11.0, 101.0)
    generator.setUseTightLabeling(false)
    val resultLoose = generator.getCutPointLabels(range, 5)
    generator.setUseTightLabeling(true)
    val resultTight = generator.getCutPointLabels(range, 5)
    assert(EXPECTED_LOOSE_CUTS === resultLoose)
    assert(EXPECTED_TIGHT_CUTS === resultTight)
  }

  test("NiceNumbers2") {
    val range = Range(11.1, 11.23)
    generator.setUseTightLabeling(false)
    val resultLoose = generator.getCutPointLabels(range, 5)
    assert(EXPECTED_LOOSE_CUTS2 === resultLoose)

    generator.setUseTightLabeling(true)
    val resultTight = generator.getCutPointLabels(range, 5)
    assert(EXPECTED_TIGHT_CUTS2 === resultTight)
  }
//
//  test("NiceNumbers3") {
//    val range = Range(-1.475879149417865, 4.609775340085392)
//    generator.setUseTightLabeling(false)
//    val resultLoose = generator.getCutPointLabels(range, 5)
//    generator.setUseTightLabeling(true)
//    val resultTight = generator.getCutPointLabels(range, 5)
//    assert(resultLoose === EXPECTED_LOOSE_CUTS3)
//    assert(resultTight === EXPECTED_TIGHT_CUTS3)
//  }
//
//  test("NiceNumbers4") {
//    val range = Range(-1.475879149417865, 4.609775340085392)
//    generator.setUseTightLabeling(false)
//    val resultLoose = generator.getCutPointLabels(range, 20)
//    generator.setUseTightLabeling(true)
//    val resultTight = generator.getCutPointLabels(range, 20)
//    assert(resultLoose === EXPECTED_LOOSE_CUTS4)
//    assert(resultTight === EXPECTED_TIGHT_CUTS4)
//  }
//
//  /** There should be no fractional digits in this case */
//  test("FracDigitsWhenIntegerEndPoints") {
//    val expectedNumFractDigits = 0
//    var f = generator.getNumberOfFractionDigits(Range(10, 100), 3)
//    assert(expectedNumFractDigits === f)
//    f = generator.getNumberOfFractionDigits(Range(1000, 100000), 10)
//    assert(expectedNumFractDigits === f)
//    f = generator.getNumberOfFractionDigits(Range(-1000, -100), 300)
//    assert(expectedNumFractDigits === f)
//  }
//
//  /** Min > max/ Expect an error */
//  test("FracDigitsWithHighMaxTicks") {
//    val range = Range(-0.002, 0.001)
//    val f = generator.getNumberOfFractionDigits(range, 900)
//    assert(6.0 === f)
//  }
//
//  test("FracDigitsInThirdDecimalPlace200Ticks") {
//    val f = generator.getNumberOfFractionDigits(Range(-0.002, 0.001), 200)
//    assert(5.0 === f)
//  }
//
//  test("FracDigitsInThirdDecimalPlace2Ticks") {
//    val f = generator.getNumberOfFractionDigits(Range(-0.002, 0.001), 2)
//    assert(3.0 === f)
//  }
//
//  test("FracDigitsSixthDecimalPlace30Ticks") {
//    val f = generator.getNumberOfFractionDigits(Range(0.0000001, 0.0001), 30)
//    assert(6.0=== f)
//  }
//
//  test("FracDigitsSixthDecimalPlace5Ticks") {
//    val f = generator.getNumberOfFractionDigits(Range(0.0000001, 0.0001), 5)
//    assert(5.0 === f)
//  }
//
//  test("FracDigits4") {
//    val f = generator.getNumberOfFractionDigits(Range(-100.01, -100.001), 30)
//    assert(4.0 === f)
//  }
}
