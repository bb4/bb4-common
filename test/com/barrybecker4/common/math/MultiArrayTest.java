// Copyright by Barry G. Becker, 2012. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * @author Barry Becker
 */
public class MultiArrayTest extends TestCase {

    /** instance under test */
    private MultiArray array;

    /** must be at least one dim */
    public void testCreateOLength0DArray() {
        int[] dims = new int[0];
        try {
            new MultiArray(dims);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /** dim lengths must be greater than 0 */
    public void testCreate0Length1DArray() {
        int[] dims = new int[1];
        try {
            new MultiArray(dims);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    public void testCreate1Length1DArray() {
        int[] dims = new int[] {1};
        array = new MultiArray(dims);
        assertEquals("Unexpected value at index", 0.0, array.get(new int[] {0}));
        assertEquals("Unexpected num dims", 1, array.getNumDims());
        assertEquals("Unexpected num values", 1, array.getNumValues());
        assertIndicesEqual("Unexpected index from raw.", new int[] {0}, array.getIndexFromRaw(0));
        assertEquals("Unexpected index key", "[0]", array.getIndexKey(0));
        //assertEquals("Unexpected index key (array)", "0", array.getIndexKey(new int[] {0}));
        assertEquals("Unexpected raw value", 0.0, array.getRaw(0));
    }

    public void testCreate2Length2DArray() {
        int[] dims = new int[] {2, 2};
        array = new MultiArray(dims);
        array.set(new int[] {1, 0}, 3.4);

        assertEquals("Unexpected value at index", 0.0, array.get(new int[] {0, 1}));
        assertEquals("Unexpected value at index", 3.4, array.get(new int[] {1, 0}));
        assertEquals("Unexpected num dims", 2, array.getNumDims());
        assertEquals("Unexpected num values", 4, array.getNumValues());
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 0}, array.getIndexFromRaw(0));
        assertIndicesEqual("Unexpected index from raw.", new int[] {1, 0}, array.getIndexFromRaw(2));
        assertIndicesEqual("Unexpected index from raw.", new int[] {1, 1}, array.getIndexFromRaw(3));
        assertEquals("Unexpected index key", "[0, 0]", array.getIndexKey(0));
        assertEquals("Unexpected index key", "[0, 1]", array.getIndexKey(1));
        assertEquals("Unexpected index key", "[1, 1]", array.getIndexKey(3));
        //assertEquals("Unexpected index key (array)", "0,1", array.getIndexKey(new int[] {0, 1}));
        assertEquals("Unexpected raw value", 3.4, array.getRaw(2));
    }

    public void testCreate3DArray() {
        int[] dims = new int[] {2, 3, 4};
        array = new MultiArray(dims);
        array.set(new int[] {1, 0, 1}, 3.4);
        array.set(new int[] {1, 1, 3}, 5.1);

        assertEquals("Unexpected value at index", 3.4, array.get(new int[] {1, 0, 1}));
        assertEquals("Unexpected value at index", 5.1, array.get(new int[] {1, 1, 3}));
        assertEquals("Unexpected num dims", 3, array.getNumDims());
        assertEquals("Unexpected num values", 24, array.getNumValues());
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 0, 0}, array.getIndexFromRaw(0));
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 0, 2}, array.getIndexFromRaw(2));
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 1, 0}, array.getIndexFromRaw(4));
        assertIndicesEqual("Unexpected index from raw.", new int[] {1, 1, 3}, array.getIndexFromRaw(19));
        assertEquals("Unexpected index key", "[0, 0, 0]", array.getIndexKey(0));
        assertEquals("Unexpected index key", "[1, 1, 3]", array.getIndexKey(19));
        assertEquals("Unexpected index key", "[1, 2, 0]", array.getIndexKey(20));
        assertEquals("Unexpected index key", "[1, 2, 1]", array.getIndexKey(21));
        assertEquals("Unexpected index key", "[1, 2, 2]", array.getIndexKey(22));
        assertEquals("Unexpected index key", "[1, 2, 3]", array.getIndexKey(23));
        //assertEquals("Unexpected index key (array)", "0,1,3", array.getIndexKey(new int[] {0, 1, 3}));
        assertEquals("Unexpected raw value", 3.4, array.getRaw(13));
    }

    private void assertIndicesEqual(String msg, int[] expIndices, int[] indices) {
        assertTrue("Got " + Arrays.toString(indices),
                Arrays.equals(expIndices, indices));
    }
}
