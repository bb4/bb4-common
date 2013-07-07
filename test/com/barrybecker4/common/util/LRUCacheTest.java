/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.util;


import junit.framework.TestCase;

import java.util.Map;

/**
 * Test LRUCache behavior.
 * @author Barry Becker
 */
public class LRUCacheTest extends TestCase {

    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";
    private static final String FOUR = "four";
    private static final String FIVE= "five";

    private LRUCache<String,String> lruCache;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        lruCache = new LRUCache<String,String>(3);

        lruCache.put ("1", ONE);             // 1
        lruCache.put ("2", TWO);             // 2 1
        lruCache.put ("3", THREE);          // 3 2 1
    }


    public void testNumEntries() {

        assertEquals("Unexpected number of entries. ", 3, lruCache.numEntries());
        // there are only 3 entries, so 1 gets bumped out.
        lruCache.put ("4", FOUR);                           // 4 3 2
        // we never go above 3.
        assertEquals("Unexpected number of entries. ", 3, lruCache.numEntries());
    }

    public void testEntryReplaced() {
        // there are only 3 entries, so 1 gets bumped out.
        lruCache.put ("4", FOUR);                           // 4 3 2

        // Since 2 is getting accessed now, it moves to the front of the list.
        // 2 4 3
        assertEquals("2 not in cache like we expected. ", TWO, lruCache.get("2"));
        lruCache.put ("5", FIVE);
        // 5 2 4
        lruCache.put ("4","second four");
        // 4 5 2

        // Verify cache content.
        assertTrue("Unexpected value for key 4. ", lruCache.get("4").equals("second four"));
        assertTrue("Unexpected value for key 5. ", lruCache.get("5").equals(FIVE));
        assertTrue("Unexpected value for key 2. ", lruCache.get("2").equals(TWO));
        assertNull("We did not expect one to still be there. ", lruCache.get("1"));

        // List cache content.
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String,String> e : lruCache.getAll()) {
            content.append("[").append(e.getKey()).append(" : ").append(e.getValue()).append("]");
        }

        String expectedContent = "[4 : second four][5 : five][2 : two]";

        assertEquals("Unexpected content.",
              expectedContent,  content.toString());
    }

}
