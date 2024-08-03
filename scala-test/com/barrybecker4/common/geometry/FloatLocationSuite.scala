/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class FloatLocationSuite extends AnyFunSuite {

  test("distance") {
    val loc = FloatLocation(2.3f, 3.5f)
    assertResult(2.8178004862628763) { loc.distance(FloatLocation(1, 1)) }
    assertResult(2.8178004862628763) { loc.distance(FloatLocation(1f, 1f)) }
  }

  test("midPoint") {
    val loc = FloatLocation(2.3f, 3.5f)
    assertResult(FloatLocation(1.65f, 2.25f)) { loc.midPoint(FloatLocation(1, 1)) }
  }

  test("centroid") {
    val locations = Seq(FloatLocation(2, 3), FloatLocation(1, 7), FloatLocation(5, 9))
    val centroid = FloatLocation.centroid(locations)
    assertResult(centroid) {
      FloatLocation(2.6666667f, 6.3333335f)
    }
  }

}
