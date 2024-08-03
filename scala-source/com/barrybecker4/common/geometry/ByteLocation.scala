/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/**
  * Represents an immutable location location of something in byte coordinates.
  * The range of bytes are only -127 to 127.
  * @param brow the row coordinate (0 - 255).
  * @param bcol the column coordinate (0 - 255).
  */
case class ByteLocation(brow: Byte, bcol: Byte) extends Location {
  assert(Math.abs(row) < 128 && Math.abs(col) < 128, "row=" + row + " or col=" + col + " was out of range.")

  def this(irow: Int, icol: Int) = {
    this(irow.toByte, icol.toByte)
  }
  override def row: Int = brow
  override def col: Int = bcol
  override def getX: Int = brow
  override def getY: Int = bcol

  override def incrementOnCopy(rowChange: Int, colChange: Int) =
    new ByteLocation(row + rowChange, col + colChange)
}
