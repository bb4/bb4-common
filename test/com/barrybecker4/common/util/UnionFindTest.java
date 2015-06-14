package com.barrybecker4.common.util;

import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class UnionFindTest {

    private static final String PATH = "test/com/barrybecker4/common/util/";
    private UnionFind uf;

    @Test
    public void testTiny() throws Exception  {
        uf = new UnionFind(new FileInputStream(PATH + "data/tinyUF.txt"));
        assertEquals("Unexpected number of sets", 10, uf.count());
    }
    @Test
    public void testMedium() throws Exception  {
        uf = new UnionFind(new FileInputStream(PATH + "data/mediumUF.txt"));
        assertEquals("Unexpected number of sets", 625, uf.count());
    }

    @Test
    public void testLarge() throws Exception  {
        uf = new UnionFind(new FileInputStream(PATH + "data/largeUF.txt"));
        assertEquals("Unexpected number of sets", 1000000, uf.count());
    }
}
