package image

import math.ceil

case class Bounds(topLeft: Point, bottomRight: Point) {
  def width: Int = ceil(bottomRight.x - topLeft.x).toInt
  def height: Int = ceil(bottomRight.y - topLeft.y).toInt
}