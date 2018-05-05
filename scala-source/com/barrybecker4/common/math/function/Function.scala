/*
 * Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
 */
package com.barrybecker4.common.math.function
import com.barrybecker4.common.math.Range


/**
  * Defines interface for generic 1-1 function f(x).
  * @author Barry Becker
  */
trait Function {

  /*** Given an x value, returns f(x)   (i.e. y)
    * @param value value to remap.
    * @return the remapped value.
    */
  def getValue(value: Double): Double

  /** @return range of x axis values */
  def getDomain: Range
}