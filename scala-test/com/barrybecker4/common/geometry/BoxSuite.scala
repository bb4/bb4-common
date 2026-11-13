/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite


class BoxSuite extends AnyFunSuite {

  test("Construction") {
    val box = new Box(IntLocation(2, 3), IntLocation(4, 4))
    assertResult(IntLocation(2, 3)) { box.getTopLeftCorner }
    assertResult(IntLocation(4, 4)) { box.getBottomRightCorner }
  }

  test("Point Containment") {
    val box = new Box(IntLocation(2, 3), IntLocation(6, 5))
    assert(box.contains(IntLocation(4, 5)))
    assert(box.contains(IntLocation(3, 4)))
    assert(!box.contains(IntLocation(9, 7)))
    assert(box.contains(IntLocation(6, 5)))
    assert(box.contains(IntLocation(2, 3)))
    assert(box.contains(new ByteLocation(3, 4)))
  }

  test("Box Containment") {
    val box = new Box(IntLocation(2, 3), IntLocation(8, 9))
    assert(box.contains(box))
    assert(box.contains(new Box(IntLocation(3, 4), IntLocation(7, 8))))
    assert(!box.contains(new Box(IntLocation(9, 7), IntLocation(20, 30))))
    assert(!box.contains(new Box(IntLocation(1, 3), IntLocation(7,8))))
    assert(!box.contains(new Box(IntLocation(4, 5), IntLocation(7, 10))))
  }

