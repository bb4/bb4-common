// Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.interpolation

/**
  * @author Barry Becker
  */
class StepInterpolatorSuite extends InterpolatorSuiteBase {

  override protected def createInterpolator(func: Array[Double]) = new StepInterpolator(func)

  override protected def getExpectedSimpleInterpolation0_1 = 0.0
  override protected def getExpectedSimpleInterpolation0_9 = 2.0
  override protected def getExpectedTypicalInterpolation0_1 = 1.0
  override protected def getExpectedTypicalInterpolation0_4 = 2.296
  override protected def getExpectedTypicalInterpolation0_5 = 2.5
  override protected def getExpectedTypicalInterpolation0_9 = 4.0
  override protected def getExpectedOnePointInterpolation = 1.0
  override protected def getExpectedInterpolation2Points0_1 = 0.0

  override protected def getExpectedInterpolationExponential0 = 0
  override protected def getExpectedInterpolationExponential0_11 = 0
  override protected def getExpectedInterpolationExponential0_85 = 1.0
  override protected def getExpectedInterpolationExponential_1 = 1.0
}
