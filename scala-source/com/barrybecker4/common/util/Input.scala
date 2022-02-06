/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import com.barrybecker4.common.format.FormatUtil

import java.io.{BufferedReader, IOException, InputStreamReader}
import java.math.BigInteger

/**
  * Convenience methods for getting user input entered from the keyboard.
  * Consider using Scanner instead of this class.
  * @author Barry Becker
  */
object Input {
  /**
    * Get a number from the user.
    * @param prompt query string to prompt the user for a response.
    * @return an integer number.
    */
  def getLong(prompt: String): Long = getLong(prompt, Long.MinValue, Long.MaxValue)

  /** Get a number from the user.
    * Continues to ask until a valid number provided.
    * @param prompt query string to prompt the user for a response.
    * @param min    minimum acceptable value.
    * @param max    the maximum number allowed to be entered.
    * @return an integer number between 0 and max.
    */
  @throws[IOException]
  def getLong(prompt: String, min: Long, max: Long): Long = {
    var value: Long = 0
    var valid: Boolean = false
    while (!valid) { // give them another chance if not valid.
      println(prompt)
      val inp = new InputStreamReader(System.in)
      val br = new BufferedReader(inp)
      val str = br.readLine
      try {
        value = str.toLong
        valid = true
        if (value < min) {
          println("You must enter a number greater than " + FormatUtil.formatNumber(min))
          valid = false
        }
        else if (value > max) {
          println("That number is too big! It must be smaller than " + FormatUtil.formatNumber(max))
          valid = false
        }
      } catch {
        case nfe: NumberFormatException =>
          println("Hey! What kind of number is that? ")
          valid = false
      }
    }
    value
  }

  /** Get a potentially huge number from the user.
    * Continues to ask until a valid number provided.
    * There is mo limit to the amount of precision.
    * @param prompt query string to prompt the user for a response.
    * @return an big integer number
    */
  @throws[IOException]
  def getBigInteger(prompt: String): BigInteger = {
    var value = new BigInteger("0")
    var valid = false
    while (!valid) {
      println(prompt)
      val inp = new InputStreamReader(System.in)
      val br = new BufferedReader(inp)
      try {
        value = new BigInteger(br.readLine)
        valid = true
      } catch {
        case e: NumberFormatException =>
          println("That was not a valid number. Try again.")
          valid = false
      }
    }
    value
  }

  /** Get a string from the user.
    * todo: add an optional regexp argument.
    * @return input string.
    */
  @throws[IOException]
  def getString(prompt: String): String = {
    println(prompt)
    val inp = new InputStreamReader(System.in)
    val br = new BufferedReader(inp)
    br.readLine
  }
}