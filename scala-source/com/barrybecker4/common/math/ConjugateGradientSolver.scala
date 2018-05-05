/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import javax.vecmath.{GMatrix, GVector}


object ConjugateGradientSolver {
  private val DEFAULT_EPS = 0.000001
  private val DEFAULT_MAX_ITERATIONS = 8
}

/**
  * Iterative conjugate-Gradient solver for a system of equations of the form Ax = b
  * Where A is a symmetric, positive definite matrix.
  * See http://en.wikipedia.org/wiki/Conjugate_gradient_method
  * Commonly applied when the matrix is large and sparse.
  * For other sort of matrices, gaussian elimination may work better.
  * @param matrix A in Ax = b
  * @param b      the b vector in Ax = b
  * @author Barry Becker
  */
class ConjugateGradientSolver(var matrix: GMatrix, var b: GVector) {
  /** the tolerable error  */
  private var eps = ConjugateGradientSolver.DEFAULT_EPS
  private var maxIterations = ConjugateGradientSolver.DEFAULT_MAX_ITERATIONS

  /** @param e some small error tolerance */
  def setEpsilon(e: Double): Unit = { eps = e }
  def setMaxIterations(num: Int): Unit = { maxIterations = num }

  def solve: GVector = {
    val zeros = new Array[Double](b.getSize)
    val initialGuess = new GVector(zeros)
    solve(initialGuess)
  }

  /** Find a solution or return the initial guess if something goes wrong.
    * @param initialGuess the initial guess for the solution x, x0
    * @return solution vector
    */
  def solve(initialGuess: GVector): GVector = {
    val x = new GVector(initialGuess)
    val tempv = new GVector(initialGuess)
    tempv.mul(matrix, initialGuess)
    val bb = new GVector(b)
    bb.sub(tempv)
    val r = new GVector(bb)
    val p = new GVector(r)
    val xnew = new GVector(p)
    val rnew = new GVector(p)
    val pnew = new GVector(p)
    val matrixMultp = new GVector(p)
    val matrixInverse = new GMatrix(matrix)
    matrixInverse.invert()
    var error = .0
    var norm = .0
    var iteration = 0
    do {
      matrixMultp.mul(matrix, p)
      val lambda = r.dot(p) / p.dot(matrixMultp)
      xnew.scaleAdd(lambda, p, x)
      rnew.scaleAdd(-lambda, matrixMultp, r)
      val alpha = -(rnew.dot(matrixMultp) / p.dot(matrixMultp))
      pnew.scaleAdd(alpha, p, rnew)
      p.set(pnew)
      r.set(rnew)
      //System.out.println("the residual = "+r.toString());
      x.set(xnew)
      //error = Math.abs(r.dot(r)); // wrong way to compute norm
      rnew.mul(r, matrixInverse)
      norm = rnew.dot(r)
      error = norm * norm
      //System.out.println("xi = "+x.toString());
      iteration += 1
      //System.out.println("The error for iteration " + iteration + " is : " + error );
    } while (error > eps && iteration < maxIterations)

    if (error > eps || error.isNaN || error.isInfinite) { // something went wrong
      throw new IllegalStateException("Unable to converge on a solution. Error = " + error)
    }
    xnew
  }
}