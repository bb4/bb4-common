/* Copyright by Barry G. Becker, 2000-2026. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite


class BoxExpansionSuite extends AnyFunSuite {

  test("includePoint expands toward upper-left") {
    assertResult((1, 1, 5, 5)) {
      BoxExpansion.includePoint(2, 3, 5, 5, 1, 1)
    }
  }

  test("includePoint leaves bounds unchanged when already inside") {
    assertResult((2, 3, 5, 5)) {
      BoxExpansion.includePoint(2, 3, 5, 5, 4, 4)
    }
  }

  test("expandGlobally clamps to caller-supplied board bounds") {
    assertResult((1, 1, 6, 5)) {
      BoxExpansion.expandGlobally(2, 3, 5, 5, 3, 1, 1, 6, 5)
    }
  }

  test("expandGlobally clamps to a 0-based board") {
    assertResult((0, 0, 5, 4)) {
      BoxExpansion.expandGlobally(1, 2, 4, 4, 3, 0, 0, 5, 4)
    }
  }

  test("snapBordersToEdge updates min row and col independently at a corner") {
    // Previously a bug: sequential mutation reset row when also snapping col.
    assertResult((1, 1, 8, 8)) {
      BoxExpansion.snapBordersToEdge(2, 2, 8, 8, 1, 1, 1, 10, 10)
    }
  }

  test("snapBordersToEdge can snap all four sides at once") {
    assertResult((1, 1, 10, 10)) {
      BoxExpansion.snapBordersToEdge(2, 2, 9, 9, 1, 1, 1, 10, 10)
    }
  }

  test("snapBordersToEdge snaps only sides within threshold") {
    assertResult((1, 5, 6, 6)) {
      BoxExpansion.snapBordersToEdge(2, 5, 6, 6, 1, 1, 1, 20, 20)
    }
  }

  test("snapBordersToEdge respects a 0-based board origin") {
    assertResult((0, 0, 8, 8)) {
      BoxExpansion.snapBordersToEdge(1, 1, 8, 8, 1, 0, 0, 10, 10)
    }
  }

  test("intersection of overlapping boxes") {
    assertResult(Some((3, 4, 5, 6))) {
      BoxExpansion.intersection(2, 3, 5, 6, 3, 4, 7, 8)
    }
  }

  test("intersection returns None when separated") {
    assertResult(None) {
      BoxExpansion.intersection(1, 1, 2, 2, 3, 3, 4, 4)
    }
  }

  test("intersection of touching edges is a line segment box") {
    assertResult(Some((2, 2, 2, 5))) {
      BoxExpansion.intersection(1, 2, 2, 5, 2, 1, 4, 5)
    }
  }
}
