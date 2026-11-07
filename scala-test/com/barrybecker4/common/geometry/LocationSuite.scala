/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite

import java.awt.geom.Point2D


class LocationSuite extends AnyFunSuite {

  test("equals reflexivity") {
    val loc = IntLocation(10, 20)
    assert(loc.equals(loc))
  }

  test("equals symmetry") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(10, 20)
    assert(loc1.equals(loc2))
    assert(loc2.equals(loc1))
  }

  test("equals transitivity") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(10, 20)
    val loc3 = IntLocation(10, 20)
    assert(loc1.equals(loc2))
    assert(loc2.equals(loc3))
    assert(loc1.equals(loc3))
  }

  test("equals consistency with hashCode") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(10, 20)
    assert(loc1.equals(loc2))
    assertResult(loc1.hashCode) { loc2.hashCode }
  }

  test("equals with different types but same coordinates") {
    val intloc = IntLocation(10, 20)
    val byteloc = ByteLocation(10, 20)
    assert(intloc.equals(byteloc))
    assert(byteloc.equals(intloc))
    assertResult(intloc.hashCode) { byteloc.hashCode }
  }

  test("equals with null") {
    val loc = IntLocation(10, 20)
    assert(!loc.equals(null))
  }

  test("equals with non-Location") {
    val loc = IntLocation(10, 20)
    assert(!loc.equals("string"))
    assert(!loc.equals(42))
  }

  test("hashCode formula") {
    val loc = IntLocation(10, 20)
    assertResult(100 * 10 + 20) { loc.hashCode }
  }

  test("hashCode with negative values") {
    val loc = IntLocation(-10, -20)
    assertResult(100 * (-10) + (-20)) { loc.hashCode }
  }

  test("hashCode collision for different locations") {
    // Locations with row=1,col=100 and row=2,col=0 both have hashCode 200
    val loc1 = IntLocation(1, 100)
    val loc2 = IntLocation(2, 0)
    assertResult(200) { loc1.hashCode }
    assertResult(200) { loc2.hashCode }
    assert(!loc1.equals(loc2))
  }

  test("getDistanceFrom same location") {
    val loc = IntLocation(5, 10)
    assertResult(0.0) { loc.getDistanceFrom(loc) }
  }

  test("getDistanceFrom horizontal distance") {
    val loc1 = IntLocation(0, 0)
    val loc2 = IntLocation(0, 5)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom vertical distance") {
    val loc1 = IntLocation(0, 0)
    val loc2 = IntLocation(5, 0)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom diagonal distance") {
    val loc1 = IntLocation(0, 0)
    val loc2 = IntLocation(3, 4)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom with negative coordinates") {
    val loc1 = IntLocation(-3, -4)
    val loc2 = IntLocation(0, 0)
    assertResult(5.0) { loc1.getDistanceFrom(loc2) }
  }

  test("getDistanceFrom Point2D at origin") {
    val loc = IntLocation(3, 4)
    val point = new Point2D.Double(0.0, 0.0)
    val expected = Math.sqrt(4.0 * 4.0 + 3.0 * 3.0)
    assertResult(expected) { loc.getDistanceFrom(point) }
  }

  test("getDistanceFrom Point2D same Cartesian position") {
    val loc = IntLocation(5, 10)
    val point = new Point2D.Double(10.0, 5.0)
    assertResult(0.0) { loc.getDistanceFrom(point) }
  }

  test("getDistanceFrom Point2D with fractional coordinates") {
    val loc = IntLocation(0, 0)
    val point = new Point2D.Double(3.5, 4.5)
    val expected = Math.sqrt(3.5 * 3.5 + 4.5 * 4.5)
    assert(Math.abs(loc.getDistanceFrom(point) - expected) < 1e-9)
  }

  test("incrementOnCopy creates new instance") {
    val loc = IntLocation(10, 20)
    val incremented = loc.incrementOnCopy(5, 3)
    assertResult(IntLocation(10, 20)) { loc }
    assertResult(IntLocation(15, 23)) { incremented }
  }

  test("incrementOnCopy with zero change") {
    val loc = IntLocation(10, 20)
    val result = loc.incrementOnCopy(0, 0)
    assertResult(IntLocation(10, 20)) { result }
    assert(!result.eq(loc))
  }

  test("incrementOnCopy with Location parameter") {
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

  test("decrementOnCopy with negative location") {
    val loc1 = IntLocation(10, 20)
    val loc2 = IntLocation(-5, -3)
    val result = loc1.decrementOnCopy(loc2)
    assertResult(IntLocation(15, 23)) { result }
  }

  test("toString format") {
    val loc = IntLocation(10, 20)
    assertResult("(row=10, column=20)") { loc.toString }
  }

  test("toString with zero values") {
    val loc = IntLocation(0, 0)
    assertResult("(row=0, column=0)") { loc.toString }
  }

  test("toString with negative values") {
    val loc = IntLocation(-5, -10)
    assertResult("(row=-5, column=-10)") { loc.toString }
  }
}
