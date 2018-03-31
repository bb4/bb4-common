/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.concurrency

/**
  * Class to maintain reference to current worker thread
  * under separate synchronization control.
  */
class ThreadVar private[concurrency](var thread: Thread) {

  assert(thread != null)

  private[concurrency] def get = thread

  private[concurrency] def clear(): Unit = synchronized {
    thread = null
  }

  private[concurrency] def interrupt(): Unit = synchronized {
    if (thread != null) thread.interrupt()
    thread = null
  }

  private[concurrency] def start(): Unit = synchronized {
    if (thread != null) thread.start()
  }
}