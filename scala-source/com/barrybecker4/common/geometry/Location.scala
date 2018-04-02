/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */

package com.barrybecker4.common.geometry

import java.awt.geom.Point2D

/** Represents a location location of something in coordinates.
  * Immutable. Use MutableIntLocation if you really need to modify it (rare).
  * @author Barry Becker
  */
trait Location extends Serializable {
  def getRow: Int
  def getCol: Int
  def getX: Int
  def getY: Int
  def copy: Location

  /** Checks to see if the given location has the same coordinates as this one.
    * @param location The location whose coordinates are to be compared.
    * @return true  The location's coordinates exactly equal this location's.
    */
  override def equals(location: Any): Boolean = {
    if (!location.isInstanceOf[Location]) return false
    val loc = location.asInstanceOf[Location]
    (loc.getRow == getRow) && (loc.getCol == getCol)
  }

  /** If override equals, should also override hashCode */
  override def hashCode: Int = 100 * getRow + getCol

  /** @param loc another location to measure distance from.
    * @return the euclidean distance from this location to another.
    */
  def getDistanceFrom(loc: Location): Double = {
    val xDif = Math.abs(getCol - loc.getCol)
    val yDif = Math.abs(getRow - loc.getRow)
    Math.sqrt(xDif * xDif + yDif * yDif)
  }

  /** @param loc another arbitrary floating point location to measure distance from.
    * @return the euclidean distance from this location to another.
    */
  def getDistanceFrom(loc: Point2D): Double = {
    val xDif = Math.abs(getRow - loc.getX)
    val yDif = Math.abs(getCol - loc.getY)
    Math.sqrt(xDif * xDif + yDif * yDif)
  }

  /** @return an immutable copy of the original incremented by the amount specified. */
  def incrementOnCopy(rowChange: Int, colChange: Int): Location
  def incrementOnCopy(loc: Location): Location = incrementOnCopy(loc.getRow, loc.getCol)
  def decrementOnCopy(loc: Location): Location = incrementOnCopy(-loc.getRow, -loc.getCol)

  /** @return the string form*/
  override def toString: String = "(row=" + getRow + ", column=" + getCol + ")" //NON-NLS
}