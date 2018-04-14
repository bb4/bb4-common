// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Barry Becker
 */
public class RangeTest {

    private static final double TOL = 0;

    /** instance under test */
    private Range range;

    @Test
    public void testDefaultConstruction() {
        range = new Range();
        assertTrue("Unexpected extent", Double.isNaN(range.getExtent()));
    }

    public void testTypicalConstruction() {
        range = new Range(1.2, 3.4);
        assertEquals("Unexpected extent", 2.2, range.getExtent(), TOL);
        assertEquals("Unexpected min", 1.2, range.getMin(), TOL);
        assertEquals("Unexpected min", 3.4, range.getMax(), TOL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConstruction() {
        range = new Range(5.2, 3.4);
    }

    @Test
    public void testExtendRangeHigher() {
        range = new Range(2, 3);
        range.add(5);
        assertEquals("Unexpected max", 5.0, range.getMax(), TOL);
    }

    @Test
    public void testExtendRangeLower() {
        range = new Range(2, 3);
        range.add(-2);
        assertEquals("Unexpected max", -2.0, range.getMin(), TOL);
    }

    @Test
    public void testExtendByRange() {
        range = new Range(2, 3);
        Range range2 = new Range(-1.5, 2.5);
        range.add(range2);

        assertEquals("Unexpected min", -1.5, range.getMin(), TOL);
        assertEquals("Unexpected max", 3.0, range.getMax(), TOL);
    }


}
