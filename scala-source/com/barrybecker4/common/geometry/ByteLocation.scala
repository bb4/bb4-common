/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/**
  * Represents an immutable location of something in byte coordinates.
  * The range of each coordinate is [-128, 127].
  * @param brow the row coordinate [-128, 127].
  * @param bcol the column coordinate [-128, 127].
  */
// Note: changing serialVersionUID breaks deserialization of stored ByteLocation objects.
@SerialVersionUID(1L)
case class ByteLocation(brow: Byte, bcol: Byte) extends Location {

  def this(irow: Int, icol: Int) = {
    // Auxiliary constructors must call this(...) first; require after still rejects bad Ints.
    this(irow.toByte, icol.toByte)
    require(irow >= -128 && irow <= 127, s"row $irow out of byte range [-128, 127]")
    require(icol >= -128 && icol <= 127, s"col $icol out of byte range [-128, 127]")
  }
  override def row: Int = brow
  override def col: Int = bcol
  override def getX: Int = bcol
  override def getY: Int = brow

  override def incrementOnCopy(rowChange: Int, colChange: Int) =
    new ByteLocation(row + rowChange, col + colChange)
}
