/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.concurrency

import java.util
import java.util.concurrent.{Callable, ExecutionException, ExecutorCompletionService, Future}


/**
  * Using this class you should be able to easily parallelize a set of long running allable tasks.
  * Immutable.
  * @tparam T - the result object
  * @author Barry Becker
  */
class CallableParallelizer[T](numThreads: Int = AbstractParallelizer.NUM_PROCESSORS)
  extends AbstractParallelizer[T](numThreads) {

  /**
    * Invoke all the workers at once and optionally call doneHandler on the results as they complete.
    * Once all the separate threads have completed their assigned work, you may want to commit the results.
    *
    * @param callables   list of workers to execute in parallel.
    * @param doneHandler gets called for each task as it finishes.
    */
  def invokeAllWithCallback(callables: util.List[Callable[T]], doneHandler: DoneHandler[T]): Unit = {

    val futures: util.List[Future[T]] = invokeAll(callables)
    val completionService: ExecutorCompletionService[T] = new ExecutorCompletionService[T](executor)

    for (i <- 0 until callables.size()) {
      completionService.submit(callables.get(i))
    }

    try {
      var i: Int = 0
      while (i < futures.size) {
        val future: Future[T] = completionService.take
        try {
          val result: T = future.get
          if (doneHandler != null && result != null) doneHandler.done(result)
        } catch {
          case e: ExecutionException =>
            e.printStackTrace()
        }
        i += 1
      }
    } catch {
      case e: InterruptedException =>
        e.printStackTrace()
    }
  }
}
