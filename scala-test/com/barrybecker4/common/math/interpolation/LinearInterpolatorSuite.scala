/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

/**
  * @author Barry Becker
  */
class LinearInterpolatorSuite extends InterpolatorSuiteBase {
  override protected def createInterpolator(func: Array[Double]) = new LinearInterpolator(func)

  override protected def getExpectedSimpleInterpolation0_1 = 0.2
  override protected def getExpectedSimpleInterpolation0_9 = 1.8
  override protected def getExpectedTypicalInterpolation0_1 = 1.3
  override protected def getExpectedTypicalInterpolation0_4 = 2.8
  override protected def getExpectedTypicalInterpolation0_5 = 3.6
  override protected def getExpectedTypicalInterpolation0_9 = 3.7
  override protected def getExpectedOnePointInterpolation = 1.0
  override protected def getExpectedInterpolation2Points0_1 = 0.1

  override protected def getExpectedInterpolationExponential0 = 0
  override protected def getExpectedInterpolationExponential0_11 = 0.034375
  override protected def getExpectedInterpolationExponential0_85 = 0.625
  override protected def getExpectedInterpolationExponential_1 = 1.0
}
