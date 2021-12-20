/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class BoxSuite extends AnyFunSuite {

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

  test("IsOnCorner") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assert(box.isOnCorner(IntLocation(2, 3)))
    assert(!box.isOnCorner(IntLocation(3, 3)))
    assert(box.isOnCorner(IntLocation(5, 5)))
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

  test("ExpandGlobally") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandGloballyBy(3, 6, 5)
    assertResult(IntLocation(6, 5)) { newBox.getBottomRightCorner }
    assertResult(IntLocation(1, 1)) { newBox.getTopLeftCorner }
  }

  test("ExpandBordersToEdge") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    val newBox = box.expandBordersToEdge(3, 6, 5)
    assertResult(IntLocation(5, 5)) { newBox.getBottomRightCorner }
    assertResult(IntLocation(2, 1)) { newBox.getTopLeftCorner }
  }
  test("ToString") {
    val box = new Box(IntLocation(2, 3), IntLocation(5, 5))
    assertResult("Box:(row=2, column=3) - (row=5, column=5)") { box.toString }
  }
}
