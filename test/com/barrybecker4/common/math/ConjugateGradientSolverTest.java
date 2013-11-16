package com.barrybecker4.common.math;

import org.junit.Test;

import javax.vecmath.GMatrix;
import javax.vecmath.GVector;

import static org.junit.Assert.assertTrue;


/**
 * @author Barry Becker
 */
public class ConjugateGradientSolverTest {


    /** instance under test */
    ConjugateGradientSolver solver;

    @Test
    public void solveSimpleSystem() {

        GMatrix A = new GMatrix(2, 2, new double[] {4, 1, 1, 3});
        GVector b = new GVector(new double[] {1, 2});

        solver = new ConjugateGradientSolver(A, b);

        GVector solution = solver.solve();

        System.out.println("Solution = "+ solution);
        GVector expectedSolution = new GVector(new double[] {1.0/11.0, 7.0/11.0});

        assertTrue("Unexpected solution:" + solution,
                LinearUtil.appxVectorsEqual(expectedSolution, solution, 0.000001));

    }

}
