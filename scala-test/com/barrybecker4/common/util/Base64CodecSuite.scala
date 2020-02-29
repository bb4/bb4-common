/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class Base64CodecSuite extends AnyFunSuite {
  test("Compress") {
    val str = "test/com/barrybecker4/common/util"
    val compressed = Base64Codec.compress(str)
    assertResult("eJwrSS0u0U/Oz9VPSiwqqkxKTc5OLTIBCeTm5+mXlmTmAADYrAzD") { compressed }
  }

  test("Uncompress") {
    val uncompressed: String = Base64Codec.decompress("eJwrSS0u0U/Oz9VPSiwqqkxKTc5OLTIBCeTm5+mXlmTmAADYrAzD")
    assertResult("test/com/barrybecker4/common/util") { uncompressed }
  }

  test("CompressLonger") {
    val str = "The quick brown fox jumped over the lazy brown dog. The quick brown fox jumped over " +
      "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
      "fox jumped over the lazy brown dog."
    val compressed = Base64Codec.compress(str)
    assertResult("eJwLyUhVKCzNTM5WSCrKL89TSMuvUMgqzS1ITVHIL0stUigByuckVlVCpVPy0/UUQgavHgB/gUr1") { compressed }
  }

  test("CompressLonger2") {
    val str = "The quick brown fox jumped over the lazy brown dog. The quick brown fox jumped over " +
      "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
      "fox jumped over the lazy brown dog. The quick brown fox jumped over the lazy brXown dog. " +
      "The quick brown fox jumped over " +
      "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
      "fox jumped over the lazy brown dog. The quick brown fox jumped over the lazy brown dog. " +
      "The quick brown fox jumped over " +
      "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
      "fox jumped over the lazy brown dog."
    val compressed = Base64Codec.compress(str)
    assertResult("eJwLyUhVKCzNTM5WSCrKL89TSMuvUMgqzS1ITVHIL0stUigByuckVlVCpVPy0/UUQoaHnojB7LjhqgcAZTLhdQ==") {
      compressed
    }
  }
}
