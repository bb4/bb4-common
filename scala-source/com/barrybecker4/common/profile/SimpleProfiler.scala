/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */

package com.barrybecker4.common.profile

object SimpleProfiler {
  val ROOT = "totalTime"
}

/**
  * Use this class to get a single performance number for your application.
  * This profiler just contains a single top level entry for a single timing number.
  * @author Barry Becker
  */
class SimpleProfiler() extends Profiler {

  super.add(SimpleProfiler.ROOT)

  def start(): Unit = start(SimpleProfiler.ROOT)
  def stop(): Unit = stop(SimpleProfiler.ROOT)
}
