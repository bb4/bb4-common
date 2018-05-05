/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import LRUCacheSuite._
import org.scalatest.FunSuite
/**
  * Test LRUCache behavior.
  * @author Barry Becker
  */
object LRUCacheSuite {
  private val ONE = "one"
  private val TWO = "two"
  private val THREE = "three"
  private val FOUR = "four"
  private val FIVE = "five"
}

class LRUCacheSuite extends FunSuite {


  test("NumEntries(") {
    val lruCache = createCache()
    assertResult(3) { lruCache.numEntries }
    // there are only 3 entries, so 1 gets bumped out.
    lruCache.put("4", FOUR) // 4 3 2

    // we never go above 3.
    assertResult(3) {lruCache.numEntries }
  }

  test("EntryReplaced") {
    val lruCache = createCache()
    lruCache.put("4", FOUR)
    // Since 2 is getting accessed now, it moves to the front of the list.
    // 2 4 3
    assertResult(TWO) { lruCache.get("2") }
    lruCache.put("5", FIVE)
    // 5 2 4
    lruCache.put("4", "second four")
    // 4 5 2
    // Verify cache content.
    assert(lruCache.get("4") == "second four")
    assert(lruCache.get("5") == FIVE)
    assert(lruCache.get("2") == TWO)
    assert(lruCache.get("1") == null)
    // List cache content.
    val content = new StringBuilder
    import scala.collection.JavaConversions._
    for (e <- lruCache.getAll) {
      content.append("[").append(e.getKey).append(" : ").append(e.getValue).append("]")
    }
    val expectedContent = "[4 : second four][5 : five][2 : two]"
    assertResult(expectedContent) { content.toString }
  }

  @throws[Exception]
  def createCache(): LRUCache[String, String] = {
    var lruCache = new LRUCache[String, String](3)
    lruCache.put("1", ONE) // 1
    lruCache.put("2", TWO) // 2 1
    lruCache.put("3", THREE) // 3 2 1
    lruCache
  }
}
