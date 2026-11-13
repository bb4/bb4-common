/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/** Represents a location of something in coordinates.
  * Immutable. `getX` / `getY` use Cartesian mapping: x = column, y = row (see [[IntLocation]]).
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

  /** If override equals, should also override hashCode.
    * Mix row and col so map/set keys do not collide for distinct cells
    * (e.g. old `100 * row + col` made (1,100) and (2,0) both hash to 200).
    */
  override def hashCode: Int = (row, col).hashCode

  /** @param loc another location to measure distance from.
    * @return the Euclidean distance from this location to another.
    */
  def getDistanceFrom(loc: Location): Double =
    getDistanceFrom(loc.getX.toDouble, loc.getY.toDouble)

  /** Euclidean distance to an arbitrary Cartesian point (x = column, y = row).
    * Callers with AWT `Point2D` should pass `point.getX` / `point.getY`.
    */
  def getDistanceFrom(x: Double, y: Double): Double = {
    val dx = col - x
    val dy = row - y
    Math.sqrt(dx * dx + dy * dy)
  }

  /** @return an immutable copy of the original incremented by the amount specified. */
  def incrementOnCopy(rowChange: Int, colChange: Int): Location
  def incrementOnCopy(loc: Location): Location = incrementOnCopy(loc.row, loc.col)
  def decrementOnCopy(loc: Location): Location = incrementOnCopy(-loc.row, -loc.col)

  /** @return the string form*/
  override def toString: String = "(row=" + row + ", column=" + col + ")" //NON-NLS
}
