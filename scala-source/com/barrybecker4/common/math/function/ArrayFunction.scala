/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.Range
import com.barrybecker4.common.math.interpolation.{InterpolationMethod, LINEAR}

/**
  * The function is represented with an array of lookups that
  * can be interpolated using some method.
  * @param functionMap the linear function lookup table.
  * @param inverseFunctionMap The inverse lookup for the main function.
  * @param interpMethod method to use for interpolating array values
  * @author Barry Becker
  */
class ArrayFunction private(val functionMap: Array[Double],
                            val inverseFunctionMap: Array[Double],
                            val interpMethod: InterpolationMethod) extends InvertibleFunction {

  private val interpolator = interpMethod.createInterpolator(functionMap)
  private val inverseInterpolator = interpMethod.createInterpolator(inverseFunctionMap)

  /** Alternative constructor.
    * @param func the array representing function values.
    * @param interpMethod method to use when interpolating
    */
  def this(func: Array[Double], interpMethod: InterpolationMethod = LINEAR) {
    this(func, new FunctionInverter(func).createInverseFunction(Range(0, 1.0)), interpMethod)
  }

  /** Use this version of the constructor if you already know the inverse function and do not
    * want to compute it (because computing it will not be as accurate).
    * @param func function definition.
    */
  def this(func: Array[Double], inverseFunc: Array[Double]) {
    this(func, inverseFunc, LINEAR)
  }

  /** Evaluate the function at the specified point.
    * @param value an x value
    * @return y value for specified x value
    */
  override def getValue(value: Double): Double = interpolator.interpolate(value)

  override def getDomain = Range(0, 1.0)

  /** Get x for specified y.
    * @param value y value
    * @return inverse function value.
    */
  override def getInverseValue(value: Double): Double = inverseInterpolator.interpolate(value)

  /* create new one instead of having mutable method
  def setInterpolationMethod(interp: InterpolationMethod): Unit = {
    interpolator = interp.createInterpolator(functionMap)
    inverseInterpolator = interp.createInterpolator(inverseFunctionMap)
  }*/
}