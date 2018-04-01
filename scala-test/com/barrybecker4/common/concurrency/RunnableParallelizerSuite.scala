/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

import java.util

import org.scalatest.FunSuite


/**
  * @author Barry Becker
  */
class RunnableParallelizerSuite extends FunSuite {
  private var parallelizer = new RunnableParallelizer()


  test("runParallelTasks(") {
    val workers = new util.ArrayList[Runnable]()
    var i = 1000
    while (i < 100000) {
      workers.add(new Worker(i))
      i += 10000
    }
    parallelizer.invokeAllRunnables(workers)
    var result = ""
    import scala.collection.JavaConversions._
    for (worker <- workers) {
      result += worker.asInstanceOf[Worker].getTotal + " "
    }
    // these results are always in the same order
    assertResult("999000 120989000 440979000 960969000 1680959000 2600949000 3720939000 5040929000 6560919000 8280909000 ") {
      result
    }
  }

  /** do some long running task like sum up some number */
  private class Worker private[concurrency](var num: Int) extends Runnable {
    private[concurrency] var total: Long = 0

    override def run(): Unit = {
      var i = 0
      while (i < num) {
        total += i
        i += 1
      }
    }

    def getTotal: Long = total
  }

}
