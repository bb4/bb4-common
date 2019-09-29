/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.function

import com.barrybecker4.common.math.MathUtil
import com.barrybecker4.common.math.Range


object FunctionInverter {
  private val EPS_BIG = .1
}

/** Use to find the inverse of a given function
  * @param functionMap the monotonically increasing values of the function to invert assuming domain is [0,1]
  * @author Barry Becker
  */
class FunctionInverter(var functionMap: Array[Double]) {

  private val length = functionMap.length
  private val lengthm1 = length - 1
  assert(functionMap(lengthm1) == 1.0, s"${functionMap(lengthm1)} was not = 1.0")

  /** Creates an inverse of the function specified with the same precision as the passed in function.
    * Assumes that function func is monotonically increasing and maps [xRange] into [yRange].
    * This can be quite inaccurate if there are not that many sample points.
    * @param xRange the extent of the domain
    * @return inverse function for specified range
    */
  def createInverseFunction(xRange: Range): Array[Double] = {
    val invFunc = new Array[Double](length)
    var j = 0
    val xMax = xRange.max
    for (i <- 0 until length) {
      val xVal = i.toDouble / lengthm1
      while (j < lengthm1 && functionMap(j) <= xVal) {
        j += 1
        if (functionMap(j - 1) > functionMap(j) + MathUtil.EPS)
          throw new IllegalStateException(
            s"""${functionMap(j - 1)}
               | was not less than ${functionMap(j)}
               | . That means the function was not monotonically increasing, as we assumed for
               |  func= ${functionMap.mkString(", ")} at position= $j)""".stripMargin)
      }
      invFunc(i) = xRange.min
      if (j > 0) {
        val fm1 = functionMap(j - 1)
        assert(xVal >= fm1)
        var denom = functionMap(j) - fm1
        val numerator = xVal - fm1
        assert(denom >= 0)
        if (denom == 0) {
          assert(numerator == 0)
          denom = 1.0
        }
        val y = ((j - 1).toDouble + numerator / denom) / lengthm1.toDouble
        invFunc(i) = xRange.min + y * xRange.getExtent
        assert(invFunc(i) < xMax + FunctionInverter.EPS_BIG, s"${invFunc(i)} was not less than $xMax")
      }
    }
    assert(invFunc(lengthm1) > xMax - FunctionInverter.EPS_BIG, s"${invFunc(lengthm1)} was not greater than $xMax")
    invFunc(lengthm1) = xMax
    invFunc
  }
}
