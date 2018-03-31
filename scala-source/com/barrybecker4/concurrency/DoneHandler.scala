/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.concurrency

/**
  * Called when a task completes with a result.
  * @author Barry Becker
  */
trait DoneHandler[T] {
  def done(result: T): Unit
}
