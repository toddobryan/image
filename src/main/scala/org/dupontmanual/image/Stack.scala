package org.dupontmanual.image

import math.{abs, min, max}
import scalafx.geometry.Rectangle2D
import scalafx.geometry.Bounds
import scalafx.geometry.BoundingBox

/** the parent class for horizontal alignments */
sealed abstract class XAlign private[image] ()
/** an object with the three types of horizontal alignments */
object XAlign {
  /** represents alignment to the left */
  object Left extends XAlign
  /** represents alignment on the center */
  object Center extends XAlign
  /** represents alignment to the right */
  object Right extends XAlign
}

/** the parent class for vertical alignments */
sealed abstract class YAlign
/** an object with the three types of vertical alignments */
object YAlign {
  /** represents alignment on the top */
  object Top extends YAlign
  /** represents alignment on the center */
  object Center extends YAlign
  /** represents alignment on the bottom */
  object Bottom extends YAlign
}

/** represent two images, one in front of the other */
private[image] class Stack(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) extends Image {
  val backBounds = Stack.translateRect(back.bounds, 
		  							   Stack.dx(back.displayBounds, xAlign, 0), 
		  							   Stack.dy(back.displayBounds, yAlign, 0))
  val frontBounds = Stack.translateRect(front.displayBounds,
             							Stack.dx(front.displayBounds, xAlign, dx),
             							Stack.dy(front.displayBounds, yAlign, dy))
  val newX = min(backBounds.getX, frontBounds.getX)
  val newY = min(backBounds.getY, frontBounds.getY)
  val newWidth = max(backBounds.getMaxX, frontBounds.getMaxX) - newX
  val newHeight = max(backBounds.getMaxY, frontBounds.getMaxY) - newY
  val backTopLeft = Point(backBounds.getX - newX, backBounds.getY - newY)
  val frontTopLeft = Point(frontBounds.getX - newX, frontBounds.getY - newY)
  
  val img: Node = new Group(back)
  
  def render(g2: Graphics2D) {
    val backOutRect: Rectangle2D = back.displayBounds
    val backInRect: Rectangle2D = back.bounds.getBounds2D
    val frontOutRect: Rectangle2D = front.displayBounds
    val frontInRect: Rectangle2D = front.bounds.getBounds2D
    val backDx = (backTopLeft.x + (backOutRect.getWidth - backInRect.getWidth) / 2)
    val backDy = (backTopLeft.y + (backOutRect.getHeight - backInRect.getHeight) / 2)
    val frontDx = (frontTopLeft.x + (frontOutRect.getWidth - frontInRect.getWidth) / 2)
    val frontDy = (frontTopLeft.y + (frontOutRect.getHeight - frontInRect.getHeight) / 2)
    g2.translate(backDx, backDy)
    back.render(g2)
    g2.translate(frontDx - backDx, frontDy - backDy)
    front.render(g2)
    g2.translate(-frontDx, -frontDy)
  }
}

private[image] object Stack {
  def apply(front: Image, back: Image) = new Stack(front, back, XAlign.Center, YAlign.Center, 0.0, 0.0)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign) = 
      new Stack(front, back, xAlign, yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, dx: Double, dy: Double) =
      new Stack(front, back, XAlign.Center, YAlign.Center, dx, dy)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) =
      new Stack(front, back, xAlign, yAlign, dx, dy)
  
  def translateRect(rect: Bounds, dx: Double, dy: Double): Bounds = {
    new BoundingBox(rect.minX + dx, rect.minY + dy, rect.width, rect.height)
  }
  
  def dx(rect: Rectangle2D, xAlign: XAlign, xOffset: Double): Double = xOffset + (xAlign match {
    case XAlign.Left => 0
    case XAlign.Center => -0.5 * (rect.minX + rect.maxX)
    case XAlign.Right => -rect.maxX
  })
  
  def dy(rect: Rectangle2D, yAlign: YAlign, yOffset: Double): Double = yOffset + (yAlign match {
    case YAlign.Top => 0
    case YAlign.Center => -0.5 * (rect.minY + rect.maxY)
    case YAlign.Bottom => -rect.maxY
  })
}