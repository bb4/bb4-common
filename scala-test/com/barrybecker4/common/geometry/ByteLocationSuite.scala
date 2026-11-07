/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite


class ByteLocationSuite extends AnyFunSuite {

  test("Construction with valid byte values") {
    val loc = ByteLocation(10.toByte, 20.toByte)
    assertResult(10) { loc.row }
    assertResult(20) { loc.col }
  }

  test("Construction with negative byte values") {
    val loc = ByteLocation((-10).toByte, (-20).toByte)
    assertResult(-10) { loc.row }
    assertResult(-20) { loc.col }
  }

  test("Construction with boundary byte values") {
    val loc1 = ByteLocation(127.toByte, 127.toByte)
    assertResult(127) { loc1.row }
    assertResult(127) { loc1.col }
    
    val loc2 = ByteLocation((-127).toByte, (-127).toByte)
    assertResult(-127) { loc2.row }
    assertResult(-127) { loc2.col }
  }

  test("Int constructor with valid values") {
    val loc = new ByteLocation(10, 20)
    assertResult(10) { loc.row }
    assertResult(20) { loc.col }
  }

  test("Int constructor with negative values") {
    val loc = new ByteLocation(-10, -20)
    assertResult(-10) { loc.row }
    assertResult(-20) { loc.col }
  }

  test("Int constructor at boundary values") {
    val loc1 = new ByteLocation(127, 127)
    assertResult(127) { loc1.row }
    assertResult(127) { loc1.col }
    
    val loc2 = new ByteLocation(-127, -127)
    assertResult(-127) { loc2.row }
    assertResult(-127) { loc2.col }
  }

  test("Int constructor with value 128 truncates to -128 and fails assertion") {
    // Int 128 converts to Byte -128 via toByte, then assertion checks abs(-128) < 128, which is false
    // This documents current truncation behavior (related to issue #53)
    assertThrows[AssertionError] {
      new ByteLocation(128, 0)
    }
  }

  test("Int constructor with value -128 fails assertion") {
    // -128 as a byte has abs(-128) = 128, which is not < 128
    assertThrows[AssertionError] {
      new ByteLocation(-128, 0)
    }
  }

  test("Int constructor with large positive value 200 truncates to -56") {
    // 200 as Int.toByte = -56, which passes assertion since abs(-56) < 128
    // This documents current truncation behavior (related to issue #53)
    val loc = new ByteLocation(200, 0)
    assertResult(-56) { loc.row }
  }

  test("Int constructor with value 256 truncates to 0 and passes") {
    // 256.toByte = 0, so ByteLocation(0, 10) passes assertion
    // This documents current truncation behavior (related to issue #53)
    val loc = new ByteLocation(256, 10)
    assertResult(0) { loc.row }
    assertResult(10) { loc.col }
  }

  test("Int constructor with col=128 truncates and fails assertion") {
    assertThrows[AssertionError] {
      new ByteLocation(0, 128)
    }
  }

  test("getX returns brow (axis quirk documented in issue #66)") {
    val loc = ByteLocation(5, 10)
    assertResult(5) { loc.getX }
    assertResult(10) { loc.getY }
  }

  test("incrementOnCopy with positive values") {
    val loc = new ByteLocation(10, 20)
    val incremented = loc.incrementOnCopy(5, 3)
    assertResult(15) { incremented.row }
    assertResult(23) { incremented.col }
  }

  test("incrementOnCopy with negative values") {
    val loc = new ByteLocation(10, 20)
    val incremented = loc.incrementOnCopy(-5, -10)
    assertResult(5) { incremented.row }
    assertResult(10) { incremented.col }
  }

  test("incrementOnCopy with Location") {
    val loc1 = ByteLocation(10, 20)
    val loc2 = IntLocation(5, 3)
    val result = loc1.incrementOnCopy(loc2)
    assertResult(15) { result.row }
    assertResult(23) { result.col }
  }

  test("decrementOnCopy") {
    val loc1 = ByteLocation(10, 20)
    val loc2 = IntLocation(5, 3)
    val result = loc1.decrementOnCopy(loc2)
    assertResult(5) { result.row }
    assertResult(17) { result.col }
  }

  test("equals with same ByteLocation") {
    val loc1 = ByteLocation(10, 20)
    val loc2 = ByteLocation(10, 20)
    assert(loc1.equals(loc2))
    assert(loc1 == loc2)
  }

  test("equals with different ByteLocation") {
    val loc1 = ByteLocation(10, 20)
    val loc2 = ByteLocation(11, 20)
    assert(!loc1.equals(loc2))
    assert(loc1 != loc2)
  }

  test("equals with IntLocation having same coordinates") {
    val byteloc = ByteLocation(10, 20)
    val intloc = IntLocation(10, 20)
    assert(byteloc.equals(intloc))
    assert(byteloc == intloc)
  }

  test("equals with non-Location object") {
    val loc = ByteLocation(10, 20)
    assert(!loc.equals("not a location"))
    assert(!loc.equals(10))
  }

  test("hashCode consistency") {
    val loc1 = ByteLocation(10, 20)
    val loc2 = ByteLocation(10, 20)
    assertResult(loc1.hashCode) { loc2.hashCode }
  }

  test("hashCode differs for different locations") {
    val loc1 = ByteLocation(10, 20)
    val loc2 = ByteLocation(10, 21)
    assert(loc1.hashCode != loc2.hashCode)
  }

  test("getDistanceFrom Location") {
    val loc1 = ByteLocation(0, 0)
    val loc2 = IntLocation(3, 4)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom Point2D (axis quirk documented in issue #66)") {
    val loc = ByteLocation(3, 4)
    val point = new java.awt.geom.Point2D.Double(3.0, 4.0)
    // Current implementation uses row-X, col-Y in distance calculation
    assertResult(0.0) { loc.getDistanceFrom(point) }
  }

  test("toString") {
    val loc = ByteLocation(10, 20)
    assertResult("(row=10, column=20)") { loc.toString }
  }
}
