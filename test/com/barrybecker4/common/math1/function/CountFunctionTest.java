// Copyright by Barry G. Becker, 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math1.function;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Barry Becker
 */
public class CountFunctionTest {

    /** instance under test */
    private CountFunction func;

    private static final double EPS = 0.0000001;

    @Test
    public void testBehaviorAfterConstruction() {
        func = new CountFunction(0.3);
        assertEquals(0.3, func.getValue(0), EPS);
        assertEquals(0.3, func.getValue(10), EPS);
        assertEquals(0.3, func.getValue(80), EPS);
    }

    /** note that x values are normalized to 0 - 1 range.  */
    @Test
    public void testAddValue() {
        func = new CountFunction(2.1);
        assertEquals(2.1, func.getValue(0), EPS);

        func.addValue(1, 5.3);
        assertEquals(5.3, func.getValue(1), EPS);
        assertEquals(3.7, func.getValue(0.5), EPS);

        func.addValue(2, 7.3);
        func.addValue(3, 4.3);
        assertEquals(4.3, func.getValue(1), EPS);
    }

    @Test
    public void testAddValueWheMaxSet() {
        func = new CountFunction(3.0);
        func.setMaxXValues(5);

        func.addValue(1, 5);
        func.addValue(3, 8);
        func.addValue(4, 10);
        assertEquals(3, func.getValue(0), EPS);
        assertEquals(4.2, func.getValue(0.2), EPS);
        assertEquals(6.5, func.getValue(0.5), EPS);
        assertEquals(10, func.getValue(1.0), EPS);

        func.addValue(5, 11);
        assertEquals(4.6, func.getValue(0.2), EPS);
        func.addValue(6, 12);
        func.addValue(7, 14);

        // not that the early x values have been removed.
        assertEquals(8, func.getValue(0.2), EPS);
        assertEquals(10.5, func.getValue(0.5), EPS);
        assertEquals(14, func.getValue(1.0), EPS);
    }

    @Test
    public void testSetInitialValue() {
        func = new CountFunction(3.0);

        func.addValue(1, 5);
        func.addValue(3, 8);
        func.addValue(4, 10);
        assertEquals(3, func.getValue(0), EPS);
        assertEquals(4.2, func.getValue(0.2), EPS);
        assertEquals(6.5, func.getValue(0.5), EPS);
        assertEquals(10, func.getValue(1.0), EPS);

        func.setInitialValue(11);
        assertEquals(11, func.getValue(0.2), EPS);
        func.addValue(6, 12);
        func.addValue(7, 14);

        assertEquals(11.4, func.getValue(0.2), EPS);
        assertEquals(12, func.getValue(0.5), EPS);
        assertEquals(14, func.getValue(1.0), EPS);
    }

}
