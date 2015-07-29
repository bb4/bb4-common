// Copyright by Barry G. Becker, 2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.combinatorics;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Barry Becker
 */
public class PermuterTest {

    /** instance under test */
    private Permuter permuter;

    @Test
    public void testPermute0() {
        permuter = new Permuter(0);
        assertFalse(permuter.hasNext());
    }


    @Test
    public void testPermute1() {
        permuter = new Permuter(1);
        assertTrue(permuter.hasNext());
        assertEquals("Unexpected second permutation", Arrays.asList(0), permuter.next());
        assertFalse(permuter.hasNext());
    }

    @Test
    public void testPermute2() {
        permuter = new Permuter(2);

        assertTrue(permuter.hasNext());
        assertEquals("Unexpected first permutation", Arrays.asList(0, 1), permuter.next());
        assertEquals("Unexpected second permutation", Arrays.asList(1, 0), permuter.next());
        assertFalse(permuter.hasNext());
    }


    /** permutations of  1, 2, 3 */
    @Test
    public void testPermute3() {
        permuter = new Permuter(3);

        assertTrue(permuter.hasNext());
        assertEquals("Unexpected permutation", Arrays.asList(0, 1, 2), permuter.next());
        assertEquals("Unexpected permutation", Arrays.asList(0, 2, 1), permuter.next());
        assertEquals("Unexpected permutation", Arrays.asList(1, 0, 2), permuter.next());
        assertEquals("Unexpected permutation", Arrays.asList(1, 2, 0), permuter.next());
        assertEquals("Unexpected permutation", Arrays.asList(2, 0, 1), permuter.next());
        assertEquals("Unexpected permutation", Arrays.asList(2, 1, 0), permuter.next());
        assertFalse(permuter.hasNext());
    }

    /** permutations of 1, 2, 3, 4, 5. There will be 120 of them*/
    @Test
    public void testPermute5() {
        permuter = new Permuter(5);

        assertTrue(permuter.hasNext());
        assertEquals("Unexpected permutation", Arrays.asList(0, 1, 2, 3, 4), permuter.next());
        for (int i=0; i<119; i++)
            permuter.next();
        assertEquals("Unexpected permutation", Arrays.asList(4, 3, 2, 1, 0), permuter.next());
        assertFalse(permuter.hasNext());
    }

    /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
    @Test
    public void testPermuteReallyBig() {
        permuter = new Permuter(10);

        assertTrue(permuter.hasNext());
        permuter.next();
        assertEquals("Unexpected permutation", 10, permuter.next().size());
        assertTrue(permuter.hasNext());
    }


    /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
    @Test
    public void testPermuteReallyReallyBig() {
        permuter = new Permuter(20);

        assertTrue(permuter.hasNext());
        permuter.next();
        assertEquals("Unexpected permutation", 20, permuter.next().size());
        assertEquals("Unexpected second permutation",
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 17, 19]",
                permuter.next().toString());
        assertEquals("Unexpected third permutation",
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 17]",
                permuter.next().toString());
        assertTrue(permuter.hasNext());
    }


    /** permutations of a really large sequence. There will be more than Long.MAX_VAL of them */
    @Test(expected = IllegalArgumentException.class)
    public void testPermuteTooBig() {
        permuter = new Permuter(30);
    }

}
