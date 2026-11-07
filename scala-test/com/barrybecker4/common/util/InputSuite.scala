/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayInputStream, IOException}
import java.math.BigInteger


/**
  * Tests for Input utility methods with mocked System.in.
  * @author Barry Becker
  */
class InputSuite extends AnyFunSuite {

  private def withSystemIn(input: String)(testCode: => Unit): Unit = {
    val originalIn = System.in
    try {
      System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")))
      testCode
    } finally {
      System.setIn(originalIn)
    }
  }

  test("getLong with valid input") {
    withSystemIn("42\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(42L) { result }
    }
  }

  test("getLong with negative input") {
    withSystemIn("-123\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(-123L) { result }
    }
  }

  test("getLong with zero") {
    withSystemIn("0\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(0L) { result }
    }
  }

  test("getLong with max long value") {
    withSystemIn(s"${Long.MaxValue}\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(Long.MaxValue) { result }
    }
  }

  test("getLong with min long value") {
    withSystemIn(s"${Long.MinValue}\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(Long.MinValue) { result }
    }
  }

  test("getLong with range accepts value in range") {
    withSystemIn("50\n") {
      val result = Input.getLong("Enter a number:", 10, 100)
      assertResult(50L) { result }
    }
  }

  test("getLong with range accepts minimum value") {
    withSystemIn("10\n") {
      val result = Input.getLong("Enter a number:", 10, 100)
      assertResult(10L) { result }
    }
  }

  test("getLong with range accepts maximum value") {
    withSystemIn("100\n") {
      val result = Input.getLong("Enter a number:", 10, 100)
      assertResult(100L) { result }
    }
  }

  test("getLong with range rejects value below minimum then accepts valid") {
    withSystemIn("5\n50\n") {
      val result = Input.getLong("Enter a number:", 10, 100)
      assertResult(50L) { result }
    }
  }

  test("getLong with range rejects value above maximum then accepts valid") {
    withSystemIn("150\n50\n") {
      val result = Input.getLong("Enter a number:", 10, 100)
      assertResult(50L) { result }
    }
  }

  test("getLong rejects invalid format then accepts valid") {
    withSystemIn("not-a-number\n42\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(42L) { result }
    }
  }

  test("getLong rejects empty input then accepts valid") {
    withSystemIn("\n42\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(42L) { result }
    }
  }

  test("getLong rejects multiple invalid inputs before accepting valid") {
    withSystemIn("abc\n\n123.45\n42\n") {
      val result = Input.getLong("Enter a number:")
      assertResult(42L) { result }
    }
  }

  test("getBigInteger with valid input") {
    withSystemIn("12345\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(new BigInteger("12345")) { result }
    }
  }

  test("getBigInteger with zero") {
    withSystemIn("0\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(BigInteger.ZERO) { result }
    }
  }

  test("getBigInteger with negative value") {
    withSystemIn("-999999\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(new BigInteger("-999999")) { result }
    }
  }

  test("getBigInteger with very large number") {
    val huge = "123456789012345678901234567890"
    withSystemIn(s"$huge\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(new BigInteger(huge)) { result }
    }
  }

  test("getBigInteger rejects invalid format then accepts valid") {
    withSystemIn("not-a-number\n999\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(new BigInteger("999")) { result }
    }
  }

  test("getBigInteger rejects decimal then accepts valid integer") {
    withSystemIn("123.45\n12345\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(new BigInteger("12345")) { result }
    }
  }

  test("getBigInteger rejects multiple invalid inputs before accepting valid") {
    withSystemIn("abc\n\n3.14\n42\n") {
      val result = Input.getBigInteger("Enter a big number:")
      assertResult(new BigInteger("42")) { result }
    }
  }

  test("getString returns simple text") {
    withSystemIn("hello world\n") {
      val result = Input.getString("Enter text:")
      assertResult("hello world") { result }
    }
  }

  test("getString returns empty string") {
    withSystemIn("\n") {
      val result = Input.getString("Enter text:")
      assertResult("") { result }
    }
  }

  test("getString with numbers") {
    withSystemIn("12345\n") {
      val result = Input.getString("Enter text:")
      assertResult("12345") { result }
    }
  }

  test("getString with special characters") {
    withSystemIn("!@#$%^&*()\n") {
      val result = Input.getString("Enter text:")
      assertResult("!@#$%^&*()") { result }
    }
  }

  test("getString with spaces") {
    withSystemIn("  leading and trailing  \n") {
      val result = Input.getString("Enter text:")
      assertResult("  leading and trailing  ") { result }
    }
  }

  test("getString with unicode") {
    withSystemIn("Hello 世界 🌍\n") {
      val result = Input.getString("Enter text:")
      assertResult("Hello 世界 🌍") { result }
    }
  }
}
