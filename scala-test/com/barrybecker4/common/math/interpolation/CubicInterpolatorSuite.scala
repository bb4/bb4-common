/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

/**
  * @author Barry Becker
  */
class CubicInterpolatorSuite extends InterpolatorSuiteBase {

  override protected def createInterpolator(func: Array[Double]) = new CubicInterpolator(func)

  override protected def getExpectedSimpleInterpolation0_1 = 0.168
  override protected def getExpectedSimpleInterpolation0_9 = 1.832
  override protected def getExpectedTypicalInterpolation0_1 = 1.237
  override protected def getExpectedTypicalInterpolation0_4 = 2.296
  override protected def getExpectedTypicalInterpolation0_5 = 2.5
  override protected def getExpectedTypicalInterpolation0_9 = 3.763
  override protected def getExpectedOnePointInterpolation = 1.0
  override protected def getExpectedInterpolation2Points0_1 = 0.1

  override protected def getExpectedInterpolationExponential0 = 0
  override protected def getExpectedInterpolationExponential0_11 = 0.025867187500000003
  override protected def getExpectedInterpolationExponential0_85 = 0.66015625
  override protected def getExpectedInterpolationExponential_1 = 1.0
}
