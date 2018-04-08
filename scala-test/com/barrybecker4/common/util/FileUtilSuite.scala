/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */

package com.barrybecker4.common.util

import org.scalatest.FunSuite


/**
  * @author Barry Becker
  */
class FileUtilSuite extends FunSuite {
  test("FilesInDirectory (relative)") {
    val dir = "scala-test/com/barrybecker4/common/util"
    val files = FileUtil.getFilesInDirectory(dir)
    assertResult(5) {files.length }
  }

  test("FilesInDirectory (absolute - from root)") {
    val dir = FileUtil.getHomeDir + "scala-test/com/barrybecker4/common/util"
    val files = FileUtil.getFilesInDirectory(dir)
    assertResult(5) { files.length }
  }
}
