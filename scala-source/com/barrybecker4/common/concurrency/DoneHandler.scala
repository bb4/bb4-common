/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

/**
  * Called when a task completes with a result.
  * @author Barry Becker
  */
@deprecated("This trait will be removed once everything is converted to scala. " +
  "Use parallel arrays instead of *Parallelizer classes",
  "bb4-common 1.6")
trait DoneHandler[T] {
  def done(result: T): Unit
}
