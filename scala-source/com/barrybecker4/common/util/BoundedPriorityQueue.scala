package com.barrybecker4.common.util

import scala.collection.mutable
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.Serializable
import java.util.PriorityQueue as JPriorityQueue
import scala.collection.mutable.Growable
import scala.jdk.CollectionConverters.*


/**
  * Wraps the original PriorityQueue and modifies it such that only the top K elements are retained.
  * The top K elements are defined by an implicit Ordering[A].
  */
class BoundedPriorityQueue[A](maxSize: Int)(implicit ord: Ordering[A])
  extends Iterable[A] with mutable.Growable[A] with Serializable {

  private val backingQueue = new JPriorityQueue[A](maxSize, ord)

  override def iterator: Iterator[A] = backingQueue.iterator.asScala
  override def size: Int = backingQueue.size
  override def knownSize: Int = size

  override def addAll(values: IterableOnce[A]): this.type = {
    values.iterator.foreach { this += _ }
    this
  }

  override def addOne(elem: A): this.type = {
    if (size < maxSize) {
      backingQueue.offer(elem)
    } else {
      maybeReplaceLowest(elem)
    }
    this
  }

  def dequeue(): A = backingQueue.poll()

  override def clear(): Unit = { backingQueue.clear() }

  private def maybeReplaceLowest(a: A): Boolean = {
    val head = backingQueue.peek()
    if (head != null && ord.gt(a, head)) {
      backingQueue.poll()
      backingQueue.offer(a)
    } else {
      false
    }
  }
}