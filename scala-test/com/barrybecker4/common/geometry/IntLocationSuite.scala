/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class IntLocationSuite extends AnyFunSuite {

  test("Construction with positive values") {
    val loc = IntLocation(10, 20)
    assertResult(10) { loc.row }
    assertResult(20) { loc.col }
  }

  test("Construction with negative values") {
    val loc = IntLocation(-10, -20)
    assertResult(-10) { loc.row }
    assertResult(-20) { loc.col }
  }

  test("Construction with zero values") {
    val loc = IntLocation(0, 0)
    assertResult(0) { loc.row }
    assertResult(0) { loc.col }
  }

  test("Construction from Location") {
    val source = ByteLocation(5, 10)
    val loc = IntLocation(source)
    assertResult(5) { loc.row }
    assertResult(10) { loc.col }
  }

  test("getX returns col, getY returns row") {
    val loc = IntLocation(5, 10)
    assertResult(10) { loc.getX }
    assertResult(5) { loc.getY }
  }

  test("incrementOnCopy with positive values") {
    val loc = IntLocation(10, 20)
    val incremented = loc.incrementOnCopy(5, 3)
    assertResult(IntLocation(15, 23)) { incremented }
  }

  test("incrementOnCopy with negative values") {
    val loc = IntLocation(10, 20)
    val incremented = loc.incrementOnCopy(-5, -10)
    assertResult(IntLocation(5, 10)) { incremented }
  }

  test("incrementOnCopy with Location") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(5, 3)
    val result = loc1.incrementOnCopy(loc2)
    assertResult(IntLocation(15, 23)) { result }
  }

  test("decrementOnCopy") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(5, 3)
    val result = loc1.decrementOnCopy(loc2)
    assertResult(IntLocation(5, 17)) { result }
  }

  test("equals with same IntLocation") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(10, 20)
    assert(loc1.equals(loc2))
    assert(loc1 == loc2)
  }

  test("equals with different IntLocation") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(11, 20)
    assert(!loc1.equals(loc2))
    assert(loc1 != loc2)
  }

  test("equals with ByteLocation having same coordinates") {
    val intloc = IntLocation(10, 20)
    val byteloc = ByteLocation(10, 20)
    assert(intloc.equals(byteloc))
    assert(intloc == byteloc)
  }

  test("equals with non-Location object") {
    val loc = IntLocation(10, 20)
    assert(!loc.equals("not a location"))
    assert(!loc.equals(null))
  }

  test("hashCode consistency") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(10, 20)
    assertResult(loc1.hashCode) { loc2.hashCode }
  }

  test("hashCode differs for different locations") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(10, 21)
    assert(loc1.hashCode != loc2.hashCode)
  }

  test("hashCode matches ByteLocation with same coordinates") {
    val intloc = IntLocation(10, 20)
    val byteloc = ByteLocation(10, 20)
    assertResult(intloc.hashCode) { byteloc.hashCode }
  }

  test("getDistanceFrom Location") {
    val loc1 = IntLocation(0, 0)
    val loc2 = IntLocation(3, 4)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom Location with negative coordinates") {
    val loc1 = IntLocation(-3, -4)
    val loc2 = IntLocation(0, 0)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom Point2D") {
    val loc = IntLocation(0, 0)
    val point = new java.awt.geom.Point2D.Double(3.0, 4.0)
    assertResult(5.0) { loc.getDistanceFrom(point) }
  }

  test("getDistanceFrom same location is zero") {
    val loc = IntLocation(10, 20)
    assertResult(0.0) { loc.getDistanceFrom(loc) }
  }

  test("toString") {
    val loc = IntLocation(10, 20)
    assertResult("(row=10, column=20)") { loc.toString }
  }

  test("toString with negative values") {
    val loc = IntLocation(-5, -10)
    assertResult("(row=-5, column=-10)") { loc.toString }
  }
}
