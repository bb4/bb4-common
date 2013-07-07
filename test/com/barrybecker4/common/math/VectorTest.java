// Copyright by Barry G. Becker, 2012. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math;

import junit.framework.TestCase;


/**
 * @author Barry Becker
 */
public class VectorTest extends TestCase {

    /** instance under test */
    private Vector vector;

    /** must be at least one dim */
    public void testCreateLength0Vector() {

        try {
            new Vector(0);
            fail();
        } catch (AssertionError e) {
            // success
        }
    }

    public void testCreateLength1Vector() {

        vector = new Vector(1);
        vector.set(0, 1.23);

        assertEquals("Unexpected value.", 1.23, vector.get(0));
    }

    public void testCreateLength2Vector() {

        vector = new Vector(2);
        vector.set(0, 1.23);
        vector.set(1, 2.34);

        assertEquals("Unexpected value at 0.", 1.23, vector.get(0));
        assertEquals("Unexpected value at 1.", 2.34, vector.get(1));
    }

    public void testCreateLength2VectorWithData() {
        vector = new Vector(new double[] {1.23, 2.34});

        assertEquals("Unexpected value at 0.", 1.23, vector.get(0));
        assertEquals("Unexpected value at 1.", 2.34, vector.get(1));
    }


    public void testDistanceTo() {

        vector = new Vector(new double[] {1.0, 2.0});
        Vector vector2 = new Vector(new double[] {3.0, 4.0});

        assertEquals("Unexpected distance to itself.",
                0.0, vector.distanceTo(vector));
        assertEquals("Unexpected distance to vector2.",
                2.8284271247461903, vector.distanceTo(vector2));
    }

    public void testMagnitude() {

        vector = new Vector(new double[] {3.0, 4.0});
        assertEquals("Unexpected magnitude.",
                5.0, vector.magnitude());
    }

    public void testDotProduct() {

        vector = new Vector(new double[] {3.0, 4.0});
        Vector vector2 = new Vector(new double[] {5.0, 12.0});
        assertEquals("Unexpected dot product.",
                63.0, vector.dot(vector2));
    }

    public void testNormalizedDotProduct() {

        vector = new Vector(new double[] {3.0, 4.0});
        Vector vector2 = new Vector(new double[] {5.0, 12.0});
        assertEquals("Unexpected normalizedDot product.",
                0.9692307692307692, vector.normalizedDot(vector2));
    }

    public void testNormalizedDotProductWhenParallel() {

        vector = new Vector(new double[] {3.0, 4.0});
        Vector vector2 = new Vector(new double[] {3.0, 4.0});
        assertEquals("Unexpected normalizedDot product.",
                1.0, vector.normalizedDot(vector2));
    }

    public void testNormalizedDotProductWhenSmall() {

        vector = new Vector(new double[] {0.0000003, 0.0000004});
        Vector vector2 = new Vector(new double[] {0.0000005, 0.0000012});
        assertEquals("Unexpected normalizedDot product.",
                0.9692307692307692, vector.normalizedDot(vector2));
    }


    //Normalized Dot product, 1.0000000000000002, was outside expected range.
    // Dot=0.5670000000000002 div=0.5670000000000001
    // for v1=-0.4409008100751817 -0.4500071951369762 v2=-0.6298583001074021 -0.6428674216242523
    // magThis=0.63 magB=0.9000000000000001
    // for v1=-0.4409008100751817 -0.4500071951369762 v2=-0.6298583001074021 -0.6428674216242523  magThis=0.63 magB=0.9000000000000001

    // my test = magThis=0.8909080052121578 magB=1.2727257217316543 dot=1.133881533930152  divisor=1.133881533930152



    /** This case failed from hill climbing one time */
    public void testNormalizedDotProductWhenAlmostSameNegative() {

        vector = new Vector(new double[] {-0.4409008100751817, -0.4500071951369762});
        Vector vector2 = new Vector(new double[] {-0.6298583001074021, -0.6428674216242523});
        assertEquals("Unexpected normalizedDot product.",
                1.0, vector.normalizedDot(vector2), MathUtil.EPS_MEDIUM);
        assertEquals("Unexpected normalizedDot product.",
                1.0, vector2.normalizedDot(vector), MathUtil.EPS_MEDIUM);
    }

    // java.lang.AssertionError: Normalized Dot product, 1.0000000000000002, was outside expected range.
    //Dot=4.208925801143705 div=4.208925801143704
    //        for v1=-1.6370338447524475 -1.6708452150399316 v2=-1.2592568036557408 -1.285265550030705
    //magThis=2.339145900000001 magB=1.799343000000001

    /** This case failed from hill climbing one time */
    public void testNormalizedDotProductWhenAlmostSame2() {

        vector = new Vector(new double[] {-1.6370338447524475, -1.6708452150399316});
        Vector vector2 = new Vector(new double[] {-1.2592568036557408, -1.285265550030705});
        assertEquals("Unexpected normalizedDot product.",
                1.0, vector.normalizedDot(vector2), MathUtil.EPS_MEDIUM);
    }
}
