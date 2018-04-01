/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

import java.util
import java.util.concurrent._


/**
  * Using this class you should be able to easily parallelize a set of long running tasks.
  * Immutable.
  * @tparam T - the result type
  * @author Barry Becker
  */
class RunnableParallelizer[T](numThreads: Int = AbstractParallelizer.NUM_PROCESSORS)
  extends AbstractParallelizer[T](numThreads) {

  /**
    * Invoke all the workers at once and optionally call doneHandler on the results as they complete.
    * Once all the separate threads have completed their assigned work, you may want to commit the results.
    *
    * @param workers list of workers to execute in parallel.
    */
  def invokeAllRunnables(workers: util.List[Runnable]): Unit = {
    // convert the runnables to callables so the invokeAll api works
    val callables: util.List[Callable[T]] = new util.ArrayList[Callable[T]](workers.size)

    for (r <- 0 until workers.size()) {
      callables.add(Executors.callable(workers.get(r), null.asInstanceOf[T]))
    }

    val futures: util.List[Future[T]] = invokeAll(callables)
    val completionService: ExecutorCompletionService[T] = new ExecutorCompletionService[T](executor)

    for (i <- 0 until callables.size()) {
      completionService.submit(callables.get(i))
    }

    try {
      var i: Int = 0
      while (i < futures.size) {
        val future: Future[T] = completionService.take
        try
          future.get
        catch {
          case e: ExecutionException =>
            e.printStackTrace()
        }
        i += 1
      }
    } catch {
      case e: InterruptedException => e.printStackTrace()
    }
  }
}
