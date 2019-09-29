/* Copyright by Barry G. Becker, 2019. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

class StubWorker(n: Int) extends Worker {

  /** Compute the value to be returned by the get method.
    * @return the result. Must not be null.
    */
  override def construct: Long = {
    var total: Long = 0
    for (i <- 1 to n) {
      total += i
    }
    total
  }

  override def finished(): Unit = {
    println("finished")
  }
}

