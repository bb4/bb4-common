/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import java.io.{FileInputStream, InputStream}
import org.scalatest.funsuite.AnyFunSuite
import UnionFindSuite._

/**
  * @author Barry Becker
  */
object UnionFindSuite  {
  protected val PATH = "scala-test/com/barrybecker4/common/util/"
}

class UnionFindSuite extends AnyFunSuite {

  test("Tiny") {
    val is: InputStream = new FileInputStream(PATH + "data/tinyUF.txt")
    val uf = UnionFind.create(is)
    assertResult(2) {uf.getCount}
  }

  test("Medium") {
    val is: InputStream = new FileInputStream(PATH + "data/mediumUF.txt")
    val uf = UnionFind.create(is)
    assertResult(3) {uf.getCount}
  }

  test("Find") {
    val uf = new UnionFind(9)
    uf.union(2, 4)
    uf.union(6, 8)
    assertResult(7) { uf.getCount }
    assertResult(2) { uf.find(2) }
    assertResult(2) { uf.find(4) }
    assertResult(6) { uf.find(6) }
    assertResult(6) { uf.find(8) }
    assertResult(1) { uf.find(1) }
    assertResult(3) { uf.find(3) }
    uf.union(2, 8)
    assertResult(2) { uf.find(2) }
    assertResult(2) { uf.find(4) }
    assertResult(2) { uf.find(6) }
    assertResult(2) { uf.find(8) }
  }

  test("Connected") {
    val uf = new UnionFind(7)
    uf.union(1, 3)
    uf.union(4, 0)
    assert(!uf.connected(4, 3))
    uf.union(0, 1)
    assert(uf.connected(4, 3))
  }
}
