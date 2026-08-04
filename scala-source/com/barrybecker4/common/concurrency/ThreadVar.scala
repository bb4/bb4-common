/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

/**
  * Class to maintain reference to current worker thread under separate synchronization control.
  */
class ThreadVar private[concurrency](initial: Thread) {

  assert(initial != null)
  private var thread: Option[Thread] = Some(initial)

  private[concurrency] def get: Thread = thread.orNull

  private[concurrency] def clear(): Unit = synchronized {
    thread = None
  }

  private[concurrency] def interrupt(): Unit = synchronized {
    thread.foreach(_.interrupt())
    thread = None
  }

  private[concurrency] def start(): Unit = synchronized {
    thread.foreach(_.start())
  }
}
