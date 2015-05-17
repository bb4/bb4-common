// Copyright by Barry G. Becker, 2013-2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.cutpoints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class RounderTest {

    private static final double EPS = 0.00001;


    @Test
    public void testRoundSmallUp() {
        assertEquals(2, Rounder.roundUp(1.234), EPS);
    }

    @Test
    public void testRoundSmallDown() {
        assertEquals(1, Rounder.roundDown(1.234), EPS);
    }

    @Test
    public void testRoundVerySmallUp() {
        assertEquals(0.05, Rounder.roundUp(0.034), EPS);
    }

    @Test
    public void testRoundVerySmallDown() {
        assertEquals(0.05, Rounder.roundDown(0.034), EPS);
    }

     @Test
    public void testRoundMedium1Up() {
        assertEquals(50, Rounder.roundUp(34.5), EPS);
    }

    @Test
    public void testRoundMedium1Down() {
        assertEquals(50, Rounder.roundDown(34.5), EPS);
    }

    @Test
    public void testRoundMedium2Up() {
        assertEquals(100, Rounder.roundUp(74.5), EPS);
    }

    @Test
    public void testRoundMedium2Down() {
        assertEquals(100, Rounder.roundDown(74.5), EPS);
    }

    @Test
    public void testRoundMedium3Up() {
        assertEquals(200, Rounder.roundUp(140.1), EPS);
    }

    @Test
    public void testRoundMedium3Down() {
        assertEquals(100, Rounder.roundDown(140.1), EPS);
    }

    @Test
    public void testRoundLargeUp() {
        assertEquals(1000, Rounder.roundUp(874.5), EPS);
    }

    @Test
    public void testRoundLargeDown() {
        assertEquals(1000, Rounder.roundDown(874.5), EPS);
    }

    @Test
    public void testRoundVeryLargeUp() {
        assertEquals(2000000, Rounder.roundUp(1812874.51234567), EPS);
    }

    @Test
    public void testRoundVeryLargeDown() {
        assertEquals(2000000, Rounder.roundDown(1812874.51234567), EPS);
    }

}
