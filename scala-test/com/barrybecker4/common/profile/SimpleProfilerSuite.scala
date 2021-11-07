/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.MemoryLogger
import com.barrybecker4.common.concurrency.ThreadUtil
import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class SimpleProfilerSuite extends AnyFunSuite {

  test("ProfilerTime") {
    val profiler = new SimpleProfiler()
    profiler.start()
    ThreadUtil.sleep(10)
    profiler.stop()
    ThreadUtil.sleep(5)
    val entry = profiler.getEntry(SimpleProfiler.ROOT)
    val elapsed = entry.getTime
    // any elapsed time is acceptable, as different machines perform differently.
    assert((elapsed - 19) <= 22, "Invalid elapsed time = " + elapsed)
  }

  test("ProfilerTimeWhenDisabled") {
    val profiler = new SimpleProfiler()
    val entry = profiler.getEntry(SimpleProfiler.ROOT)
    assertResult(0) { entry.getTime }
    profiler.setEnabled(false)
    profiler.start()
    ThreadUtil.sleep(10)
    profiler.stop()
    assertResult(0) { entry.getTime }
  }

  test("ProfilerLogging") {
    val profiler = new SimpleProfiler()
    val logger =  new MemoryLogger(Some(new StringBuilder()))
    profiler.setLogger(logger)
    profiler.start()
    ThreadUtil.sleep(10)
    profiler.stop()
    profiler.print()
    assert(logger.toString.startsWith("Time for totalTime : "), "Unexpected message = " + logger.toString)
  }
}