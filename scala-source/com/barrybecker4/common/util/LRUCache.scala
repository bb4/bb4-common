package com.barrybecker4.common.util

import scala.collection.{Set, mutable}

/**
  * An LRU cache, based on LinkedHashMap (try switching to immutable ListMap).
  * This cache has a fixed maximum number of elements (cacheSize).
  * If the cache is full and another entry is added, the LRU (least recently used) entry is evicted.
  * @param cacheSize the maximum number of entries that will be kept in this cache.
  */
class LRUCache[K, V](var cacheSize: Int) {

  private val map = new mutable.LinkedHashMap[K, V]()

  /** Retrieves an entry from the cache.
    * The retrieved entry becomes the MRU (most recently used) entry.
    * @param key the key whose associated value is to be returned.
    * @return the value associated to this key, or null if no value with this key exists in the cache.
    */
  def get(key: K): Option[V] = {
    access(key)
    map.get(key)
  }
  def apply(key: K): V = {
    access(key)
    map(key)
  }

  private def access(key: K): Unit = {
    if (map.contains(key)) {
      val v: Option[V] = map.remove(key)
      if (v.nonEmpty)
        map.put(key, v.get)  // make sure it shows as recently accessed
    }
  }

  /** Adds an entry to this cache.
    * If the cache is full, the LRU (least recently used) entry is dropped.
    * @param key the key with which the specified value is to be associated.
    * @param value a value to be associated with the specified key.
    */
  def put(key: K, value: V): Unit = {
    map.put(key, value)
    if (map.size > cacheSize) {
      println(s"${map.size} is greater than $cacheSize so removing eldest")
      removeEldestEntry()
    }
    println(s"after putting $key:$value we have")
    println(map.mkString(", "))
  }

  /** Clears the cache. */
  def clear(): Unit = map.clear()

  /** @return the number of entries currently in the cache.*/
  def numEntries: Int = map.size

  /** @return all cache keys. */
  def getAllKeys: Set[K] = map.keySet

  override def toString: String = {
    var content = new StringBuilder()
    for (k <- map.keySet)
      content.append("[").append(k).append(" : ").append(map(k)).append("]")
    content.toString()
  }

  private def removeEldestEntry(): Unit =
    map.remove(map.head._1)
}

