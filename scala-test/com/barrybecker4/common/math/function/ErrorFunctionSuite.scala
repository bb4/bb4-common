/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

class ErrorFunctionSuite extends FunctionSuiteBase {

  override protected def createFunction = new ErrorFunction
  override protected def getExpectedValue0_1 = 0.11246296000000001
  override protected def getExpectedValue0_9 = 0.7969081
  override protected def getExpectedInverseValue0_1 = 0.089
  override protected def getExpectedInverseValue0_9 = 0.7969081
}