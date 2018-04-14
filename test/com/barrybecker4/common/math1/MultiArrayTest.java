// Copyright by Barry G. Becker, 2012-2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class MultiArrayTest {

    private static final double TOL = 0;

    /** instance under test */
    private MultiArray array;

    /** must be at least one dim */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateOLength0DArray() {
        int[] dims = new int[0];
        new MultiArray(dims);
    }

    /** dim lengths must be greater than 0 */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate0Length1DArray() {
        int[] dims = new int[1];
        new MultiArray(dims);
    }

    @Test
    public void testCreate1Length1DArray() {
        int[] dims = new int[] {1};
        array = new MultiArray(dims);
        assertEquals("Unexpected value at index", 0.0, array.get(new int[] {0}), TOL);
        assertEquals("Unexpected num values", 1, array.getNumValues());
        assertEquals("Unexpected raw value", 0.0, array.getRaw(0), TOL);
    }

    @Test
    public void testCreate2Length2DArray() {
        int[] dims = new int[] {2, 2};
        array = new MultiArray(dims);
        array.set(new int[] {1, 0}, 3.4);

        assertEquals("Unexpected value at index", 0.0, array.get(new int[] {0, 1}), TOL);
        assertEquals("Unexpected value at index", 3.4, array.get(new int[] {1, 0}), TOL);
        assertEquals("Unexpected num values", 4, array.getNumValues());
        assertEquals("Unexpected raw value", 3.4, array.getRaw(2), TOL);
    }

    @Test
    public void testCreate3DArray() {
        int[] dims = new int[] {2, 3, 4};
        array = new MultiArray(dims);
        array.set(new int[] {1, 0, 1}, 3.4);
        array.set(new int[] {1, 1, 3}, 5.1);

        assertEquals("Unexpected value at index", 3.4, array.get(new int[] {1, 0, 1}), TOL);
        assertEquals("Unexpected value at index", 5.1, array.get(new int[] {1, 1, 3}), TOL);
        assertEquals("Unexpected num values", 24, array.getNumValues());
        assertEquals("Unexpected raw value", 3.4, array.getRaw(13), TOL);
    }
}
