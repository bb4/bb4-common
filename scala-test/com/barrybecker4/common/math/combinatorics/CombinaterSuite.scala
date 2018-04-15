/*
 * Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
 */

package com.barrybecker4.common.math.combinatorics

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class CombinaterSuite extends FunSuite {
  /** instance under test */
  private var combinater: Combinater = _

  test("Combinations0") {
    combinater = new Combinater(0)
    assert(!combinater.hasNext)
  }

  test("Combinations1") {
    combinater = new Combinater(1)
    assert(combinater.hasNext)
    assertResult(List(0)) { combinater.next }
    assert(!combinater.hasNext)
  }

  test("Combinations2") {
    combinater = new Combinater(2)
    assert(combinater.hasNext)
    assertResult(List(0)) { combinater.next }
    assertResult(List(1)) { combinater.next }
    assertResult( List(0, 1)) { combinater.next }
    assert(!combinater.hasNext)
  }

  /** subsets of  1, 2, 3 */
  test("Combinations3") {
    combinater = new Combinater(3)
    assert(combinater.hasNext)
    assertResult(List(0)) { combinater.next }
    assertResult(List(1)) { combinater.next}
    assertResult(List(0, 1)) { combinater.next}
    assertResult(List(2)) { combinater.next}
    assertResult(List(0, 2)) { combinater.next}
    assertResult(List(1, 2)) { combinater.next}
    assertResult(List(0, 1, 2)) { combinater.next}
    assert(!combinater.hasNext)
  }

  /** permutations of 1, 2, 3, 4, 5. There will be 120 of them */
  test("Combinations5") {
    combinater = new Combinater(5)
    assert(combinater.hasNext)
    assertResult(List(0)) { combinater.next }
    for (i <- 0 until 28)
     combinater.next

    assertResult(List(1, 2, 3, 4)) { combinater.next }
    assertResult(List(0, 1, 2, 3, 4)) { combinater.next }
    assert(!combinater.hasNext)
  }

  /** an error if we request more combinations than there are */
  test("IteratePastEnd") {
    combinater = new Combinater(5)
    assertThrows[NoSuchElementException] {
      for (i <- 0 until 120)
        combinater.next
    }
  }

  /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
  test("CombinationsReallyBig") {
    combinater = new Combinater(10)
    assert(combinater.hasNext)
    combinater.next
    assertResult(1) { combinater.next.size}
    assert(combinater.hasNext)
  }

  test("CombinationsReallyReallyBig") {
    combinater = new Combinater(20)
    assert(combinater.hasNext)
    combinater.next
    assertResult(1) { combinater.next.size }
    assertResult(List(0, 1)) { combinater.next }
    assertResult( List(2)) { combinater.next }
    assert(combinater.hasNext)
  }

  test("CombinationsTooBig") {
    assertThrows[IllegalArgumentException] {
      new Combinater(90)
    }
  }
}
