/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.{AppContext, MemoryLogger}
import org.scalatest.funsuite.AnyFunSuite


class SimpleProfilerSuite extends AnyFunSuite {

  test("root entry exists and starts at zero") {
    val profiler = new SimpleProfiler()
    assertResult(0L)(profiler.getEntry(SimpleProfiler.ROOT).getTime)
  }

  test("disabled SimpleProfiler does not accumulate") {
    val profiler = new SimpleProfiler()
    profiler.setEnabled(false)
    profiler.start()
    profiler.stop()
    assertResult(0L)(profiler.getEntry(SimpleProfiler.ROOT).getTime)
  }

  test("print logs formatted root line") {
    val profiler = new SimpleProfiler()
    val logger = new MemoryLogger(Some(new StringBuilder()))
    profiler.setLogger(logger)
    profiler.print()
    val expectedPrefix =
      AppContext.getLabel("PROFILER_TIME_LINE", Array("totalTime", "X")).split("X").head
    assert(logger.toString.startsWith(expectedPrefix), "Unexpected message = " + logger.toString)
  }
}
