package org.dupontmanual.image

import math.{ abs, min, max }
import scalafx.geometry.Rectangle2D
import scalafx.geometry.Bounds
import scalafx.geometry.BoundingBox
import scalafx.scene.image.ImageView
import scalafx.scene.{ Group, Node }
import scalafx.scene.shape.{ Rectangle => SfxRectangle, Shape }
import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.application.Platform

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

sealed abstract class Alignment(val xAlign: XAlign, val yAlign: YAlign)
object Alignment {
  object TopLeft extends Alignment(XAlign.Left, YAlign.Top)
  object Top extends Alignment(XAlign.Center, YAlign.Top)
  object TopRight extends Alignment(XAlign.Right, YAlign.Top)
  object Left extends Alignment(XAlign.Left, YAlign.Center)
  object Center extends Alignment(XAlign.Center, YAlign.Center)
  object Right extends Alignment(XAlign.Right, YAlign.Center)
  object BottomLeft extends Alignment(XAlign.Left, YAlign.Bottom)
  object Bottom extends Alignment(XAlign.Center, YAlign.Bottom)
  object BottomRight extends Alignment(XAlign.Right, YAlign.Bottom)
}

/** represent two images, one in front of the other */
/* private[image] */ class Stack(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) extends Image {
  def this(front: Image, back: Image, align: Alignment, dx: Double, dy: Double) = {
    this(front, back, align.xAlign, align.yAlign, dx, dy)
  }
  
  val backBounds = back.displayBounds
  val frontBounds = front.displayBounds
  val frontBoundsWithOffset = {
    val bds = front.displayBounds
    new BoundingBox(min(bds.minX, bds.minX + dx), min(bds.minY, bds.minY + dy), bds.width + abs(dx), bds.height + abs(dy))
  }
  val newLeft = min(backBounds.minX, frontBoundsWithOffset.minX)
  val newRight = max(backBounds.minX + backBounds.width, frontBoundsWithOffset.minX + frontBoundsWithOffset.width)
  val newTop = min(backBounds.minY, frontBoundsWithOffset.minY)
  val newBottom = max(backBounds.minY + backBounds.height, frontBoundsWithOffset.minY + frontBoundsWithOffset.height)
  val newBounds = new BoundingBox(newLeft, newTop, newRight - newLeft, newBottom - newTop)
  val backDx = Stack.dx(backBounds, newBounds, xAlign)
  val backDy = Stack.dy(backBounds, newBounds, yAlign)
  val frontDx = Stack.dx(frontBounds, newBounds, xAlign) + dx
  val frontDy = Stack.dy(frontBounds, newBounds, yAlign) + dy
  val backTransform = SfxTransform.translate(backDx, backDy)
  val frontTransform = SfxTransform.translate(frontDx, frontDy)

  val img: Node = {
    val backView = new Group(back.img) { transforms = List(backTransform) }
    val frontView = new Group(front.img) { transforms = List(frontTransform) }
    new Group(backView, frontView)
  }
      
  def bounds: Shape = SfxRectangle(newBounds.minX, newBounds.minY, newBounds.width, newBounds.height)
}

private[image] object Stack {
  def apply(front: Image, back: Image) = new Stack(front, back, Alignment.Center, 0.0, 0.0)
  def apply(front: Image, back: Image, alignment: Alignment) = 
    new Stack(front, back, alignment.xAlign, alignment.yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign) =
    new Stack(front, back, xAlign, yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, dx: Double, dy: Double) =
    new Stack(front, back, XAlign.Center, YAlign.Center, dx, dy)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) =
    new Stack(front, back, xAlign, yAlign, dx, dy)
  def apply(front: Image, back: Image, alignment: Alignment, dx: Double, dy: Double) =
    new Stack(front, back, alignment.xAlign, alignment.yAlign, dx, dy)

  def translateRect(rect: BoundingBox, dx: Double, dy: Double): BoundingBox = {
    new BoundingBox(rect.minX + dx, rect.minY + dy, rect.width, rect.height)
  }

  def centerOf(bounds: BoundingBox): Point = {
    Point(0.5 * (bounds.minX + bounds.maxX), 0.5 * (bounds.minY + bounds.maxY))
  }
  
  def dx(insetBounds: BoundingBox, compoundBounds: BoundingBox, xAlign: XAlign): Double = xAlign match {
    case XAlign.Left => 0
    case XAlign.Center => centerOf(compoundBounds).x - centerOf(insetBounds).x
    case XAlign.Right => compoundBounds.maxX - insetBounds.maxX
  }

  def dy(insetBounds: BoundingBox, compoundBounds: BoundingBox, yAlign: YAlign): Double = yAlign match {
    case YAlign.Top => 0
    case YAlign.Center => centerOf(compoundBounds).y - centerOf(insetBounds).y
    case YAlign.Bottom => compoundBounds.maxY - insetBounds.maxY
  }
}