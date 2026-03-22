/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import org.scalatest.funsuite.AnyFunSuite

class ProfilerSuite extends AnyFunSuite {

  test("nested entries parent time at least child time") {
    val p = new Profiler()
    p.add("a")
    p.add("b", "a")
    p.start("a")
    Thread.sleep(15)
    p.start("b")
    Thread.sleep(15)
    p.stop("b")
    p.stop("a")
    assert(p.getEntry("a").getTime >= p.getEntry("b").getTime)
  }

  test("SimpleProfiler start stop accumulates time") {
    val sp = new SimpleProfiler()
    sp.start()
    Thread.sleep(10)
    sp.stop()
    assert(sp.getEntry(SimpleProfiler.ROOT).getTime >= 0L)
  }
}
