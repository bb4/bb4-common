/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math.combinatorics

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class PermuterSuite extends FunSuite {
  /** instance under test */
  private var permuter: Permuter = _

  test("Permute0") {
    permuter = new Permuter(0)
    assert(!permuter.hasNext)
  }

  test("Permute1") {
    permuter = new Permuter(1)
    assert(permuter.hasNext)
    assertResult(List(0)) { permuter.next }
    assert(!permuter.hasNext)
  }

  test("Permute2") {
    permuter = new Permuter(2)
    assert(permuter.hasNext)
    assertResult(List(0, 1)) { permuter.next }
    assertResult(List(1, 0)) { permuter.next }
    assert(!permuter.hasNext)
  }

  /** permutations of  1, 2, 3 */
  test("Permute3") {
    permuter = new Permuter(3)
    assert(permuter.hasNext)
    assertResult(List(0, 1, 2)) { permuter.next }
    assertResult(List(0, 2, 1)) { permuter.next }
    assertResult(List(1, 0, 2)) { permuter.next }
    assertResult(List(1, 2, 0)) { permuter.next }
    assertResult(List(2, 0, 1)) { permuter.next }
    assertResult(List(2, 1, 0)) { permuter.next }
    assert(!permuter.hasNext)
  }

  /** permutations of 1, 2, 3, 4, 5. There will be 120 of them */
  test("Permute5") {
    permuter = new Permuter(5)
    assert(permuter.hasNext)
    assertResult(List(0, 1, 2, 3, 4)) { permuter.next }

    for (i <- 0 until 119)
      permuter.next
    assertResult(List(4, 3, 2, 1, 0)) { permuter.next }
    assert(!permuter.hasNext)
  }

  /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
  test("PermuteReallyBig") {
    permuter = new Permuter(10)
    assert(permuter.hasNext)
    permuter.next
    assertResult(10) { permuter.next.size }
    assert(permuter.hasNext)
  }

  test("PermuteReallyReallyBig") {
    permuter = new Permuter(20)
    assert(permuter.hasNext)
    permuter.next
    assertResult(20) { permuter.next.size }
    assertResult(List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 17, 19)) {permuter.next}
    assertResult(List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 17)) {permuter.next}
    assert(permuter.hasNext)
  }

  test("PermuteTooBig") {
    assertThrows[IllegalArgumentException] {
      permuter = new Permuter(30)
    }
  }
}
