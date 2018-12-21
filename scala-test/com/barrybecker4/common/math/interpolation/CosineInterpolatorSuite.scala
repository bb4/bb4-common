/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation


/**
  * @author Barry Becker
  */
class CosineInterpolatorSuite extends InterpolatorSuiteBase {
  override protected def createInterpolator(func: Array[Double]) = new CosineInterpolator(func)

  override protected def getExpectedSimpleInterpolation0_1 = 0.09549150281252627
  override protected def getExpectedSimpleInterpolation0_9 = 1.9045084971874737
  override protected def getExpectedTypicalInterpolation0_1 = 1.2061073738537633
  override protected def getExpectedTypicalInterpolation0_4 = 2.296
  override protected def getExpectedTypicalInterpolation0_5 = 2.5
  override protected def getExpectedTypicalInterpolation0_9 = 3.7938926261462367
  override protected def getExpectedOnePointInterpolation = 1.0
  override protected def getExpectedInterpolation2Points0_1 = 0.024471741852423234

  override protected def getExpectedInterpolationExponential0 = 0
  override protected def getExpectedInterpolationExponential0_11 = 0.03613857703250722
  override protected def getExpectedInterpolationExponential0_85 = 0.573223304703363
  override protected def getExpectedInterpolationExponential_1 = 1.0
}
