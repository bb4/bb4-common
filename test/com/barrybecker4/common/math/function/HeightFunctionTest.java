// Copyright by Barry G. Becker, 2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.function;

import com.barrybecker4.common.math.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Barry Becker
 */
public class HeightFunctionTest {

    /** instance under test */
    private HeightFunction func;

    private static final double EPS = 0.0000001;

    @Test
    public void testTypicalFunc() {
        func = new HeightFunction(new Range(1, 20), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.11, 0.02, -0.1, -0.15});
        assertEquals(0.2, func.getValue(0), EPS);
        assertEquals(0.5, func.getValue(10), EPS);
        assertEquals(0.02, func.getValue(15), EPS);
        assertEquals(-0.15, func.getValue(20), EPS);
    }



    @Test
    public void testFuncFromRangeToBinsPositiveOffset() {
        func = new HeightFunction(new Range(100, 500), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2});
        assertEquals(0.2, func.getValue(100), EPS);
        assertEquals(0.2, func.getValue(120), EPS);
        assertEquals(0.3, func.getValue(180), EPS);
        assertEquals(0.2, func.getValue(499), EPS);
        assertEquals(0.6, func.getValue(230), EPS);
        assertEquals(0.2, func.getValue(500), EPS);
        assertEquals(0.2, func.getValue(501), EPS);
    }


    @Test
    public void testFuncFromRangeToBins() {
        func = new HeightFunction(new Range(0, 9), new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.1, 0.5, 0.4, 0.3});
        assertEquals(0.2, func.getValue(-0.5), EPS);
        assertEquals(0.2, func.getValue(-0.2), EPS);
        assertEquals(0.2, func.getValue(0), EPS);
        assertEquals(0.2, func.getValue(0.1), EPS);
        assertEquals(0.3, func.getValue(1), EPS);
        assertEquals(0.5, func.getValue(4), EPS);
        assertEquals(0.2, func.getValue(5), EPS);
        assertEquals(0.4, func.getValue(8), EPS);
        assertEquals(0.3, func.getValue(9), EPS);
        assertEquals(0.3, func.getValue(9.4), EPS);
    }


    @Test
    public void testFuncFromAutoRangeToBins() {
        func = new HeightFunction(new double[] {.2, 0.3, 0.6, 0.7, 0.5, 0.2, 0.1, 0.5, 0.4, 0.3});
        assertEquals(0.2, func.getValue(0), EPS);
        assertEquals(0.3, func.getValue(0.1), EPS);
        assertEquals(0.3, func.getValue(0.15), EPS);
        assertEquals(0.3, func.getValue(0.16), EPS);
        assertEquals(0.2, func.getValue(0.6), EPS);
        assertEquals(0.4, func.getValue(0.9), EPS);
        assertEquals(0.3, func.getValue(0.97), EPS);
        assertEquals(0.3, func.getValue(1.0), EPS);
    }
}
