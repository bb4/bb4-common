package com.barrybecker4.common.util

import org.scalatest.funsuite.AnyFunSuite

class BoundedPriorityQueueSuite extends AnyFunSuite {

  implicit val order: Ordering[Int] = (a, b) => a.compareTo(b)
  //val sortByWeight: Ordering[Path] = (a, b) => a.weight.compareTo(b.weight)

  test("Test empty queue") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    assert(queue.isEmpty)
    assert(queue.size == 0)
  }

  test("Add elements to queue without exceeding maxSize") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue += 1
    queue += 2
    queue += 3
    assert(queue.size == 3)
    assert(queue.toList.sorted == List(1, 2, 3))
  }

  test("Add elements to queue exceeding maxSize") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue += 5
    queue += 2
    queue += 4
    queue += 1
    queue += 3
    assert(queue.size == 3)
    assert(queue.toList.sorted == List(3, 4, 5))
  }

  test("Dequeue elements from queue") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue += 1
    queue += 3
    queue += 2
    assert(queue.size == 3)
    assert(queue.dequeue() == 1)
    assert(queue.size == 2)
    assert(queue.dequeue() == 2)
    assert(queue.size == 1)
    assert(queue.dequeue() == 3)
    assert(queue.isEmpty)
  }

  test("Clear the queue") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue += 1
    queue += 2
    queue += 3
    assert(queue.size == 3)
    queue.clear()
    assert(queue.isEmpty)
  }

  test("Add multiple elements at once") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue.addAll(Seq(5, 1, 3, 2, 4))
    assert(queue.size == 3)
    assert(queue.toList.sorted == List(3, 4, 5))
  }

  test("Check iterator") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue += 1
    queue += 2
    queue += 3
    val it = queue.iterator
    assert(it.toList.sorted == List(1, 2, 3))
  }

  test("Check knownSize") {
    val queue = new BoundedPriorityQueue[Int](3)(order)
    queue += 1
    queue += 2
    assert(queue.knownSize == 2)
  }
}