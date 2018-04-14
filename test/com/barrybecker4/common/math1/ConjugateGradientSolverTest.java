package com.barrybecker4.common.math1;

import org.junit.Test;

import javax.vecmath.GMatrix;
import javax.vecmath.GVector;
import javax.vecmath.MismatchedSizeException;

import static org.junit.Assert.assertTrue;


/**
 * @author Barry Becker
 */
public class ConjugateGradientSolverTest {

    /** instance under test */
    ConjugateGradientSolver solver;

    private static final GMatrix MATRIX_4x4 = new GMatrix(4, 4, new double[] {
            3, 1, 0, 0,
            1, 4, 1, 3,
            0, 1, 10, 0,
            0, 3, 0, 3
    });

    @Test
    public void solveSimple2by2System() {

        GMatrix A = new GMatrix(2, 2, new double[] {4, 1, 1, 3});
        GVector b = new GVector(new double[] {1, 2});

        solver = new ConjugateGradientSolver(A, b);

        GVector solution = solver.solve();

        System.out.println("Solution = "+ solution);
        GVector expectedSolution = new GVector(new double[] {1.0/11.0, 7.0/11.0});

        assertTrue("Unexpected solution:" + solution,
                LinearUtil.appxVectorsEqual(expectedSolution, solution, 0.000001));
    }

    /* didn't work. typo?
    @Test
    public void solveSimple3by3System() {

        GMatrix A = new GMatrix(3, 3, new double[] {
                5, 4, -1,
                0, 10, -3,
                0, 0, 1
        });
        GVector b = new GVector(new double[] {0, 11, 3});

        solver = new ConjugateGradientSolver(A, b);

        GVector solution = solver.solve();

        System.out.println("Solution = "+ solution);
        GVector expectedSolution = new GVector(new double[] {-1, 2, 3});

        assertTrue("Unexpected solution:" + solution,
                LinearUtil.appxVectorsEqual(expectedSolution, solution, 0.0000001));
    }*/


    // Conjugant gradient can only be applied to square matrices
    @Test (expected = MismatchedSizeException.class)
    public void solve3by4System() {
        GMatrix A = new GMatrix(3, 4, new double[] {
                2, 2, -1, 1,
                1, 1, 1, 1,
                2, -2, 2, 3
        });
        GVector b = new GVector(new double[] {11, 4, 7});
        solver = new ConjugateGradientSolver(A, b);
        solver.solve();
    }

    @Test
    public void solveSimple4by4System() {

        GVector b = new GVector(new double[] {1, 1, 1, 1});
        solver = new ConjugateGradientSolver(MATRIX_4x4, b);

        GVector solution = solver.solve();

        System.out.println("Solution = "+ solution);

        GVector expectedSolution = new GVector(new double[] {
                0.588235329,
                -0.76470588,
                0.176470588,
                1.098039215
        });

        assertTrue("Unexpected solution:" + solution,
                LinearUtil.appxVectorsEqual(expectedSolution, solution, 0.000001));
    }

    @Test (expected = IllegalStateException.class)
    public void solveSimple4by4WithLowMaxIt() {
        GVector b = new GVector(new double[] {1, 1, 1, 1});
        solver = new ConjugateGradientSolver(MATRIX_4x4, b);
        solver.setEpsilon(0.00000000001);
        solver.setMaxIterations(3);

        GVector solution = solver.solve();
    }
}
