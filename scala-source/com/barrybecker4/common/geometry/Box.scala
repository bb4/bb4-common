/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry


/** A box defined by 2 locations. The coordinates have the resolution of integers.
  * @author Barry Becker
  */
case class Box(var rowMin: Int, var colMin: Int, var rowMax: Int, var colMax: Int) {
  if (rowMin > rowMax) {
    val temp = rowMin
    rowMin = rowMax
    rowMax = temp
  }
  if (colMin > colMax) {
    val temp = colMin
    colMin = colMax
    colMax = temp
  }
  private var topLeftCorner = IntLocation(rowMin, colMin)
  private var bottomRightCorner = IntLocation(rowMax, colMax)

  /** Constructor
    * Two points that define the box.
    * @param pt0 one corner of the box
    * @param pt1 the opposite corner of the box.
    */
  def this(pt0: Location, pt1: Location) {
    this(Math.min(pt0.getRow, pt1.getRow), Math.min(pt0.getCol, pt1.getCol),
         Math.max(pt0.getRow, pt1.getRow), Math.max(pt0.getCol, pt1.getCol))
  }

  /** Degenerate box consisting of a point in space */
  def this(pt0: Location) { this(pt0, pt0) }

  /** Constructs a box with dimensions of oldBox, but expanded by the specified point
    * @param oldBox box to base initial dimensions on.
    * @param point  point to expand new box by.
    */
  def this(oldBox: Box, point: Location) {
    this(oldBox.getTopLeftCorner, oldBox.getBottomRightCorner)
    expandBy(point)
  }

  /** @return the width of the box */
  def getWidth: Int = Math.abs(bottomRightCorner.getCol - topLeftCorner.getCol)

  /** @return the height of the box */
  def getHeight: Int = Math.abs(bottomRightCorner.getRow - topLeftCorner.getRow)

  def getMaxDimension: Int = Math.max(getWidth, getHeight)
  def getTopLeftCorner: IntLocation = topLeftCorner
  def getBottomRightCorner: IntLocation = bottomRightCorner
  def getMinRow: Int = topLeftCorner.getRow
  def getMinCol: Int = topLeftCorner.getCol
  def getMaxRow: Int = bottomRightCorner.getRow
  def getMaxCol: Int = bottomRightCorner.getCol
  def getArea: Int = getWidth * getHeight

  /** @param pt point to check for containment in the box.
    * @return true if the box contains the specified point
    */
  def contains(pt: Location): Boolean = {
    val row = pt.getRow
    val col = pt.getCol
    row >= getMinRow && row <= getMaxRow && col >= getMinCol && col <= getMaxCol
  }

  /** Note that the corner locations are immutable so we create new objects for them if they change.
    * @param loc location to expand out box by.
    */
  def expandBy(loc: Location): Box = expandBy(new IntLocation(loc))

  /** @param loc position to extend the box by.
    * @return new Box that includes the specified loc. Box unchanged if loc withing box.
    */
  def expandBy(loc: IntLocation): Box = {
    if (loc.getRow < topLeftCorner.getRow)
      new Box(IntLocation(loc.getRow, topLeftCorner.getCol), bottomRightCorner)
    else if (loc.getRow > bottomRightCorner.getRow)
      new Box(topLeftCorner, IntLocation(loc.getRow, bottomRightCorner.getCol))
    if (loc.getCol < topLeftCorner.getCol)
      new Box(IntLocation(topLeftCorner.getRow, loc.getCol), bottomRightCorner)
    else if (loc.getCol > bottomRightCorner.getCol)
      new Box(topLeftCorner, IntLocation(bottomRightCorner.getRow, loc.getCol))
    else this
  }

  /** @param location the location to check if on border.
    * @return true if location is on this box's border
    */
  def isOnEdge(location: Location): Boolean =
    location.getRow == bottomRightCorner.getRow || location.getRow == topLeftCorner.getRow ||
    location.getCol == bottomRightCorner.getCol || location.getCol == topLeftCorner.getCol

  /** @param location the location to check if on corner.
    * @return true if location is on this box's border
    */
  def isOnCorner(location: Location): Boolean = location ==
    bottomRightCorner || location == topLeftCorner ||
    ((location.getRow == bottomRightCorner.getRow && location.getCol == topLeftCorner.getCol) ||
      (location.getRow == topLeftCorner.getRow && location.getCol == bottomRightCorner.getCol))

  /** @param amount amount to expand all borders of the box by.
    * @param maxRow don't go further than this though.
    * @param maxCol don't go further than this though.
    */
  def expandGloballyBy(amount: Int, maxRow: Int, maxCol: Int): Box = {
    val topLeft = IntLocation(Math.max(topLeftCorner.getRow - amount, 1), Math.max(topLeftCorner.getCol - amount, 1))
    val bottomRight = IntLocation(
      Math.min(bottomRightCorner.getRow + amount, maxRow),
      Math.min(bottomRightCorner.getCol + amount, maxCol)
    )
    new Box(topLeft, bottomRight)
  }

  /** @param threshold if withing this distance to the edge, extend the box all the way to that edge.
    * @param maxRow    don't go further than this though.
    * @param maxCol    don't go further than this though.
    */
  def expandBordersToEdge(threshold: Int, maxRow: Int, maxCol: Int): Unit = {
    var topLeft = topLeftCorner
    var bottomRight = bottomRightCorner
    if (topLeftCorner.getRow <= threshold + 1)
      topLeft = IntLocation(1, topLeftCorner.getCol)
    if (topLeftCorner.getCol <= threshold + 1)
      topLeft = IntLocation(topLeftCorner.getRow, 1)
    if (maxRow - bottomRightCorner.getRow <= threshold)
      bottomRight = IntLocation(maxRow, bottomRightCorner.getCol)
    if (maxCol - bottomRightCorner.getCol <= threshold)
      bottomRight = IntLocation(bottomRightCorner.getRow, maxCol)
    new Box(topLeft, bottomRight)
  }

  override def toString: String = {
    val buf = new StringBuilder("Box:") //NON-NLS
    buf.append(topLeftCorner)
    buf.append(" - ")
    buf.append(bottomRightCorner)
    buf.toString
  }
}