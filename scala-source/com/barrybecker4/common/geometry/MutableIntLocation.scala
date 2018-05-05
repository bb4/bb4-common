/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */

package com.barrybecker4.common.geometry

/** Represents a location of something in coordinates.
  * Use this version if you really need it to be mutable
  * This version <i>is</i> mutable.
  * @param row the row  coordinate (0 - 255)
  * @param col the column coordinate (0 - 255)
  * @author Barry Becker
  */
final class MutableIntLocation(var row: Int, var col: Int) extends Location {

  def this(loc: Location) {
    this(loc.getRow, loc.getCol)
  }

  def setRow(row: Int): Unit = {
    this.row = row.toByte
  }

  def getRow: Int = row
  def getCol: Int = col
  def getX: Int = col
  def getY: Int = row
  def copy = new MutableIntLocation(row, col)

  def setCol(col: Int): Unit = {
    this.col = col.toByte
  }

  def incrementRow(rowChange: Int): Unit = {
    row += rowChange
  }

  def incrementCol(colChange: Int): Unit = {
    col += colChange
  }

  def increment(rowChange: Int, colChange: Int): Unit = {
    incrementRow(rowChange)
    incrementCol(colChange)
  }

  def incrementOnCopy(rowChange: Int, colChange: Int) =
    new MutableIntLocation(row + rowChange, col + colChange)
}

