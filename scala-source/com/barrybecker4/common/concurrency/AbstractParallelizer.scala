/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

import java.util
import java.util.concurrent.{Callable, ExecutorService, Executors, Future}


object AbstractParallelizer {
  /** The number of processors available on this computer */
  val NUM_PROCESSORS: Int = Runtime.getRuntime.availableProcessors
  /**
    * By default, the number of threads we use is equal to the number of processors
    * (in some cases I read it may be better to add 1 to this, but I have not seen better results doing that.)
    */
  private val DEFAULT_NUM_THREADS: Int = NUM_PROCESSORS
}

/**
  * Using this class you should be able to easily parallelize a set of long running tasks.
  * Immutable.
  * @param numThreads number of threads to distribute the tasks among.
  * @tparam T the result type
  * @author Barry Becker
  */
@deprecated("this method will be removed once everything is converted to scala. Use parallel arrays instead",
  "bb4-common 1.6")
class AbstractParallelizer[T](var numThreads: Int) {
  assert(numThreads > 0)

  /** Recycle threads so we do not create thousands and eventually run out of memory. */
  protected val executor: ExecutorService = Executors.newFixedThreadPool(numThreads)

  /**
    * Constructs with default number of threads.
    */
  def this() {
    this(AbstractParallelizer.DEFAULT_NUM_THREADS)
  }

  /** @return number of threads in the executor thread pool. */
  def getNumThreads: Int = numThreads

  /**
    * Invoke all the workers at once and block until they are all done
    * Once all the separate threads have completed their assigned work, you may want to commit the results.
    *
    * @param callables the callables to invoke concurrently
    * @return list of Future tasks.
    */
  def invokeAll(callables: util.Collection[_ <: Callable[T]]): util.List[Future[T]] = {
    var futures: util.List[Future[T]] = null
    try
      futures = executor.invokeAll(callables)
    catch {
      case ex: InterruptedException =>
        ex.printStackTrace()
    }
    futures
  }
}

