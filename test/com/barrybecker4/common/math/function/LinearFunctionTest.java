// Copyright by Barry G. Becker, 2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.function;

import com.barrybecker4.common.math.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Barry Becker
 */
public class LinearFunctionTest {

    /** instance under test */
    private LinearFunction func;

    private static final double EPS = 0.0000001;

    @Test
    public void testTypicalFunc() {
        func = new LinearFunction(1/10.0);
        assertEquals(0, func.getValue(0), EPS);
        assertEquals(1.0, func.getValue(10), EPS);
        assertEquals(8.0, func.getValue(80), EPS);
    }

    @Test
    public void testFuncWithOffset() {
        func = new LinearFunction(2.0, 5.0);
        assertEquals(5, func.getValue(0), EPS);
        assertEquals(11.0, func.getValue(3), EPS);
        assertEquals(165.0, func.getValue(80), EPS);
        assertEquals(-15.0, func.getValue(-10), EPS);
    }

    @Test
    public void testFuncFromRangeToBins() {
        func = new LinearFunction(new Range(-1000, 4000), 10);
        assertEquals(2, func.getValue(0), EPS);
        assertEquals(2.006, func.getValue(3), EPS);
        assertEquals(2.16, func.getValue(80), EPS);
        assertEquals(6, func.getValue(2000), EPS);
        assertEquals(12, func.getValue(5000), EPS);
        assertEquals(1.98, func.getValue(-10), EPS);
        assertEquals(-0.02, func.getValue(-1010), EPS);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFuncWithZeroScale() {
        func = new LinearFunction(0.0, 5.0);
    }


}
