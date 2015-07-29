/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math;

import javax.vecmath.GMatrix;
import javax.vecmath.GVector;

/**
 * Iterative conjugate-Gradient solver for a system of equations of the form Ax = b
 * Where A is a symmetric, positive definite matrix.
 * See http://en.wikipedia.org/wiki/Conjugate_gradient_method
 * Commonly applied when the matrix is large and sparse.
 * For other sort of matrices, gaussian elimination may work better.
 *
 * @author Barry Becker
 */
public class ConjugateGradientSolver {

    private static final double DEFAULT_EPS = 0.000001;
    private static final int DEFAULT_MAX_ITERATIONS = 8;

    /** the tolerable error  */
    private double eps = DEFAULT_EPS;
    private int maxIterations = DEFAULT_MAX_ITERATIONS;

    private GMatrix matrix;
    private GVector b;

    /**
     * Constructor
     * @param matrix A in Ax = b
     * @param b the b vector in Ax = b
     */
    public ConjugateGradientSolver(GMatrix matrix, GVector b) {
        this.matrix = matrix;
        this.b = b;
    }

    /** @param e some small error tolerance */
    public void setEpsilon(double e) {
        eps = e;
    }

    public void setMaxIterations(int num) {
        maxIterations = num;
    }

    public GVector solve() {
        double zeros[] = new double[b.getSize()];
        GVector initialGuess = new GVector(zeros);
        return solve(initialGuess);
    }

    /**
     * Find a solution or return the initial guess if something goes wrong.
     * @param initialGuess the initial guess for the solution x, x0
     * @return solution vector
     */
    public GVector solve(GVector initialGuess) {

        GVector x = new GVector( initialGuess );
        GVector tempv = new GVector( initialGuess );
        tempv.mul( matrix, initialGuess );
        GVector bb = new GVector( b );
        bb.sub( tempv );
        GVector r = new GVector( bb );
        GVector p = new GVector( r );
        GVector xnew = new GVector( p );
        GVector rnew = new GVector( p );
        GVector pnew = new GVector( p );
        GVector matrixMultp = new GVector( p );
        GMatrix matrixInverse = new GMatrix( matrix );
        matrixInverse.invert();
        double error, norm;
        int iteration = 0;

        do {
            matrixMultp.mul( matrix, p );
            double lambda = (r.dot( p ) / p.dot( matrixMultp ));
            xnew.scaleAdd( lambda, p, x );
            rnew.scaleAdd( -lambda, matrixMultp, r );
            double alpha = -(rnew.dot( matrixMultp ) / p.dot( matrixMultp));
            pnew.scaleAdd( alpha, p, rnew );
            p.set( pnew );
            r.set( rnew );
            //System.out.println("the residual = "+r.toString());
            x.set( xnew );
            //error = Math.abs(r.dot(r)); // wrong way to compute norm
            rnew.mul( r, matrixInverse );
            norm = rnew.dot( r );
            error = norm * norm;
            //System.out.println("xi = "+x.toString());
            iteration++;
            //System.out.println("The error for iteration " + iteration + " is : " + error );
        } while ( error > eps && iteration < maxIterations );

        if ( error > eps || Double.isNaN( error ) || Double.isInfinite( error ) ) {
            // something went wrong
            throw new IllegalStateException("Unable to converge on a solution. Error = " + error);
            //return initialGuess;
        }

        return xnew;
    }

}

