/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

import org.scalatest.funsuite.AnyFunSuite

class ThreadUtilSuite extends AnyFunSuite {

  test("sleep zero returns immediately") {
    val t0 = System.nanoTime()
    ThreadUtil.sleep(0)
    assert(System.nanoTime() - t0 < 1_000_000_000L)
  }

  test("sleep small delay") {
    val t0 = System.nanoTime()
    ThreadUtil.sleep(20)
    assert(System.nanoTime() - t0 >= 10_000_000L)
  }
}
