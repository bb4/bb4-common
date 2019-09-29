/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.interpolation


/**
  * @author Barry Becker
  */
class LinearInterpolator(function: Array[Double]) extends AbstractInterpolator(function) {

  override def interpolate(value: Double): Double = {
    if (value < 0 || value > 1.0) throw new IllegalArgumentException("value out of range [0, 1] :" + value)
    val len = function.length - 1
    val x = value * len.toDouble
    val index0 = x.toInt
    if (x > len) throw new IllegalArgumentException(s"$index0 is >= $len. x = $x")
    var index1 = if (index0 < len) index0 + 1
    else len
    if (len == 0) index1 = len
    val xdiff = x - index0

    if (index0 < 0) throw new IllegalArgumentException(
      "index0 must be greater than 0, but was " + index0)
    if (index1 > len) throw new IllegalArgumentException(
      "index1 must be less than the size of the array (" + function.length + "), but was " + index1)
    //System.out.println("lin: xdiff="+ xdiff
    // + " f["+index0+"]="+ function[ index0 ]  +" f["+index1+"]="+function[ index1 ] );
    (1.0 - xdiff) * function(index0) + xdiff * function(index1)
  }
}
