/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import java.math.BigInteger
import javax.vecmath.Point2d
import scala.util.Random
import MathUtil.{bigFactorial, bigPermutation}

/**
  * Some supplemental mathematics routines.
  * @author Barry Becker
  */
object MathUtil {
  /** Java double precision cannot accurately represent more than 16 decimal places */
  val EPS = 0.0000000000000001
  val EPS_MEDIUM = 0.0000000001
  val EPS_BIG = 0.0001
  val RANDOM = new Random(1)

  /** Used in calculating log base 10. */
  private val LOG10SCALE = 1.0 / Math.log(10.0)

  /** @param v the value to find log base 10 of.
    * @return log base 10 of the specified value.
    */
  def log10(v: Double): Double = Math.log(v) * LOG10SCALE

  /** @param v the value to find 10 to the power of
    * @return 10 to the val power.
    */
  def exp10(v: Double): Double = Math.pow(10.0, v)

  /** @return the greatest common divisor of 2 longs (may be negative).*/
  def gcd(a: Long, b: Long): Long = {
    if (a == 0 && b == 0) return 1L
    if (a < 0) return gcd(-a, b)
    if (b < 0) return gcd(a, -b)
    if (a == 0) return b
    if (b == 0) return a
    if (a == b) return a
    if (b < a) return gcd(b, a)
    gcd(a, b % a)
  }

  /** @param a numberator
    * @param b denominator
    * @return the least common multiple of a and b
    */
  def lcm(a: Long, b: Long): Long = Math.abs(a * b) / gcd(a, b)

  /** Find the least common multiple of specified values.
    * @param values values to find least common multiple of
    * @return the least common multiple of values[0], values[1],... values[i].
    */
  def lcm(values: Array[Int]): Long = {
    var result: Long = 1
    for (v <- values) {
      result = lcm(result, v)
    }
    result
  }

  /** Implementation of c(m,n)
    * @param m total number of items
    * @param n number of items to choose where order does not matter
    * @return number of combinations
    */
  def combination(m: Int, n: Int): BigInteger = {
    val diff = m - n
    var numerator: BigInteger = null
    var denominator: BigInteger = null
    if (n > diff) {
      numerator = bigPermutation(m, n)
      denominator = bigFactorial(diff)
    }
    else {
      numerator = bigPermutation(m, diff)
      denominator = bigFactorial(n)
    }
    numerator.divide(denominator)
  }

  /** factorial function.
    * 0! = 1 (http://www.zero-factorial.com/whatis.html)
    * This could be a recursive function, but it would be slow and run out of memory for large num.
    * @param num number to take factorial of
    * @return num!
    */
  def factorial(num: Int): Long = {
    assert(num >= 0)
    if (num == 0) return 1
    var ct = num
    var f: Long = 1
    while (ct > 1) {
      f *= ct
      ct -= 1
    }
    f
  }

  def bigFactorial(num: Int): BigInteger = {
    assert(num >= 0)
    if (num == 0) return BigInteger.ONE
    var ct = new BigInteger(Integer.toString(num))
    var f = BigInteger.ONE
    while (ct.compareTo(BigInteger.ONE) > 0) {
      f = f.multiply(ct)
      ct = ct.subtract(BigInteger.ONE)
    }
    f
  }

  /** Permutation function computes a!/b!.
    * 0! = 1 (http://www.zero-factorial.com/whatis.html)
    * @param a number of items to select from
    * @param b number of items to select order being important.
    * @return a!/b!
    */
  def permutation(a: Int, b: Int): Long = {
    assert(a > 0)
    assert(a > b)
    var f = a
    var anew = a - 1
    while (anew > b) {
      f *= anew
      anew -= 1
    }
    f
  }

  /** permutation function computes a!/b!.
    * 0! = 1 (http://www.zero-factorial.com/whatis.html)
    * @param a number of items to select from
    * @param b number of items to select order being important.
    * @return a!/b!
    */
  def bigPermutation(a: Int, b: Int): BigInteger = {
    assert(a > 0)
    assert(a > b)
    var f = new BigInteger(Integer.toString(a))
    var anew = a - 1
    //BigInteger anew = new BigInteger(a-1);
    while (anew > b) {
      f = f.multiply(new BigInteger(Integer.toString(anew)))
      anew -= 1
    }
    f
  }

  def getDirectionTo(fromPoint: Point2d, toPoint: Point2d): Double = {
    val xDiff = toPoint.x - fromPoint.x
    val yDiff = toPoint.y - fromPoint.y
    Math.atan2(yDiff, xDiff)
  }
}