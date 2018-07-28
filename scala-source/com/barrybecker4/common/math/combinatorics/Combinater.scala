/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.combinatorics

import scala.collection.mutable.{ArrayBuffer, ListBuffer}


/**
  * Provides a way of iterating through all the combinations of a set of N integers.
  * For example, if N = 3, then the set of N integers is {0, 1, 2} and the all subsets are
  * {0}, {1}, {0, 1}, {2}, {2, 0}, {2, 1}, {2, 1, 0}
  * Note that the order of the elements in the subsets does not matter.
  * The implementation does not require memory for storing the permutations since  only one is produced at a time.
  * This algorithm will only work as long as the number of subsets is less than Long.MAX_VALUE.
  * In other words, N must be less than 60 since 2&caret;60 is close to Long.MAX_VALUE.
  * @param num the number of integer elements to permute.
  * @author Barry Becker
  */
class Combinater(num: Int) extends Iterator[List[Int]] {

  val numCombinations = Math.pow(2, num).toLong - 1
  // val numCombinations = (1 to num).map(x => MathUtil.combination(num, x).longValue()).sum

  if (numCombinations >= Long.MaxValue - 1)
    throw new IllegalArgumentException("The number of combinations is greater than " + Long.MaxValue)

  /** acts as a bit set to find all combinations. */
  private var counter = 0L
  private var hasMore = num > 0

  override def hasNext: Boolean = hasMore

  /** @return the next permutation */
  override def next: List[Int] = {
    counter += 1
    if (!hasMore)
      throw new NoSuchElementException("There are no more combinations. There were only " + numCombinations)

    hasMore = counter < numCombinations
    val subset = new ArrayBuffer[Int](num)
    val bits = counter.toBinaryString
    for (i <- bits.length - 1 to 0 by -1)
      if (bits.charAt(i) == '1') subset.append(bits.length - i - 1)
    subset.toList
  }
}