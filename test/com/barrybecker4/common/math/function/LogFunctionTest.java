// Copyright by Barry G. Becker, 2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.function;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class LogFunctionTest {

    /** instance under test */
    private LogFunction func;

    private static final double EPS = 0.00001;

    @Test
    public void testTypicalFunc() {
        func = new LogFunction(10.0);
        assertEquals(0, func.getValue(1), EPS);
        assertEquals(10.0, func.getValue(10), EPS);
        assertEquals(19.0309, func.getValue(80), EPS);
        assertEquals(20.0, func.getValue(100), EPS);
        assertEquals(30.0, func.getValue(1000), EPS);
    }

    @Test
    public void testBase2FuncWithSmallValues() {
        func = new LogFunction(0.1, 2.0, false);
        assertEquals(0, func.getValue(1), EPS);
        assertEquals(0.33219, func.getValue(10), EPS);
        assertEquals(0.3, func.getValue(8), EPS);
        assertEquals(0.4, func.getValue(16), EPS);
        assertEquals(-0.3, func.getValue(0.125), EPS);
        assertEquals(0.66438, func.getValue(100), EPS);
        assertEquals(0.99657, func.getValue(1000), EPS);
    }

    @Test
    public void testBase2FuncNeverLessThan0() {
        func = new LogFunction(0.1, 2.0, true);
        assertEquals(0, func.getValue(1), EPS);
        assertEquals(0.4, func.getValue(16), EPS);
        assertEquals(0.0, func.getValue(0.125), EPS);
        assertEquals(0.66438, func.getValue(100), EPS);
    }

    @Test
    public void testFuncWithNegativeValuesPassedIn() {
        func = new LogFunction(10.0);
        assertEquals(0, func.getValue(1), EPS);
        assertEquals(-10.0, func.getValue(-10), EPS);
        assertEquals(-19.0309, func.getValue(-80), EPS);
        assertEquals(-20.0, func.getValue(-100), EPS);
        assertEquals(30.0, func.getValue(1000), EPS);
    }

    @Test
    public void testFuncWithZeroScale() {
        func = new LogFunction(0.0);
        assertEquals(-0.0, func.getValue(10), EPS);
    }


}
