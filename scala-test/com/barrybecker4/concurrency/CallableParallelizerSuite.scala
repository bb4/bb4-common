/*
 * Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
 */

package com.barrybecker4.concurrency

import java.util
import java.util.concurrent.Callable

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class CallableParallelizerSuite extends FunSuite {
  private var parallelizer = new CallableParallelizer[Result]()


  test("ParallelTasksWithDoneHandler") {
    val finalResult = new StringBuilder()
    var workers = new util.ArrayList[Callable[Result]]()
    var i = 1000
    while (i < 100000) {
      workers.add(new Worker(i))
      i += 10000
    }
    parallelizer.invokeAllWithCallback(workers, new DoneHandler[Result]() {
      override def done(result: Result): Unit = {
        finalResult.append(result.sum).append(" ")
      }
    })

    // these results could be in an y order, but yhe string length shoud be constant
    assertResult("499500 60494500 220489500 840479500 480484500 1300474500 2520464500 1860469500 3280459500 4140454500 ".length) {
      finalResult.length
    }
  }

  /** do some long running task like sum up some number */
  private class Worker private[concurrency](var num: Int) extends Callable[Result] {
    override def call: Result = {
      var total: Long = 0
      var i = 0
      while (i < num) {
        total += i
        i += 1
      }
      Result(total)
    }
  }

}
