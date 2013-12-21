// Copyright by Barry G. Becker, 2012-2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Barry Becker
 */
public class MultiDimensionalIndexerTest {

    /** instance under test */
    private MultiDimensionalIndexer indexer;

    /** must be at least one dim */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateOLength0DArray() {
        int[] dims = new int[0];
        new MultiDimensionalIndexer(dims);
    }

    /** dim lengths must be greater than 0 */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate0Length1DArray() {
        int[] dims = new int[1];
        new MultiDimensionalIndexer(dims);
    }

    @Test
    public void testCreate1Length1DArray() {
        int[] dims = new int[] {1};
        indexer = new MultiDimensionalIndexer(dims);
        assertEquals("Unexpected num dims", 1, indexer.getNumDims());
        assertEquals("Unexpected num values", 1, indexer.getNumValues());
        assertIndicesEqual("Unexpected index from raw.", new int[]{0}, indexer.getIndexFromRaw(0));
        assertEquals("Unexpected index key", "[0]", indexer.getIndexKey(0));
        //assertEquals("Unexpected index key (indexer)", "0", indexer.getIndexKey(new int[] {0}));
    }

    @Test
    public void testCreate2Length2DArray() {
        int[] dims = new int[] {2, 2};
        indexer = new MultiDimensionalIndexer(dims);

        assertEquals("Unexpected num dims", 2, indexer.getNumDims());
        assertEquals("Unexpected num values", 4, indexer.getNumValues());
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 0}, indexer.getIndexFromRaw(0));
        assertIndicesEqual("Unexpected index from raw.", new int[] {1, 0}, indexer.getIndexFromRaw(2));
        assertIndicesEqual("Unexpected index from raw.", new int[] {1, 1}, indexer.getIndexFromRaw(3));
        assertEquals("Unexpected index key", "[0, 0]", indexer.getIndexKey(0));
        assertEquals("Unexpected index key", "[0, 1]", indexer.getIndexKey(1));
        assertEquals("Unexpected index key", "[1, 1]", indexer.getIndexKey(3));
        //assertEquals("Unexpected index key (indexer)", "0,1", indexer.getIndexKey(new int[] {0, 1}));
    }

    @Test
    public void testCreate3DArray() {
        int[] dims = new int[] {2, 3, 4};
        indexer = new MultiDimensionalIndexer(dims);

        assertEquals("Unexpected num dims", 3, indexer.getNumDims());
        assertEquals("Unexpected num values", 24, indexer.getNumValues());
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 0, 0}, indexer.getIndexFromRaw(0));
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 0, 2}, indexer.getIndexFromRaw(2));
        assertIndicesEqual("Unexpected index from raw.", new int[] {0, 1, 0}, indexer.getIndexFromRaw(4));
        assertIndicesEqual("Unexpected index from raw.", new int[] {1, 1, 3}, indexer.getIndexFromRaw(19));
        assertEquals("Unexpected index key", "[0, 0, 0]", indexer.getIndexKey(0));
        assertEquals("Unexpected index key", "[1, 1, 3]", indexer.getIndexKey(19));
        assertEquals("Unexpected index key", "[1, 2, 0]", indexer.getIndexKey(20));
        assertEquals("Unexpected index key", "[1, 2, 1]", indexer.getIndexKey(21));
        assertEquals("Unexpected index key", "[1, 2, 2]", indexer.getIndexKey(22));
        assertEquals("Unexpected index key", "[1, 2, 3]", indexer.getIndexKey(23));
        //assertEquals("Unexpected index key (indexer)", "0,1,3", indexer.getIndexKey(new int[] {0, 1, 3}));
    }

    private void assertIndicesEqual(String msg, int[] expIndices, int[] indices) {
        assertTrue("Got " + Arrays.toString(indices),
                Arrays.equals(expIndices, indices));
    }
}
