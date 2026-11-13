/* Copyright by Barry G. Becker, 2000-2026. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry


/** An immutable box defined by 2 locations. The coordinates have the resolution of integers.
  * Corners are inclusive. Width/height are the distance between corners (not cell counts).
  */
case class Box private (rowMin: Int, colMin: Int, rowMax: Int, colMax: Int) {
  private val topLeftCorner = IntLocation(rowMin, colMin)
  private val bottomRightCorner = IntLocation(rowMax, colMax)

  /** Two points that define the box (order does not matter). */
  def this(pt0: Location, pt1: Location) = {
    this(Math.min(pt0.row, pt1.row), Math.min(pt0.col, pt1.col),
         Math.max(pt0.row, pt1.row), Math.max(pt0.col, pt1.col))
  }

  /** Degenerate box consisting of a point in space */
  def this(pt0: Location) = { this(pt0, pt0) }

  /** Distance between left and right columns (not number of cells). */
  def getWidth: Int = colMax - colMin

  /** Distance between top and bottom rows (not number of cells). */
  def getHeight: Int = rowMax - rowMin

  def getMaxDimension: Int = Math.max(getWidth, getHeight)
  def getTopLeftCorner: IntLocation = topLeftCorner
  def getBottomRightCorner: IntLocation = bottomRightCorner
  def getMinRow: Int = rowMin
  def getMinCol: Int = colMin
  def getMaxRow: Int = rowMax
  def getMaxCol: Int = colMax
  def getArea: Int = getWidth * getHeight

  def contains(pt: Location): Boolean =
    pt.row >= rowMin && pt.row <= rowMax && pt.col >= colMin && pt.col <= colMax

  def contains(box: Box): Boolean =
    contains(box.topLeftCorner) && contains(box.bottomRightCorner)

  def expandBy(loc: Location): Box = expandBy(new IntLocation(loc))

  /** @return new box that includes `loc`, or `this` if `loc` is already inside. */
  def expandBy(loc: IntLocation): Box = {
    val (r0, c0, r1, c1) = BoxExpansion.includePoint(rowMin, colMin, rowMax, colMax, loc.row, loc.col)
    if (r0 == rowMin && c0 == colMin && r1 == rowMax && c1 == colMax) this
    else Box(r0, c0, r1, c1)
  }

  def scaleBy(scale: Int): Box =
    Box(scale * rowMin, scale * colMin, scale * rowMax, scale * colMax)

  /** True if `location` lies on this box's perimeter (including corners). */
  def isOnEdge(location: Location): Boolean =
    contains(location) &&
      (location.row == rowMin || location.row == rowMax ||
       location.col == colMin || location.col == colMax)

  /** True if `location` is one of the four corners of this box. */
  def isOnCorner(location: Location): Boolean =
    contains(location) &&
      (location.row == rowMin || location.row == rowMax) &&
      (location.col == colMin || location.col == colMax)

  /** Expand all borders by `amount`, clamped to a 1-based board of size maxRow×maxCol. */
  def expandGloballyBy(amount: Int, maxRow: Int, maxCol: Int): Box = {
    val (r0, c0, r1, c1) =
      BoxExpansion.expandGlobally(rowMin, colMin, rowMax, colMax, amount, maxRow, maxCol)
    Box(r0, c0, r1, c1)
  }

  /** If within `threshold` of a board edge, extend that border all the way to the edge. */
  def expandBordersToEdge(threshold: Int, maxRow: Int, maxCol: Int): Box = {
    val (r0, c0, r1, c1) =
      BoxExpansion.snapBordersToEdge(rowMin, colMin, rowMax, colMax, threshold, maxRow, maxCol)
    Box(r0, c0, r1, c1)
  }

  /** @return intersection with `box`, or None if there is no overlap. */
  def intersectWith(box: Box): Option[Box] =
    BoxExpansion.intersection(
      rowMin, colMin, rowMax, colMax,
      box.rowMin, box.colMin, box.rowMax, box.colMax
    ).map { case (r0, c0, r1, c1) => Box(r0, c0, r1, c1) }

  override def toString: String =
    s"Box:$topLeftCorner - $bottomRightCorner" //NON-NLS
}

object Box {

  def apply(rowMin: Int, colMin: Int, rowMax: Int, colMax: Int): Box = {
    val (r0, r1) = if (rowMin <= rowMax) (rowMin, rowMax) else (rowMax, rowMin)
    val (c0, c1) = if (colMin <= colMax) (colMin, colMax) else (colMax, colMin)
    new Box(r0, c0, r1, c1)
  }

  /** Same as [[Box.expandBy]] from an existing box and point. */
  def apply(oldBox: Box, point: Location): Box = oldBox.expandBy(point)
}
