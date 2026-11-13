/* Copyright by Barry G. Becker, 2000-2026. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/**
  * Immutable location with coordinates stored as signed bytes (range [-128, 127]).
  *
  * Prefer companion construction: `ByteLocation(row, col)`.
  * `new ByteLocation(row, col)` remains for Java callers.
  *
  * Note: changing serialVersionUID or the private byte field names breaks
  * deserialization of stored ByteLocation objects.
  */
@SerialVersionUID(1L)
case class ByteLocation private (private val brow: Byte, private val bcol: Byte) extends Location {

  /** Int construction with range check. Preferred from Java as `new ByteLocation(r, c)`. */
  def this(row: Int, col: Int) = {
    this(ByteLocation.toByte(row, "row"), ByteLocation.toByte(col, "col"))
  }

  override def row: Int = brow
  override def col: Int = bcol
  override def getX: Int = bcol
  override def getY: Int = brow

  override def incrementOnCopy(rowChange: Int, colChange: Int) =
    new ByteLocation(row + rowChange, col + colChange)
}

object ByteLocation {

  /** Preferred construction from Int coordinates in [-128, 127]. */
  def apply(row: Int, col: Int): ByteLocation = new ByteLocation(row, col)

  /** Construction from Byte values (always in range). */
  def apply(row: Byte, col: Byte): ByteLocation = new ByteLocation(row, col)

  private def toByte(value: Int, name: String): Byte = {
    require(value >= -128 && value <= 127, s"$name $value out of byte range [-128, 127]")
    value.toByte
  }
}
