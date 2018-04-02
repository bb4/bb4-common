/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/** Represents a location location of something in integer coordinates.
  * Immutable. Use MutableIntLocation if you really need to modify it (rare).
  * @author Barry Becker
  */
@SerialVersionUID(1)
case class IntLocation(row: Int, col: Int) extends Location {

  def this(loc: Location) {
    this(loc.getRow, loc.getCol)
  }

  override def getRow: Int = row
  override def getCol: Int = col
  override def getX: Int = col
  override def getY: Int = row
  override def copy = IntLocation(row, col)
  override def incrementOnCopy(rowChange: Int, colChange: Int) = IntLocation(row + rowChange, col + colChange)
}