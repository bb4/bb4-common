/* Copyright by Barry G. Becker, 2000-2026. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/** Pure coordinate helpers for expanding and intersecting axis-aligned boxes.
  * Extracted from [[Box]] so the math is small and independently testable.
  *
  * Board-oriented helpers (`expandGlobally`, `snapBordersToEdge`) use a minimum
  * index of 1, matching 1-based game boards.
  */
private[geometry] object BoxExpansion {

  /** Bounds that also include the point `(row, col)`. */
  def includePoint(
      rowMin: Int, colMin: Int, rowMax: Int, colMax: Int,
      row: Int, col: Int): (Int, Int, Int, Int) =
    (Math.min(rowMin, row), Math.min(colMin, col),
     Math.max(rowMax, row), Math.max(colMax, col))

  /** Expand all sides by `amount`, clamped to `[1, maxRow]` / `[1, maxCol]`. */
  def expandGlobally(
      rowMin: Int, colMin: Int, rowMax: Int, colMax: Int,
      amount: Int, maxRow: Int, maxCol: Int): (Int, Int, Int, Int) =
    (Math.max(rowMin - amount, 1), Math.max(colMin - amount, 1),
     Math.min(rowMax + amount, maxRow), Math.min(colMax + amount, maxCol))

  /** If a side is within `threshold` of the board edge, snap that side to the edge.
    * Uses independent min/max updates so corner cases do not overwrite each other.
    */
  def snapBordersToEdge(
      rowMin: Int, colMin: Int, rowMax: Int, colMax: Int,
      threshold: Int, maxRow: Int, maxCol: Int): (Int, Int, Int, Int) = {
    val newRowMin = if (rowMin <= threshold + 1) 1 else rowMin
    val newColMin = if (colMin <= threshold + 1) 1 else colMin
    val newRowMax = if (maxRow - rowMax <= threshold) maxRow else rowMax
    val newColMax = if (maxCol - colMax <= threshold) maxCol else colMax
    (newRowMin, newColMin, newRowMax, newColMax)
  }

  /** Intersection of two inclusive boxes, or None if they do not overlap. */
  def intersection(
      aRowMin: Int, aColMin: Int, aRowMax: Int, aColMax: Int,
      bRowMin: Int, bColMin: Int, bRowMax: Int, bColMax: Int): Option[(Int, Int, Int, Int)] = {
    val rowMin = Math.max(aRowMin, bRowMin)
    val rowMax = Math.min(aRowMax, bRowMax)
    val colMin = Math.max(aColMin, bColMin)
    val colMax = Math.min(aColMax, bColMax)
    if (rowMin > rowMax || colMin > colMax) None
    else Some((rowMin, colMin, rowMax, colMax))
  }
}
