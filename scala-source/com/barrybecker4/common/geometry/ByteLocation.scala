/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/**
  * Represents a location location of something in byte coordinates.
  * The range of bytes are only -127 to 127.
  * Immutable. Use MutableIntLocation if you really need to modify it (rare).
  * @param row the row coordinate (0 - 255).
  * @param col the column coordinate (0 - 255).
  * @author Barry Becker
  */
@SerialVersionUID(1)
case class ByteLocation(row: Byte, col: Byte) extends Location {
  assert(Math.abs(row) < 128 && Math.abs(col) < 128, "row=" + row + " or col=" + col + " was out of range.")

  def this(irow: Int, icol: Int) {
    this(irow.toByte, icol.toByte)
  }

  override def getRow: Int = row
  override def getCol: Int = col
  override def getX: Int = col
  override def getY: Int = row
  override def copy = ByteLocation(row, col)
  override def incrementOnCopy(rowChange: Int, colChange: Int) =
    new ByteLocation(row + rowChange, col + colChange)
}

