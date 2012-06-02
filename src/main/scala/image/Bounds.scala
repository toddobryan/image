package image

import math.ceil

case class Bounds(topLeft: Point, bottomRight: Point) {
  def width: Double = bottomRight.x - topLeft.x
  def height: Double = bottomRight.y - topLeft.y
  
  def calcOffset(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): (Double, Double) = {
    val xOffset = dx + (xAlign match {
      case XAlign.left => 0
      case XAlign.center => (this.width - img.bounds.width) / 2.0
      case XAlign.right => this.width - img.bounds.width
    })
    val yOffset = dy + (yAlign match {
      case YAlign.top => 0
      case YAlign.center => (this.height - img.bounds.height) / 2.0
      case YAlign.bottom => (this.height - img.bounds.height)
    })
    (xOffset, yOffset)
  }
}