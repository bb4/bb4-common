/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite


/**
  * @author Barry Becker
  */
class VectorSuite extends FunSuite {
  /** instance under test */
  private var vector: Vector = _

  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(MathUtil.EPS_MEDIUM)

  /** must be at least one dim */
  test("CreateLength0Vector("){
    assertThrows[AssertionError] {
      new Vector(0)
    }
  }

  test("CreateLength1Vector") {
    vector = new Vector(1)
    vector.set(0, 1.23)
    assertResult(1.23) { vector.get(0) }
  }

  test("CreateLength2Vector(") {
    vector = new Vector(2)
    vector.set(0, 1.23)
    vector.set(1, 2.34)
    assertResult(1.23) {vector.get(0)}
    assertResult(2.34) { vector.get(1) }
  }

  test("CreateLength2VectorWithData") {
    vector = new Vector(Array[Double](1.23, 2.34))
    assertResult(1.23) { vector.get(0) }
    assertResult(2.34) { vector.get(1) }
  }

  test("DistanceTo(") {
    vector = new Vector(Array[Double](1.0, 2.0))
    val vector2 = new Vector(Array[Double](3.0, 4.0))
    assertResult(0.0) { vector.distanceTo(vector) }
    assertResult(2.8284271247461903) { vector.distanceTo(vector2) }
  }

  test("Magnitude") {
    vector = new Vector(Array[Double](3.0, 4.0))
    assertResult(5.0) { vector.magnitude }
  }

  test("DotProduct") {
    vector = new Vector(Array[Double](3.0, 4.0))
    val vector2 = new Vector(Array[Double](5.0, 12.0))
    assertResult(63.0) { vector.dot(vector2) }
  }

  test("NormalizedDotProduct(") {
    vector = new Vector(Array[Double](3.0, 4.0))
    val vector2 = new Vector(Array[Double](5.0, 12.0))
    assertResult(0.9692307692307692) { vector.normalizedDot(vector2) }
  }

  test("NormalizedDotProductWhenParallel") {
    vector = new Vector(Array[Double](3.0, 4.0))
    val vector2 = new Vector(Array[Double](3.0, 4.0))
    assertResult(1.0) { vector.normalizedDot(vector2) }
  }

  test("NormalizedDotProductWhenSmall") {
    vector = new Vector(Array[Double](0.0000003, 0.0000004))
    val vector2 = new Vector(Array[Double](0.0000005, 0.0000012))
    assertResult(0.9692307692307692) { vector.normalizedDot(vector2) }
  }

  /** This case failed from hill climbing one time */
  test("NormalizedDotProductWhenAlmostSameNegative") {
    vector = new Vector(Array[Double](-0.4409008100751817, -0.4500071951369762))
    val vector2 = new Vector(Array[Double](-0.6298583001074021, -0.6428674216242523))
    assert(1.0 === vector.normalizedDot(vector2))
    assert(1.0 === vector2.normalizedDot(vector))
  }

  test("NormalizedDotProductWhenAlmostSame2") {
    vector = new Vector(Array[Double](-1.6370338447524475, -1.6708452150399316))
    val vector2 = new Vector(Array[Double](-1.2592568036557408, -1.285265550030705))
    assert(1.0 === vector.normalizedDot(vector2))
  }
}
