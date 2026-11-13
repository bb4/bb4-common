package com.barrybecker4.common.util

import java.util as ju

import scala.collection.Set
import scala.jdk.CollectionConverters.*

/**
  * An LRU cache backed by Java's access-order LinkedHashMap.
  * This cache has a fixed maximum number of entries (cacheSize).
  * If the cache is full and another entry is added, the LRU (least recently used) entry is evicted.
  * @param cacheSize the maximum number of entries that will be kept in this cache.
  */
class LRUCache[K, V](var cacheSize: Int) {

  private val map = new ju.LinkedHashMap[K, V](cacheSize, 0.75f, true)

  /** Retrieves an entry from the cache.
    * The retrieved entry becomes the MRU (most recently used) entry.
    * @param key the key whose associated value is to be returned.
    * @return the value associated with this key, if present in the cache.
    */
  def get(key: K): Option[V] =
    val value = map.get(key)
    if value != null || map.containsKey(key) then Some(value) else None

  def apply(key: K): V = get(key).get

  /** Adds an entry to this cache.
    * If the cache is full, the LRU (least recently used) entry is dropped.
    * @param key the key with which the specified value is to be associated.
    * @param value a value to be associated with the specified key.
    */
  def put(key: K, value: V): Unit = {
    map.put(key, value)
    if map.size() > cacheSize then removeEldestEntry()
  }

  /** Clears the cache. */
  def clear(): Unit = map.clear()

  /** @return the number of entries currently in the cache.*/
  def numEntries: Int = map.size()

  /** @return all cache keys. */
  def getAllKeys: Set[K] = map.keySet().asScala.toSet

  override def toString: String = {
    val content = new StringBuilder()
    val snapshot = new ju.ArrayList(map.entrySet())
    var i = 0
    val n = snapshot.size()
    while i < n do
      val entry = snapshot.get(i)
      content.append("[").append(entry.getKey).append(" : ").append(entry.getValue).append("]")
      i += 1
    content.toString()
  }

  private def removeEldestEntry(): Unit =
    map.remove(map.keySet().iterator().next())
}
