/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import org.junit.Before
import org.scalactic.TolerantNumerics
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
  * Base test class for function classes.
  * @author Barry Becker
  */
abstract class FunctionSuiteBase extends FunSuite with BeforeAndAfterEach {

  /** function class under test. */
  protected var function: InvertibleFunction = _
  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(0.00000000000001)

  override def beforeEach() {
    function = createFunction
  }

  protected def createFunction: InvertibleFunction

  test("GetFunctionValue") {
    var y = function.getValue(0.1)
    assert(getExpectedValue0_1 === y)
    y = function.getValue(0.9)
    assert(getExpectedValue0_9 === y)
  }

  protected def getExpectedValue0_1: Double
  protected def getExpectedValue0_9: Double

  test("GetInverseFunctionValue") {
    var y = function.getInverseValue(0.1)
    assert(getExpectedInverseValue0_1 === y)
    y = function.getValue(0.9)
    assert(getExpectedInverseValue0_9 === y)
  }

  protected def getExpectedInverseValue0_1: Double
  protected def getExpectedInverseValue0_9: Double
}