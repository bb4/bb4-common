// Copyright by Barry G. Becker, 2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.combinatorics;

import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Barry Becker
 */
public class CombinaterTest {

    /** instance under test */
    private Combinater combinater;


    @Test
    public void testCombinations0() {
        combinater = new Combinater(0);
        assertFalse(combinater.hasNext());
    }


    @Test
    public void testCombinations1() {
        combinater = new Combinater(1);
        assertTrue(combinater.hasNext());
        assertEquals("Unexpected second permutation", Arrays.asList(0), combinater.next());
        assertFalse(combinater.hasNext());
    }

    @Test
    public void testCombinations2() {
        combinater = new Combinater(2);

        assertTrue(combinater.hasNext());
        assertEquals("Unexpected first permutation", Arrays.asList(0), combinater.next());
        assertEquals("Unexpected second permutation", Arrays.asList(1), combinater.next());
        assertEquals("Unexpected third permutation", Arrays.asList(0, 1), combinater.next());
        assertFalse(combinater.hasNext());
    }

    /** subsets of  1, 2, 3 */
    @Test
    public void testCombinations3() {
        combinater = new Combinater(3);

        assertTrue(combinater.hasNext());
        assertEquals("Unexpected permutation", Arrays.asList(0), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(1), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(0, 1), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(2), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(0, 2), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(1, 2), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(0, 1, 2), combinater.next());
        assertFalse(combinater.hasNext());
    }

    /** permutations of 1, 2, 3, 4, 5. There will be 120 of them*/
    @Test
    public void testCombinations5() {
        combinater = new Combinater(5);

        assertTrue(combinater.hasNext());
        assertEquals("Unexpected permutation", Arrays.asList(0), combinater.next());
        for (int i=0; i<28; i++)
            combinater.next();
        assertEquals("Unexpected permutation", Arrays.asList(1, 2, 3, 4), combinater.next());
        assertEquals("Unexpected permutation", Arrays.asList(0, 1, 2, 3, 4), combinater.next());
        assertFalse(combinater.hasNext());
    }


    /** an error if we request more combinations than there are */
    @Test(expected = NoSuchElementException.class)
    public void testIteratePastEnd() {
        combinater = new Combinater(5);
        for (int i=0; i<120; i++)
            combinater.next();
    }


    /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
    @Test
    public void testCombinationsReallyBig() {
        combinater = new Combinater(10);

        assertTrue(combinater.hasNext());
        combinater.next();
        assertEquals("Unexpected permutation", 1, combinater.next().size());
        assertTrue(combinater.hasNext());
    }


    /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
    @Test
    public void testCombinationsReallyReallyBig() {
        combinater = new Combinater(20);

        assertTrue(combinater.hasNext());
        combinater.next();
        assertEquals("Unexpected permutation", 1, combinater.next().size());
        assertEquals("Unexpected second permutation",
                "[0, 1]",
                combinater.next().toString());
        assertEquals("Unexpected third permutation",
                "[2]",
                combinater.next().toString());
        assertTrue(combinater.hasNext());
    }


    /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
    @Test(expected = IllegalArgumentException.class)
    public void testCombinationsTooBig() {
        combinater = new Combinater(90);
    }

}
