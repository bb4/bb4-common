/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */

package com.barrybecker4.common.util

import org.scalatest.FunSuite
import FileUtil._


/**
  * @author Barry Becker
  */
class FileUtilSuite extends FunSuite {
  test("FilesInDirectory (relative)") {
    val dir = "scala-test/com/barrybecker4/common/util"
    val files = getFilesInDirectory(dir)
    assertResult(5) {files.length }
  }

  test("FilesInDirectory (absolute - from root)") {
    val dir = FileUtil.getHomeDir + "scala-test/com/barrybecker4/common/util"
    val files = getFilesInDirectory(dir)
    assertResult(5) { files.length }
  }

  test("read text from file") {
    assertResult("10\n4 3\n3 8\n6 5\n9 4\n2 1\n8 9\n5 0\n7 2\n6 1\n1 0\n6 7\n") {
      readTextFile("scala-test/com/barrybecker4/common/util/data/tinyUF.txt")
    }
  }

  test("getUrl for tinyUF") {
    assert(getURL("com/barrybecker4/common/util/data/tinyUF.txt") != null)
  }
  test("getUrl for aa") {
    assert(getURL("com/barrybecker4/common/util/data/aa.txt") != null)
  }
  test("getUrl for BB") {
    assert(getURL("com/barrybecker4/common/util/data/BB.txt") != null)
  }
  test("getUrl for BB when specified as bb") {
    assert(getURL("com/barrybecker4/common/util/data/bb.txt") != null)
  }
}
