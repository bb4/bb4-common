/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.FunSuite


/**
  * @author Barry Becker
  */
class BoxSuite extends FunSuite {

  test("Construction") {
    val box = new Box(IntLocation(2, 3), IntLocation(4, 4))
    assertResult(IntLocation(2, 3)) { box.getTopLeftCorner }
    assertResult(IntLocation(4, 4)) { box.getBottomRightCorner }
  }

  test("Containment") {
    val box = new Box(IntLocation(2, 3), IntLocation(6, 5))
    assert(box.contains(IntLocation(4, 5)))
    assert(box.contains(IntLocation(3, 4)))
    assert(!box.contains(IntLocation(9, 7)))
    assert(box.contains(IntLocation(6, 5)))
    assert(box.contains(IntLocation(2, 3)))
    assert(box.contains(new ByteLocation(3, 4)))
  }

  test("Area") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult(6) { box.getArea }
  }

  test("MaxDimension") {
    var box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult(3) { box.getMaxDimension }
  }

  test("IsOnCorner") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assert(box.isOnCorner(IntLocation(2, 3)))
    assert(!box.isOnCorner(IntLocation(3, 3)))
    assert(box.isOnCorner(IntLocation(5, 5)))
  }

  test("ExpandBy") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    box.expandBy(IntLocation(4, 10))
    assertResult(IntLocation(5, 10)) { box.getBottomRightCorner }
  }

  test("ExpandGlobally") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    box.expandGloballyBy(3, 6, 5)
    assertResult(IntLocation(6, 5)) { box.getBottomRightCorner }
    assertResult(IntLocation(1, 1)) { box.getTopLeftCorner }
  }

  test("ToString") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult("Box:(row=2, column=3) - (row=5, column=5)") { box.toString }
  }
}
