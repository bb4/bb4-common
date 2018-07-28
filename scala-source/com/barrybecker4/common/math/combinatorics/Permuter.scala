/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.combinatorics

import com.barrybecker4.common.math.MathUtil

import scala.collection.mutable.ArrayBuffer

/**
  * Provides a way of iterating through all the permutations of a set of N integers (including 0).
  * If there are more than Long.MAX_VALUE permutations, this will not work.
  * The implementation does not require memory for storing the permutations since
  * only one is produced at a time.
  * @param num the number of integer elements to permute.
  * @author Barry Becker
  */
class Permuter(val num: Int) extends Iterator[List[Int]] {
  val numPermutations: Long = MathUtil.factorial(num)
  if (numPermutations < 0)
    throw new IllegalArgumentException("The number of permutations is greater than " + Long.MaxValue)
  private var hasMore = num > 0

  /** the most recently created permutation   */
  private var lastPermutation = new ArrayBuffer[Int](num)

  for (i <- 0 until num)
    lastPermutation.append(i)

  override def hasNext: Boolean = hasMore

  /** @return the next permutation*/
  override def next: List[Int] = {
    val permutation = lastPermutation
    val nextPermutation = lastPermutation.clone()
    var k = nextPermutation.size - 2
    if (k < 0) {
      hasMore = false
      return lastPermutation.toList
    }
    while (nextPermutation(k) >= nextPermutation(k + 1)) {
      k -= 1
      if (k < 0) {
        hasMore = false
        return lastPermutation.toList
      }
    }
    var len = nextPermutation.size - 1
    while ( {
      nextPermutation(k) >= nextPermutation(len)
    }) len -= 1
    swap(nextPermutation, k, len)
    val length = nextPermutation.size - (k + 1)

    for (i <- 0 until length / 2) {
      swap(nextPermutation, k + 1 + i, nextPermutation.size - i - 1)
    }
    lastPermutation = nextPermutation
    permutation.toList
  }

  /** Swap two entries in the list  */
  private def swap(permutation: ArrayBuffer[Int], i: Int, j: Int): Unit = {
    val temp = permutation(i)
    permutation(i) = permutation(j)
    permutation(j) = temp
  }
}