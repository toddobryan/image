package org.dupontmanual.image

import math.{ abs, min, max }
import scalafx.geometry.Rectangle2D
import scalafx.geometry.Bounds
import scalafx.geometry.BoundingBox
import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import scalafx.scene.{ Group, Node }
import scalafx.scene.shape.{ Rectangle => SfxRectangle, Shape }
import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.application.Platform
import scalafx.concurrent.Task

/** represent two images, one in front of the other */
/* private[image] */ class Stack(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) extends Image {
  def this(front: Image, back: Image, align: Align, dx: Double, dy: Double) = {
    this(front, back, align.xAlign, align.yAlign, dx, dy)
  }

  val backBounds = Stack.translateRect(back.displayBounds, 
		  							   Stack.dx(back.displayBounds, xAlign, 0), 
		  							   Stack.dy(back.displayBounds, yAlign, 0))
  val frontBounds = Stack.translateRect(front.displayBounds,
             							Stack.dx(front.displayBounds, xAlign, dx),
             							Stack.dy(front.displayBounds, yAlign, dy))
  val newLeft = min(backBounds.minX, frontBounds.minX)
  val newRight = max(backBounds.minX + backBounds.width, frontBounds.minX + frontBounds.width)
  val newTop = min(backBounds.minY, frontBounds.minY)
  val newBottom = max(backBounds.maxY, frontBounds.maxY)
  val newBounds = new BoundingBox(newLeft, newTop, newRight - newLeft, newBottom - newTop)
  val backDx = backBounds.minX - newLeft
  val backDy = backBounds.minY - newTop
  val frontDx = frontBounds.minX - newLeft
  val frontDy = frontBounds.minY - newTop
  val backTransform = SfxTransform.translate(backDx, backDy)
  val frontTransform = SfxTransform.translate(frontDx, frontDy)
  
  def bounds(): Shape = SfxRectangle(newBounds.minX, newBounds.minY, newBounds.width, newBounds.height)

  def buildImage(): Node = {
    def newNode() = {
      val backView = new Group(back.buildImage()) { transforms = List(backTransform) }
      val frontView = new Group(front.buildImage()) { transforms = List(frontTransform) }
      new Pane {
        children = new Group(backView, frontView)
        prefWidth = newBounds.width
        prefHeight = newBounds.height
      }
    }
    if (Platform.isFxApplicationThread) {
      newNode()
    } else {
      val theNode = Task[Node] { newNode() }
      Platform.runLater(theNode)
      theNode.get()
    }    
  }
}

private[image] object Stack {
  def apply(front: Image, back: Image) = new Stack(front, back, Align.Center, 0.0, 0.0)
  def apply(front: Image, back: Image, alignment: Align) = 
    new Stack(front, back, alignment.xAlign, alignment.yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign) =
    new Stack(front, back, xAlign, yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, dx: Double, dy: Double) =
    new Stack(front, back, XAlign.Center, YAlign.Center, dx, dy)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) =
    new Stack(front, back, xAlign, yAlign, dx, dy)
  def apply(front: Image, back: Image, alignment: Align, dx: Double, dy: Double) =
    new Stack(front, back, alignment.xAlign, alignment.yAlign, dx, dy)

  def translateRect(rect: BoundingBox, dx: Double, dy: Double): BoundingBox = {
    new BoundingBox(rect.minX + dx, rect.minY + dy, rect.width, rect.height)
  }

  def centerOf(bounds: BoundingBox): Point = {
    Point(0.5 * (bounds.minX + bounds.maxX), 0.5 * (bounds.minY + bounds.maxY))
  }
  
  def dx(bds: BoundingBox, xAlign: XAlign, xOffset: Double): Double = xOffset + (xAlign match {
    case XAlign.Left => 0
    case XAlign.Center => -0.5 * bds.width
    case XAlign.Right => -bds.maxX
  })
  
  def dy(bds: BoundingBox, yAlign: YAlign, yOffset: Double): Double = yOffset + (yAlign match {
    case YAlign.Top => 0
    case YAlign.Center => -0.5 * bds.height
    case YAlign.Bottom => -bds.maxY
  })
}