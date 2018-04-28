/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

/**
  * Defines interface for generic 1-1 function f(x).
  * @author Barry Becker
  */
trait InvertibleFunction extends Function {

  /** Given a y value (i.e. f(x)) return the corresponding x value.
    * Inverse of the above.
    * @param value some y value.
    * @return the x value for the specified y value
    */
  def getInverseValue(value: Double): Double
}