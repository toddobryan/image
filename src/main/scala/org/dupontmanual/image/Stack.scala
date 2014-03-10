package org.dupontmanual.image

import math.{abs, min, max}
import scalafx.geometry.Rectangle2D
import scalafx.geometry.Bounds
import scalafx.geometry.BoundingBox
import scalafx.scene.image.ImageView
import scalafx.scene.{ Group, Node }
import scalafx.scene.transform.Transform

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
  val backBounds = Stack.translateRect(back.displayBounds, 
		  							   Stack.dx(back.displayBounds, xAlign, 0), 
		  							   Stack.dy(back.displayBounds, yAlign, 0))
  val frontBounds = Stack.translateRect(front.displayBounds,
             							Stack.dx(front.displayBounds, xAlign, dx),
             							Stack.dy(front.displayBounds, yAlign, dy))
  val newX = min(backBounds.minX, frontBounds.minX)
  val newY = min(backBounds.minY, frontBounds.minY)
  val newWidth = max(backBounds.maxX, frontBounds.maxX) - newX
  val newHeight = max(backBounds.maxY, frontBounds.maxY) - newY
  val backTopLeft = Point(backBounds.minX - newX, backBounds.minY - newY)
  val frontTopLeft = Point(frontBounds.minX - newX, frontBounds.minY - newY)
  
  val backDx = backTopLeft.x + back.displayBounds.width / 2
  val backDy = backTopLeft.y + back.displayBounds.height / 2
  val backTransform = Transform.translate(backDx, backDy)
  val frontDx = frontTopLeft.x + front.displayBounds.width / 2
  val frontDy = frontTopLeft.y + front.displayBounds.height / 2
  val frontTransform = Transform.translate(frontDx, frontDy)
  
  val backView = new Node(back.img) { transforms = List(backTransform) }
  val frontView = new Node(front.img) { transforms = List(frontTransform) }
  val img: Node = new Group(backView, frontView)
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
  
  def dx(rect: Bounds, xAlign: XAlign, xOffset: Double): Double = xOffset + (xAlign match {
    case XAlign.Left => 0
    case XAlign.Center => -0.5 * (rect.minX + rect.maxX)
    case XAlign.Right => -rect.maxX
  })
  
  def dy(rect: Bounds, yAlign: YAlign, yOffset: Double): Double = yOffset + (yAlign match {
    case YAlign.Top => 0
    case YAlign.Center => -0.5 * (rect.minY + rect.maxY)
    case YAlign.Bottom => -rect.maxY
  })
}