/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

import java.awt.geom.Point2D

/** Represents a location location of something in coordinates.
  * Immutable. Use MutableIntLocation if you really need to modify it (rare).
  *
  * Concrete subtypes that may be Java-serialized must declare an explicit
  * `serialVersionUID` (Scala `@SerialVersionUID`); it is not inherited from this trait.
  */
trait Location extends Serializable {
  def row: Int
  def col: Int
  def getX: Int
  def getY: Int

  /** Checks to see if the given location has the same coordinates as this one.
    * @param other The value whose coordinates are to be compared.
    * @return true  The location's coordinates exactly equal this location's.
    */
  override def equals(other: Any): Boolean = other match {
    case loc: Location => loc.row == row && loc.col == col
    case _             => false
  }

  /** If override equals, should also override hashCode */
  override def hashCode: Int = 100 * row + col

  /** @param loc another location to measure distance from.
    * @return the euclidean distance from this location to another.
    */
  def getDistanceFrom(loc: Location): Double = {
    val xDif = Math.abs(col - loc.col)
    val yDif = Math.abs(row - loc.row)
    Math.sqrt(xDif * xDif + yDif * yDif)
  }

  /** @param loc another arbitrary floating point location to measure distance from.
    * @return the euclidean distance from this location to another.
    */
  def getDistanceFrom(loc: Point2D): Double = {
    val xDif = Math.abs(row - loc.getX)
    val yDif = Math.abs(col - loc.getY)
    Math.sqrt(xDif * xDif + yDif * yDif)
  }

  /** @return an immutable copy of the original incremented by the amount specified. */
  def incrementOnCopy(rowChange: Int, colChange: Int): Location
  def incrementOnCopy(loc: Location): Location = incrementOnCopy(loc.row, loc.col)
  def decrementOnCopy(loc: Location): Location = incrementOnCopy(-loc.row, -loc.col)

  /** @return the string form*/
  override def toString: String = "(row=" + row + ", column=" + col + ")" //NON-NLS
}
