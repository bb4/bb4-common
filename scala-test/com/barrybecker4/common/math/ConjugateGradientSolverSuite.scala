/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import javax.vecmath.{GMatrix, GVector, MismatchedSizeException}
import org.junit.Assert.assertTrue
import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite
import ConjugateGradientSolverSuite._

/**
  * @author Barry Becker
  */
object ConjugateGradientSolverSuite {
  private val MATRIX_4x4 = new GMatrix(4, 4, Array[Double](3, 1, 0, 0, 1, 4, 1, 3, 0, 1, 10, 0, 0, 3, 0, 3))
}

class ConjugateGradientSolverSuite extends FunSuite {
  /** instance under test */
  var solver: ConjugateGradientSolver = _

  test("solveSimple2by2System(") {
    val A = new GMatrix(2, 2, Array[Double](4, 1, 1, 3))
    val b = new GVector(Array[Double](1, 2))
    solver = new ConjugateGradientSolver(A, b)
    val solution = solver.solve
    System.out.println("Solution = " + solution)
    val expectedSolution = new GVector(Array[Double](1.0 / 11.0, 7.0 / 11.0))
    assert(LinearUtil.appxVectorsEqual(expectedSolution, solution, 0.000001))
  }

  // Conjugant gradient can only be applied to square matrices
  test("solve3by4System: MismatchedSizeException") {
    val A = new GMatrix(3, 4, Array[Double](2, 2, -1, 1, 1, 1, 1, 1, 2, -2, 2, 3))
    val b = new GVector(Array[Double](11, 4, 7))
    solver = new ConjugateGradientSolver(A, b)
    assertThrows[MismatchedSizeException] {
      solver.solve
    }
  }

  test("solveSimple4by4System") {
    val b = new GVector(Array[Double](1, 1, 1, 1))
    solver = new ConjugateGradientSolver(MATRIX_4x4, b)
    val solution = solver.solve
    System.out.println("Solution = " + solution)
    val expectedSolution = new GVector(Array[Double](0.588235329, -0.76470588, 0.176470588, 1.098039215))
    assertTrue("Unexpected solution:" + solution, LinearUtil.appxVectorsEqual(expectedSolution, solution, 0.000001))
  }

  test("solveSimple4by4WithLowMaxIt(") {
    val b = new GVector(Array[Double](1, 1, 1, 1))
    solver = new ConjugateGradientSolver(MATRIX_4x4, b)
    solver.setEpsilon(0.00000000001)
    solver.setMaxIterations(3)
    assertThrows[IllegalStateException] { solver.solve }
  }
}
