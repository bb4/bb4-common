/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/** Represents an immutable location of something in integer coordinates.
  * @author Barry Becker
  */
case class IntLocation(row: Int, col: Int) extends Location {

  def this(loc: Location) = {
    this(loc.row, loc.col)
  }

  override def getX: Int = col
  override def getY: Int = row
  override def incrementOnCopy(rowChange: Int, colChange: Int): IntLocation =
    IntLocation(row + rowChange, col + colChange)
}