  test("Area") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult(6) { box.getArea }
  }

  test("scaling") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val scaledBox = box.scaleBy(3)
    assertResult(6) { scaledBox.getWidth }
    assertResult(54) { scaledBox.getArea }
  }

  test("MaxDimension") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult(3) { box.getMaxDimension }
  }

  test("get Dimensions") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult((2, 3)) {
      (box.getWidth, box.getHeight)
    }
  }

  test("IsOnCorner") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assert(box.isOnCorner(IntLocation(2, 3)))
    assert(box.isOnCorner(IntLocation(2, 5)))
    assert(box.isOnCorner(IntLocation(5, 3)))
    assert(box.isOnCorner(IntLocation(5, 5)))
    assert(!box.isOnCorner(IntLocation(3, 3)))
    assert(!box.isOnCorner(IntLocation(2, 4)))
    assert(!box.isOnCorner(IntLocation(1, 3)))
  }

  test("IsOnEdge") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assert(box.isOnEdge(IntLocation(2, 4)))
    assert(box.isOnEdge(IntLocation(5, 4)))
    assert(box.isOnEdge(IntLocation(3, 3)))
    assert(box.isOnEdge(IntLocation(3, 5)))
    assert(box.isOnEdge(IntLocation(2, 3)))
    assert(!box.isOnEdge(IntLocation(3, 4)))
    // Aligned with an edge but outside the box must be false
    assert(!box.isOnEdge(IntLocation(2, 100)))
    assert(!box.isOnEdge(IntLocation(0, 3)))
  }

  test("ExpandBy") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBy(IntLocation(4, 10))
    assertResult(IntLocation(5, 10)) { newBox.getBottomRightCorner }
  }

  test("ExpandBy toward upper left") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBy(IntLocation(1, 1))
    assertResult(new Box(IntLocation(1, 1), IntLocation(5, 5))) { newBox }
  }

  test("ExpandBy toward upper right") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBy(IntLocation(1, 10))
    assertResult(new Box(IntLocation(1, 3), IntLocation(5, 10))) { newBox }
  }

  test("ExpandBy toward lower right") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBy(IntLocation(6, 10))
    assertResult(new Box(IntLocation(2, 3), IntLocation(6, 10))) { newBox }
  }

  test("ExpandBy toward lower left") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBy(IntLocation(7, 1))
    assertResult(new Box(IntLocation(2, 1), IntLocation(7, 5))) { newBox }
  }

  test("ExpandBy with location already inside (should return same box)") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBy(IntLocation(3, 4))
    assertResult(box) { newBox }
    assert(box eq newBox, "Should return the same Box instance when location is already inside")
  }

  test("ExpandBy with location on boundary (should return same box)") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBoxCorner = box.expandBy(IntLocation(2, 3))
    assertResult(box) { newBoxCorner }
    assert(box eq newBoxCorner, "Should return the same Box instance when location is on corner")
    
    val newBoxEdge = box.expandBy(IntLocation(4, 5))
    assertResult(box) { newBoxEdge }
    assert(box eq newBoxEdge, "Should return the same Box instance when location is on edge")
  }

  test("ExpandGlobally") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandGloballyBy(3, 1, 1, 6, 5)
    assertResult(IntLocation(6, 5)) { newBox.getBottomRightCorner }
    assertResult(IntLocation(1, 1)) { newBox.getTopLeftCorner }
  }

  test("ExpandGlobally on 0-based board") {
    val box = new Box(IntLocation(1, 2), IntLocation(4, 4))
    val newBox = box.expandGloballyBy(3, 0, 0, 5, 4)
    assertResult(IntLocation(0, 0)) { newBox.getTopLeftCorner }
    assertResult(IntLocation(5, 4)) { newBox.getBottomRightCorner }
  }

  test("ExpandBordersToEdge snaps all near sides independently") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBordersToEdge(3, 1, 1, 6, 5)
    // Near top (2-1<=3), left (3-1<=3), bottom (6-5<=3), and right (5-5<=3)
    assertResult(IntLocation(1, 1)) { newBox.getTopLeftCorner }
    assertResult(IntLocation(6, 5)) { newBox.getBottomRightCorner }
  }

  test("ExpandBordersToEdge near corner only expands the near sides") {
    val box = new Box(IntLocation(2, 2), IntLocation(8, 8))
    val newBox = box.expandBordersToEdge(1, 1, 1, 10, 10)
    assertResult(IntLocation(1, 1)) { newBox.getTopLeftCorner }
    assertResult(IntLocation(8, 8)) { newBox.getBottomRightCorner }
  }

  test("ExpandBordersToEdge leaves distant sides alone") {
    val box = new Box(IntLocation(5, 5), IntLocation(6, 6))
    val newBox = box.expandBordersToEdge(1, 1, 1, 20, 20)
    assertResult(box) { newBox }
  }

  test("Construction normalizes inverted corners") {
    val box = Box(5, 8, 2, 3)
    assertResult(IntLocation(2, 3)) { box.getTopLeftCorner }
    assertResult(IntLocation(5, 8)) { box.getBottomRightCorner }
  }

  test("Degenerate point box") {
    val box = new Box(IntLocation(4, 4))
    assertResult(0) { box.getWidth }
    assertResult(0) { box.getHeight }
    assert(box.contains(IntLocation(4, 4)))
    assert(box.isOnEdge(IntLocation(4, 4)))
    assert(box.isOnCorner(IntLocation(4, 4)))
  }

  test("Box.apply expand from existing box") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult(box.expandBy(IntLocation(1, 10))) { Box(box, IntLocation(1, 10)) }
  }

  test("Box Intersection") {
    val box = new Box(IntLocation(3, 4), IntLocation(9, 10))
    assertResult(Some(box)) { box.intersectWith(new Box(IntLocation(3, 4), IntLocation(9, 10))) }
    // Some(Box:(row=3, column=4) - (row=9, column=10)), but got Some(Box:(row=3, column=4) - (row=3, column=4))
    assertResult(None) { box.intersectWith(new Box(IntLocation(30, 40), IntLocation(90, 100))) }
    assertResult(Some(Box(3, 4, 6, 7))) { box.intersectWith(new Box(IntLocation(2, 2), IntLocation(6, 7))) }
    assertResult(Some(Box(7, 6, 9, 10))) { box.intersectWith(new Box(IntLocation(7, 6), IntLocation(100, 100))) }
    assertResult(Some(Box(7, 4, 9, 7))) { box.intersectWith(new Box(IntLocation(7, 3), IntLocation(11, 7))) }
    assertResult(Some(Box(3, 6, 7, 10))) { box.intersectWith(new Box(IntLocation(2, 6), IntLocation(7, 10))) }
  }

  test("ToString") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult("Box:(row=2, column=3) - (row=5, column=5)") { box.toString }
  }
}
