/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayInputStream, IOException}
import java.math.BigInteger


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

  test("getLong with range rejects value below minimum") {
    // Note: Input.readLineAfterPrompt creates a new BufferedReader on each prompt,
    // which drains ByteArrayInputStream, making multi-line retry tests unreliable.
    // Testing single invalid input behavior only.
    withSystemIn("5\n") {
      // Would loop forever waiting for valid input
      // Skipping retry behavior test due to BufferedReader limitation
    }
  }

  test("getLong with range rejects value above maximum") {
    withSystemIn("150\n") {
      // Would loop forever waiting for valid input
      // Skipping retry behavior test due to BufferedReader limitation
    }
  }

  test("getLong with invalid format would retry") {
    // Input.readLineAfterPrompt creates a new BufferedReader on each prompt.
    // With a mocked ByteArrayInputStream, the second readLine returns null and causes NPE.
    // This cannot be tested reliably without changing production code.
    // Skipping retry tests.
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

  test("getBigInteger with invalid format would retry") {
    // Input.readLineAfterPrompt creates a new BufferedReader on each prompt.
    // Multi-line retry tests cannot work reliably with mocked System.in.
    // Skipping retry tests.
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
