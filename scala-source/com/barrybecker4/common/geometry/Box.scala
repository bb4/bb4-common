/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry


/** An immutable box defined by 2 locations. The coordinates have the resolution of integers.
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
  private val topLeftCorner = IntLocation(rowMin, colMin)
  private val bottomRightCorner = IntLocation(rowMax, colMax)

  /** Constructor
    * Two points that define the box.
    * @param pt0 one corner of the box
    * @param pt1 the opposite corner of the box.
    */
  def this(pt0: Location, pt1: Location) = {
    this(Math.min(pt0.row, pt1.row), Math.min(pt0.col, pt1.col),
         Math.max(pt0.row, pt1.row), Math.max(pt0.col, pt1.col))
  }

  /** Degenerate box consisting of a point in space */
  def this(pt0: Location) = { this(pt0, pt0) }

  /** Constructs a box with dimensions of oldBox, but expanded by the specified point
    * @param oldBox box to base initial dimensions on.
    * @param point  point to expand new box by.
    */
  def this(oldBox: Box, point: Location) = {
    this(oldBox.getTopLeftCorner, oldBox.getBottomRightCorner)
    expandBy(point)
  }

  /** @return the width of the box */
  def getWidth: Int = Math.abs(bottomRightCorner.col - topLeftCorner.col)

  /** @return the height of the box */
  def getHeight: Int = Math.abs(bottomRightCorner.row - topLeftCorner.row)

  def getMaxDimension: Int = Math.max(getWidth, getHeight)
  def getTopLeftCorner: IntLocation = topLeftCorner
  def getBottomRightCorner: IntLocation = bottomRightCorner
  def getMinRow: Int = topLeftCorner.row
  def getMinCol: Int = topLeftCorner.col
  def getMaxRow: Int = bottomRightCorner.row
  def getMaxCol: Int = bottomRightCorner.col
  def getArea: Int = getWidth * getHeight

  /** @param pt point to check for containment in the box.
    * @return true if the box contains the specified point
    */
  def contains(pt: Location): Boolean = {
    val row = pt.row
    val col = pt.col
    row >= getMinRow && row <= getMaxRow && col >= getMinCol && col <= getMaxCol
  }

  /** @param box the box to check if contained in this one.
    * @return true if box is completely contained in this box
    */
  def contains(box: Box): Boolean =
    contains(box.getTopLeftCorner) && contains(box.getBottomRightCorner)

  /** Note that the corner locations are immutable so we create new objects for them if they change.
    * @param loc location to expand out box by.
    */
  def expandBy(loc: Location): Box = expandBy(new IntLocation(loc))

  /** @param loc position to extend the box by.
    * @return new Box that includes the specified loc. Box unchanged if loc withing box.
    */
  def expandBy(loc: IntLocation): Box = {
    var newBox = this
    if (loc.row < newBox.topLeftCorner.row)
      newBox = new Box(IntLocation(loc.row, newBox.topLeftCorner.col), newBox.bottomRightCorner)
    if (loc.row > newBox.bottomRightCorner.row)
      newBox = new Box(newBox.topLeftCorner, IntLocation(loc.row, newBox.bottomRightCorner.col))
    if (loc.col < newBox.topLeftCorner.col)
      newBox = new Box(IntLocation(newBox.topLeftCorner.row, loc.col), newBox.bottomRightCorner)
    if (loc.col > newBox.bottomRightCorner.col)
      newBox = new Box(newBox.topLeftCorner, IntLocation(newBox.bottomRightCorner.row, loc.col))
    newBox
  }
  
  def scaleBy(scale: Int): Box = {
    Box(scale * rowMin, scale * colMin, scale * rowMax, scale * colMax)
  }

  /** @param location the location to check if on border.
    * @return true if location is on this box's border
    */
  def isOnEdge(location: Location): Boolean =
    location.row == bottomRightCorner.row || location.row == topLeftCorner.row ||
    location.col == bottomRightCorner.col || location.col == topLeftCorner.col

  /** @param location the location to check if on corner.
    * @return true if location is on this box's border
    */
  def isOnCorner(location: Location): Boolean = location ==
    bottomRightCorner || location == topLeftCorner ||
    ((location.row == bottomRightCorner.row && location.col == topLeftCorner.col) ||
      (location.row == topLeftCorner.row && location.col == bottomRightCorner.col))

  /** @param amount amount to expand all borders of the box by.
    * @param maxRow don't go further than this though.
    * @param maxCol don't go further than this though.
    */
  def expandGloballyBy(amount: Int, maxRow: Int, maxCol: Int): Box = {
    val topLeft = IntLocation(Math.max(topLeftCorner.row - amount, 1), Math.max(topLeftCorner.col - amount, 1))
    val bottomRight = IntLocation(
      Math.min(bottomRightCorner.row + amount, maxRow),
      Math.min(bottomRightCorner.col + amount, maxCol)
    )
    new Box(topLeft, bottomRight)
  }

  /** @param threshold if withing this distance to the edge, extend the box all the way to that edge.
    * @param maxRow    don't go further than this though.
    * @param maxCol    don't go further than this though.
    */
  def expandBordersToEdge(threshold: Int, maxRow: Int, maxCol: Int): Box = {
    var topLeft = topLeftCorner
    var bottomRight = bottomRightCorner
    if (topLeftCorner.row <= threshold + 1)
      topLeft = IntLocation(1, topLeftCorner.col)
    if (topLeftCorner.col <= threshold + 1)
      topLeft = IntLocation(topLeftCorner.row, 1)
    if (maxRow - bottomRightCorner.row <= threshold)
      bottomRight = IntLocation(maxRow, bottomRightCorner.col)
    if (maxCol - bottomRightCorner.col <= threshold)
      bottomRight = IntLocation(bottomRightCorner.row, maxCol)
    new Box(topLeft, bottomRight)
  }

  /** @param box the box to intersect with
    * @return the intersection of this box with another. None if no intersection area.
    */
  def intersectWith(box: Box): Option[Box] = {
    val topLeftY = Math.max(this.getTopLeftCorner.getY, box.getTopLeftCorner.getY)
    val bottomRightY = Math.min(this.getBottomRightCorner.getY, box.getBottomRightCorner.getY)
    if (topLeftY > bottomRightY) return None

    val topLeftX = Math.max(this.getTopLeftCorner.getX, box.getTopLeftCorner.getX)
    val bottomRightX = Math.min(this.getBottomRightCorner.getX, box.getBottomRightCorner.getX)
    if (topLeftX > bottomRightX) return None

    Some(Box(topLeftY, topLeftX, bottomRightY, bottomRightX))
  }

  override def toString: String = {
    val buf = new StringBuilder("Box:") //NON-NLS
    buf.append(topLeftCorner)
    buf.append(" - ")
    buf.append(bottomRightCorner)
    buf.toString
  }
}
