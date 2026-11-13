/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.{AppContext, MemoryLogger}
import org.scalatest.funsuite.AnyFunSuite

class ProfilerEntrySuite extends AnyFunSuite {

  test("start stop accumulates controlled clock deltas") {
    var now = 1000L
    val entry = new ProfilerEntry("work", () => now)
    entry.start()
    now = 1025L
    entry.stop()
    assertResult(25L)(entry.getTime)
    assertResult(0.025)(entry.getTimeInSeconds)
    assert(!entry.isRunning)
  }

  test("stop without start does not accumulate time") {
    var now = 5000L
    val entry = new ProfilerEntry("idle", () => now)
    entry.stop()
    assertResult(0L)(entry.getTime)
    assert(!entry.isRunning)
  }

  test("multiple start stop intervals accumulate") {
    var now = 0L
    val entry = new ProfilerEntry("loop", () => now)
    entry.start()
    now = 10L
    entry.stop()
    entry.start()
    now = 40L
    entry.stop()
    assertResult(40L)(entry.getTime)
  }

  test("resetAll clears time and running state including children") {
    var now = 0L
    val parent = new ProfilerEntry("parent", () => now)
    val child = new ProfilerEntry("child", () => now)
    parent.addChild(child)
    parent.start()
    now = 5L
    child.start()
    now = 15L
    child.stop()
    parent.stop()
    parent.resetAll()
    assertResult(0L)(parent.getTime)
    assertResult(0L)(child.getTime)
    assert(!parent.isRunning)
    assert(!child.isRunning)
  }

  test("validateChildTimes throws when children exceed parent") {
    var now = 0L
    val parent = new ProfilerEntry("parent", () => now)
    val child = new ProfilerEntry("child", () => now)
    parent.addChild(child)
    child.start()
    now = 50L
    child.stop()
    parent.start()
    now = 60L
    parent.stop()
    val ex = intercept[IllegalStateException] {
      parent.validateChildTimes()
    }
    assert(ex.getMessage.contains("parent"))
  }

  test("print writes formatted line to logger") {
    var now = 0L
    val entry = new ProfilerEntry("totalTime", () => now)
    entry.start()
    now = 1000L
    entry.stop()
    val logger = new MemoryLogger(Some(new StringBuilder()))
    entry.print("", Some(logger))
    val expectedPrefix =
      AppContext.getLabel("PROFILER_TIME_LINE", Array("totalTime", "X")).split("X").head
    assert(logger.toString.startsWith(expectedPrefix), s"Unexpected: ${logger.toString}")
  }
}
