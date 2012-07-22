package image

import math.{abs, min, max}
import java.awt.geom.Rectangle2D

case class Bounds(topLeft: Point, bottomRight: Point) {
  def width: Double = bottomRight.x - topLeft.x
  def height: Double = bottomRight.y - topLeft.y
  
  def asRect2D: Rectangle2D = new Rectangle2D.Double(topLeft.x, topLeft.y, width, height)
  
  def translate(dx: Double, dy: Double): Bounds = {
    Bounds(topLeft.translate(dx, dy), bottomRight.translate(dx, dy))
  }
  
  def dx(xAlign: XAlign, xOffset: Double): Double = xOffset + (xAlign match {
    case XAlign.left => 0
    case XAlign.center => -0.5 * (this.topLeft.x + this.bottomRight.x)
    case XAlign.right => -this.bottomRight.x
  })
  
  def dy(yAlign: YAlign, yOffset: Double): Double = yOffset + (yAlign match {
    case YAlign.top => 0
    case YAlign.center => -0.5 * (this.topLeft.y + this.bottomRight.y)
    case YAlign.bottom => -this.bottomRight.y
  })
  
  /*def calcOffset(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): (Double, Double) = {
    val xOffset = dx + (xAlign match {
      case XAlign.left => 0
      case XAlign.center => (this.width - (img.bounds.width + abs(dx))) / 2.0
      case XAlign.right => this.width - (img.bounds.width + abs(dx))
    })
    val yOffset = dy + (yAlign match {
      case YAlign.top => 0
      case YAlign.center => (this.height - (img.bounds.height + abs(dy))) / 2.0
      case YAlign.bottom => (this.height - (img.bounds.height + abs(dy)))
    })
    (xOffset, yOffset)
  }*/
}

object Bounds {
  def calcBoundsAndOffsets(
      front: Image, back: Image,
      xAlign: XAlign, yAlign: YAlign, 
      dx: Double, dy: Double): (Bounds, Bounds, Point, Point) = {
    val backBounds = back.bounds.translate(back.bounds.dx(xAlign, 0), back.bounds.dy(yAlign, 0))
    val backDisplayBounds = back.displayBounds.translate(back.displayBounds.dx(xAlign, 0), back.displayBounds.dy(yAlign, 0))
    val frontBounds = front.bounds.translate(front.bounds.dx(xAlign, dx), front.bounds.dy(yAlign, dy))
    val frontDisplayBounds = front.displayBounds.translate(front.displayBounds.dx(xAlign, dx), front.displayBounds.dy(yAlign, dy))
    val overallBounds = Bounds(Point(min(backBounds.topLeft.x, frontBounds.topLeft.x),
                                     min(backBounds.topLeft.y, frontBounds.topLeft.y)),
                               Point(max(backBounds.bottomRight.x, frontBounds.bottomRight.x),
                                     max(backBounds.bottomRight.y, frontBounds.bottomRight.y)))
    val displayBounds = Bounds(Point(min(backDisplayBounds.topLeft.x, frontDisplayBounds.topLeft.x),
                                    min(backDisplayBounds.topLeft.y, frontDisplayBounds.topLeft.y)),
                              Point(max(backDisplayBounds.bottomRight.x, frontDisplayBounds.bottomRight.x),
                                    max(backDisplayBounds.bottomRight.y, frontDisplayBounds.bottomRight.y)))
    (Bounds(Point(0, 0), Point(overallBounds.width, overallBounds.height)),
     displayBounds.translate(-overallBounds.topLeft.x, -overallBounds.topLeft.y),
     Point(backBounds.topLeft.x - overallBounds.topLeft.x, backBounds.topLeft.y - overallBounds.topLeft.y),
     Point(frontBounds.topLeft.x - overallBounds.topLeft.x, frontBounds.topLeft.y - overallBounds.topLeft.y))
  }
}
