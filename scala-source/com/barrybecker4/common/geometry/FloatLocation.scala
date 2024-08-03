package com.barrybecker4.common.geometry


/**
  * Represents an immutable location location of something in Float coordinates.
  * @param x the x-axis coordinate.
  * @param y the y-axis coordinate.
  */
case class FloatLocation(x: Float, y: Float) {

  def distance(location: FloatLocation): Double = {
    val deltaX = location.x - x
    val deltaY = location.y - y
    Math.sqrt(deltaX * deltaX + deltaY * deltaY)
  }

  def midPoint(location: FloatLocation): FloatLocation = {
    FloatLocation((location.x + x) / 2, (location.y + y) / 2)
  }
}

object FloatLocation {
  def apply(x: Int, y: Int): FloatLocation = new FloatLocation(x.toFloat, y.toFloat)

  def centroid(locations: Seq[FloatLocation]): FloatLocation = {
    val x = locations.map(_.x).sum / locations.size
    val y = locations.map(_.y).sum / locations.size
    FloatLocation(x, y)
  }
}
