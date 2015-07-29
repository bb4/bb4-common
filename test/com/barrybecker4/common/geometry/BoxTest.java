package com.barrybecker4.common.geometry;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Barry Becker
 */
public class BoxTest {

    /** instance under test */
    private Box box;

    @Test
    public void testConstruction() {
        box = new Box(new IntLocation(2, 3), new IntLocation(4, 4));
        assertEquals("Unexpected top left", new IntLocation(2, 3), box.getTopLeftCorner());
        assertEquals("Unexpected bottom right", new IntLocation(4, 4), box.getBottomRightCorner());
    }

    @Test
    public void testContainment() {
        box = new Box(new IntLocation(2, 3), new IntLocation(6, 5));

        assertTrue("Unexpectedly not contained when within", box.contains(new IntLocation(4, 5)));
        assertTrue("Unexpectedly not contained when within", box.contains(new IntLocation(3, 4)));
        assertFalse("Unexpected containment", box.contains(new IntLocation(9, 7)));
        assertTrue("Unexpectedly not contained when at corner", box.contains(new IntLocation(6, 5)));
        assertTrue("Unexpectedly not contained when at corner", box.contains(new IntLocation(2, 3)));
        assertTrue("Unexpectedly not contained when byte", box.contains(new ByteLocation(3, 4)));
    }

    @Test
    public void testArea() {
        box = new Box(new IntLocation(2, 3), new IntLocation(5, 5));
        assertEquals("Unexpected area", 6, box.getArea());
    }

    @Test
    public void testMaxDimension() {
        box = new Box(new IntLocation(2, 3), new IntLocation(5, 5));
        assertEquals("Unexpected max dim", 3, box.getMaxDimension());
    }

    @Test
    public void testIsOnCorner() {
        box = new Box(new IntLocation(2, 3), new IntLocation(5, 5));
        assertTrue(box.isOnCorner(new IntLocation(2, 3)));
        assertFalse(box.isOnCorner(new IntLocation(3, 3)));
        assertTrue(box.isOnCorner(new IntLocation(5, 5)));
    }

    @Test
    public void testExpandBy() {
        box = new Box(new IntLocation(2, 3), new IntLocation(5, 5));
        box.expandBy(new IntLocation(4, 10));
        assertEquals("Unexpected bottom right", new IntLocation(5, 10), box.getBottomRightCorner());
    }

    @Test
    public void testExpandGlobally() {
        box = new Box(new IntLocation(2, 3), new IntLocation(5, 5));
        box.expandGloballyBy(3, 6, 5);
        assertEquals("Unexpected bottom right", new IntLocation(6, 5), box.getBottomRightCorner());
        assertEquals("Unexpected top left", new IntLocation(1, 1), box.getTopLeftCorner());
    }

    @Test
    public void testToString() {
        box = new Box(new IntLocation(2, 3), new IntLocation(5, 5));
        assertEquals("Unexpected string form", "Box:(row=2, column=3) - (row=5, column=5)", box.toString());
    }
}
