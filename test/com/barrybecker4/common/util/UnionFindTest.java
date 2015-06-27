package com.barrybecker4.common.util;

import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Barry Becker
 */
public class UnionFindTest {

    private static final String PATH = "test/com/barrybecker4/common/util/";
    private UnionFind uf;

    @Test
    public void testTiny() throws Exception  {
        uf = UnionFind.create(new FileInputStream(PATH + "data/tinyUF.txt"));
        assertEquals("Unexpected number of sets", 2, uf.getCount());
    }

    @Test
    public void testMedium() throws Exception  {
        uf = UnionFind.create(new FileInputStream(PATH + "data/mediumUF.txt"));
        assertEquals("Unexpected number of sets", 3, uf.getCount());
    }

    @Test
    public void testLarge() throws Exception  {
        long start = System.currentTimeMillis();
        uf = UnionFind.create(new FileInputStream(PATH + "data/largeUF.txt"));
        long elapsed = System.currentTimeMillis() - start;
        assertEquals("Unexpected number of sets", 6, uf.getCount());

        assertTrue("Too long: "+ elapsed, elapsed < 4500);
    }

    @Test
    public void testFind() throws Exception {
        uf = new UnionFind(9);

        uf.union(2, 4);
        uf.union(6, 8);
        assertEquals("Unexpected number of sets", 7, uf.getCount());

        assertEquals("Unexpected item set id for 2", 2, uf.find(2));
        assertEquals("Unexpected item set id for 2", 2, uf.find(4));
        assertEquals("Unexpected item set id for 2", 6, uf.find(6));
        assertEquals("Unexpected item set id for 2", 6, uf.find(8));
        assertEquals("Unexpected item set id for 2", 1, uf.find(1));
        assertEquals("Unexpected item set id for 2", 3, uf.find(3));

        uf.union(2, 8);
        assertEquals("Unexpected item set id for 2", 2, uf.find(2));
        assertEquals("Unexpected item set id for 2", 2, uf.find(4));
        assertEquals("Unexpected item set id for 2", 2, uf.find(6));
        assertEquals("Unexpected item set id for 2", 2, uf.find(8));
    }

    @Test
    public void testConnected() throws Exception {
        uf = new UnionFind(7);

        uf.union(1, 3);
        uf.union(4, 0);
        assertFalse("Unexpectedly connected", uf.connected(4, 3));

        uf.union(0, 1);
        assertTrue("Unexpectedly not connected", uf.connected(4, 3));
    }
}
