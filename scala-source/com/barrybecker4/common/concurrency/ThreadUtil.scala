/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT*/
package com.barrybecker4.common.concurrency

/**
  * @author Barry Becker
  */
object ThreadUtil {

  /** Cause this thread to sleep for specified amount of time while other threads run.
    * @param millis number of seconds to sleep
    */
  def sleep(millis: Int): Unit = {
    if (millis > 0) try
      Thread.sleep(millis)
    catch {
      case e: InterruptedException =>
        e.printStackTrace()
    }
  }
}
