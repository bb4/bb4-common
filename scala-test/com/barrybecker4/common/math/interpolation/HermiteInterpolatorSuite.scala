/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation

/**
  * @author Barry Becker
  */
class HermiteInterpolatorSuite extends InterpolatorSuiteBase {
  override protected def createInterpolator(func: Array[Double]) = new HermiteInterpolator(func)

  override protected def getExpectedSimpleInterpolation0_1 = 0.136
  override protected def getExpectedSimpleInterpolation0_9 = 1.864
  override protected def getExpectedTypicalInterpolation0_1 = 1.2265
  override protected def getExpectedTypicalInterpolation0_4 = 2.296
  override protected def getExpectedTypicalInterpolation0_5 = 2.5
  override protected def getExpectedTypicalInterpolation0_9 = 3.7735
  override protected def getExpectedOnePointInterpolation = 1.0
  override protected def getExpectedInterpolation2Points0_1 = 0.064
}