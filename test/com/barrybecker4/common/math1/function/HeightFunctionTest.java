// Copyright by Barry G. Becker, 2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math1.function;

import com.barrybecker4.common.math1.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Barry Becker
 */
public class HeightFunctionTest {

    /** instance under test */
    private HeightFunction func;

    private static final double EPS = 0.000001;

    @Test
    public void testTypicalFunc() {
        func = new HeightFunction(new Range(1, 20), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.3, 0.11, 0.04, -0.1, -0.15});
        assertEquals(0.2, func.getValue(1), EPS);
        assertEquals(0.4473684, func.getValue(10), EPS);
        assertEquals(0.065789, func.getValue(15), EPS);
        assertEquals(-0.15, func.getValue(20), EPS);
    }


    @Test
    public void testBase0Func() {
        func = new HeightFunction(new Range(0, 9), new double[] {.2, 0.31, 0.6, 0.7, 0.5, 0.3, 0.11, 0.04, -0.1, -0.15});
        assertEquals(0.2, func.getValue(0), EPS);
        assertEquals(0.31, func.getValue(1), EPS);
        assertEquals(0.5, func.getValue(4), EPS);
        assertEquals(0.04, func.getValue(7), EPS);
        assertEquals(-0.15, func.getValue(9), EPS);
    }



    @Test
    public void testFuncFromRangeToBinsPositiveOffset() {
        func = new HeightFunction(new Range(100, 500), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2});
        assertEquals(0.2, func.getValue(100), EPS);
        assertEquals(0.225, func.getValue(120), EPS);
        assertEquals(0.3, func.getValue(180), EPS);
        assertEquals(0.20375, func.getValue(499), EPS);
        assertEquals(0.4875, func.getValue(230), EPS);
        assertEquals(0.2, func.getValue(500), EPS);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFuncFromRangeToBinsOutOfRange() {
        func = new HeightFunction(new Range(100, 500), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2});
        assertEquals(0.2, func.getValue(501), EPS);
    }


    @Test
    public void testFuncFromRangeToBins() {
        func = new HeightFunction(new Range(0, 9), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.1, 0.5, 0.4, 0.3});
        assertEquals(0.2, func.getValue(0), EPS);
        assertEquals(0.21, func.getValue(0.1), EPS);
        assertEquals(0.3, func.getValue(1), EPS);
        assertEquals(0.5, func.getValue(4), EPS);
        assertEquals(0.2, func.getValue(5), EPS);
        assertEquals(0.4, func.getValue(8), EPS);
        assertEquals(0.3, func.getValue(9), EPS);
    }


    @Test
    public void testFuncFromAutoRangeToBins() {
        func = new HeightFunction(new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.1, 0.5, 0.4, 0.3});
        assertEquals(0.2, func.getValue(0), EPS);
        assertEquals(0.29, func.getValue(0.1), EPS);
        assertEquals(0.405, func.getValue(0.15), EPS);
        assertEquals(0.432, func.getValue(0.16), EPS);
        assertEquals(0.16, func.getValue(0.6), EPS);
        assertEquals(0.39, func.getValue(0.9), EPS);
        assertEquals(0.327, func.getValue(0.97), EPS);
        assertEquals(0.3, func.getValue(1.0), EPS);
    }
}
