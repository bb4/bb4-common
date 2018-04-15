/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import com.barrybecker4.common.format1.FormatUtil
import javax.vecmath.{GMatrix, GVector, Vector2d}

/**
  * This class implements a number of static utility functions that are useful for math.
  * Mostly linear algebra matrix solvers and the like.
  * @author Barry Becker
  */
object LinearUtil {

  /** Matrix conjugate-Gradient solver for Ax = b
    * See http://en.wikipedia.org/wiki/Conjugate_gradient_method
    * @param matrix       the matrix of linear coefficients
    * @param b            the right hand side
    * @param initialGuess the initial guess for the solution x, x0
    * @param eps          the tolerable error (eg .0000001)
    */
  def conjugateGradientSolve(matrix: GMatrix, b: GVector, initialGuess: GVector, eps: Double): GVector = {
    val solver = new ConjugateGradientSolver(matrix, b)
    solver.setEpsilon(eps)
    solver.solve(initialGuess)
  }

  /** Pretty print the matrix for debugging. */
  def printMatrix(matrix: GMatrix): Unit = {
    var i = 0
    for (i <- 0 until matrix.getNumRow) {
      for (j <- 0 until matrix.getNumCol) {
        val a = matrix.getElement(i, j)
        if (a == 0) print("  0  ")
        else print(FormatUtil.formatNumber(a) + ' ')
      }
      println()
    }
  }

  /** @return the distance between two points  */
  def distance(p1: Vector2d, p2: Vector2d): Double = {
    val dx = p2.x - p1.x
    val dy = p2.y - p1.y
    Math.sqrt(dx * dx + dy * dy)
  }

  /** Vectors are considered approximately equal if x and y components are within eps of each other.
    * @return true if approximately equal.
    */
  def appxVectorsEqual(vec1: Vector2d, vec2: Vector2d, eps: Double): Boolean =
    Math.abs(vec1.x - vec2.x) < eps && Math.abs(vec1.y - vec2.y) < eps

  /** @return true if the 2 vectors have RMS error, eps, of each other */
  def appxVectorsEqual(vec1: GVector, vec2: GVector, eps: Double): Boolean = {
    assert(vec1.getSize == vec2.getSize)
    var totalDiff: Double = 0
    for (i <- 0 until vec1.getSize) {
      val diff = vec2.getElement(i) - vec1.getElement(i)
      totalDiff += (diff * diff)
    }
    Math.sqrt(totalDiff) < eps
  }
}