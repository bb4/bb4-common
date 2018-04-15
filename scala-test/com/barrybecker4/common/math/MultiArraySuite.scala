/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class MultiArraySuite extends FunSuite {
  /** instance under test */
  private var array: MultiArray = _

  /** must be at least one dim */
  test("CreateOLength0DArray") {
    val dims = new Array[Int](0)
    assertThrows[IllegalArgumentException] {
      new MultiArray(dims)
    }
  }

  /** dim lengths must be greater than 0 */
  test("Create0Length1DArray") {
    val dims = new Array[Int](1)
    assertThrows[IllegalArgumentException] {
      new MultiArray(dims)
    }
  }

  test("Create1Length1DArray") {
    val dims = Array[Int](1)
    array = new MultiArray(dims)
    assertResult(0.0) { array.get(Array[Int](0))}
    assertResult(1) { array.getNumValues }
    assertResult(0.0) { array.getRaw(0)}
  }

  test("Create2Length2DArray") {
    val dims = Array[Int](2, 2)
    array = new MultiArray(dims)
    array.set(Array[Int](1, 0), 3.4)
    assertResult(0.0) { array.get(Array[Int](0, 1))}
    assertResult(3.4) { array.get(Array[Int](1, 0))}
    assertResult(4) { array.getNumValues }
    assertResult(3.4) { array.getRaw(2)}
  }

  test("Create3DArray") {
    val dims = Array[Int](2, 3, 4)
    array = new MultiArray(dims)
    array.set(Array[Int](1, 0, 1), 3.4)
    array.set(Array[Int](1, 1, 3), 5.1)
    assertResult(3.4) { array.get(Array[Int](1, 0, 1))}
    assertResult(5.1) { array.get(Array[Int](1, 1, 3))}
    assertResult(24) { array.getNumValues }
    assertResult(3.4) { array.getRaw(13)}
  }
}