package com.barrybecker4.common.util

import java.util


/**
  * An LRU cache, based on ListMap.
  * This cache has a fixed maximum number of elements (cacheSize).
  * If the cache is full and another entry is added, the LRU (least recently used) entry is dropped.
  * <p>
  * This class is thread-safe. All methods of this class are synchronized.<br>
  * Author: Christian d'Heureuse (<a href="http://www.source-code.biz">www.source-code.biz</a>)<br>
  * License: <a href="http://www.gnu.org/licenses/lgpl.html">LGPL</a>.
  */
object LRUCache {
  /** The <i>load factor</i> is a measure of how full the hash table is allowed to
    * get before its capacity is automatically increased.  75% in this case.
    */
    private val hashTableLoadFactor = 0.75f
}

/**
  * Creates a new LRU cache.
  * @param cacheSize the maximum number of entries that will be kept in this cache.
  */
class LRUCache[K, V](var cacheSize: Int) {

  val hashTableCapacity: Int = Math.ceil(cacheSize / LRUCache.hashTableLoadFactor).toInt + 1

  private val map = new util.LinkedHashMap[K, V](hashTableCapacity, LRUCache.hashTableLoadFactor, true) {
    override protected def removeEldestEntry(eldest: util.Map.Entry[K, V]): Boolean = size > cacheSize
  }

  private var nextThreshold = 1000

  /** Retrieves an entry from the cache.
    * The retrieved entry becomes the MRU (most recently used) entry.
    * @param key the key whose associated value is to be returned.
    * @return the value associated to this key, or null if no value with this key exists in the cache.
    */
  def get(key: K): V = map.get(key)

  /**
    * Adds an entry to this cache.
    * If the cache is full, the LRU (least recently used) entry is dropped.
    * @param key   the key with which the specified value is to be associated.
    * @param value a value to be associated with the specified key.
    */
  def put(key: K, value: V): Unit = {
    map.put(key, value)
    if (numEntries > nextThreshold) nextThreshold *= 10
  }

  /** Clears the cache. */
  def clear(): Unit = map.clear()

  /** @return the number of entries currently in the cache.*/
  def numEntries: Int = map.size

  /** @return a { @code Collection} with a copy of the cache content. */
  def getAll = new util.ArrayList[util.Map.Entry[K, V]](map.entrySet)
}