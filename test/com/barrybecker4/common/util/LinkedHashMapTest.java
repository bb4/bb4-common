// Copyright by Barry G. Becker, 2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.util;


import junit.framework.TestCase;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Test LRUCache behavior.
 * @author Barry Becker
 */
public class LinkedHashMapTest extends TestCase {

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";
    private static final String FOUR = "four";
    private static final String FIVE= "five";

    private LinkedHashMap<String,String> map;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        map = new LinkedHashMap<String,String>(3);

        map.put("2", TWO);
        map.put("1", ONE);
        map.put("3", THREE);
    }

    public void testNumEntries() {

        assertEquals("Unexpected number of entries. ", 3, map.size());

        map.put ("4", FOUR);
        assertEquals("Unexpected number of entries. ", 4, map.size());
    }


    public void testKeyOrder() {

        Iterator<String> keys = map.keySet().iterator();

        assertEquals("2", keys.next());
        assertEquals("1", keys.next());
        assertEquals("3", keys.next());
    }

    public void testKeyOrderAfterModify() {

        map.remove("1");
        map.put("5", FIVE);
        map.put("1", ONE);
        Iterator<String> keys = map.keySet().iterator();

        System.out.println("keys="+ keys);
        assertEquals("2", keys.next());
        assertEquals("3", keys.next());
        assertEquals("5", keys.next());
        assertEquals("1", keys.next());
    }


}
