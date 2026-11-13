/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.MemoryLogger
import org.scalatest.funsuite.AnyFunSuite

class ProfilerSuite extends AnyFunSuite {

  test("nested entries maintain parent child relationship") {
    val p = new Profiler()
    p.add("a")
    p.add("b", "a")
    assert(p.getEntry("a").name == "a")
    assert(p.getEntry("b").name == "b")
  }

  test("duplicate top level add is rejected") {
    val p = new Profiler()
    p.add("entry1")
    val ex = intercept[IllegalArgumentException] {
      p.add("entry1")
    }
    assert(ex.getMessage.contains("entry1"))
  }

  test("duplicate child add is rejected") {
    val p = new Profiler()
    p.add("a")
    p.add("b", "a")
    val ex = intercept[IllegalArgumentException] {
      p.add("b", "a")
    }
    assert(ex.getMessage.contains("b"))
  }

  test("unknown entry name has clear error") {
    val p = new Profiler()
    val ex = intercept[IllegalArgumentException] {
      p.getEntry("missing")
    }
    assert(ex.getMessage.contains("missing"))
  }

  test("add with unknown parent fails clearly") {
    val p = new Profiler()
    val ex = intercept[IllegalArgumentException] {
      p.add("child", "noParent")
    }
    assert(ex.getMessage.contains("noParent"))
  }

  test("enabled flag gates start and stop") {
    val p = new Profiler()
    p.add("a")
    p.setEnabled(false)
    p.start("a")
    p.stop("a")
    assertResult(0L)(p.getEntry("a").getTime)
    assert(!p.isEnabled)
    p.setEnabled(true)
    assert(p.isEnabled)
  }

  test("resetAll clears accumulated times") {
    val p = new Profiler()
    p.add("a")
    p.add("b", "a")
    // use real clock briefly; only need non-negative after reset to 0
    p.start("a")
    p.start("b")
    p.stop("b")
    p.stop("a")
    p.resetAll()
    assertResult(0L)(p.getEntry("a").getTime)
    assertResult(0L)(p.getEntry("b").getTime)
  }

  test("print uses logger when set") {
    val p = new Profiler()
    p.add("root")
    val logger = new MemoryLogger(Some(new StringBuilder()))
    p.setLogger(logger)
    p.print()
    assert(logger.toString.nonEmpty)
  }

  test("printMessage uses logger when set") {
    val p = new Profiler()
    val logger = new MemoryLogger(Some(new StringBuilder()))
    p.setLogger(logger)
    p.printMessage("hello")
    assertResult("hello")(logger.toString)
  }

  test("print is skipped when disabled") {
    val p = new Profiler()
    p.add("root")
    val logger = new MemoryLogger(Some(new StringBuilder()))
    p.setLogger(logger)
    p.setEnabled(false)
    p.print()
    assertResult("")(logger.toString)
  }
}
