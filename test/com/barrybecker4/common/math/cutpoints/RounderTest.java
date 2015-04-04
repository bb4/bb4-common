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
        assertEquals(2, Rounder.round(1.234, false), EPS);
    }

    @Test
    public void testRoundSmallDown() {
        assertEquals(1, Rounder.round(1.234, true), EPS);
    }

    @Test
    public void testRoundVerySmallUp() {
        assertEquals(0.05, Rounder.round(0.034, false), EPS);
    }

    @Test
    public void testRoundVerySmallDown() {
        assertEquals(0.05, Rounder.round(0.034, true), EPS);
    }

     @Test
    public void testRoundMedium1Up() {
        assertEquals(50, Rounder.round(34.5, false), EPS);
    }

    @Test
    public void testRoundMedium1Down() {
        assertEquals(50, Rounder.round(34.5, true), EPS);
    }

    @Test
    public void testRoundMedium2Up() {
        assertEquals(100, Rounder.round(74.5, false), EPS);
    }

    @Test
    public void testRoundMedium2Down() {
        assertEquals(100, Rounder.round(74.5, true), EPS);
    }

    @Test
    public void testRoundMedium3Up() {
        assertEquals(200, Rounder.round(140.1, false), EPS);
    }

    @Test
    public void testRoundMedium3Down() {
        assertEquals(100, Rounder.round(140.1, true), EPS);
    }

    @Test
    public void testRoundLargeUp() {
        assertEquals(1000, Rounder.round(874.5, false), EPS);
    }

    @Test
    public void testRoundLargeDown() {
        assertEquals(1000, Rounder.round(874.5, true), EPS);
    }

    @Test
    public void testRoundVeryLargeUp() {
        assertEquals(2000000, Rounder.round(1812874.5, false), EPS);
    }

    @Test
    public void testRoundVeryLargeDown() {
        assertEquals(2000000, Rounder.round(1812874.5, true), EPS);
    }

}
