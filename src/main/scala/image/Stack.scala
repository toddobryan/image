package image

import java.awt.Graphics2D

import math.{min, max}

sealed abstract class XAlign
object XAlign {
  object left extends XAlign
  object center extends XAlign
  object right extends XAlign
}

sealed abstract class YAlign
object YAlign {
  object top extends YAlign
  object center extends YAlign
  object bottom extends YAlign
}

class Stack(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) extends Image {
  lazy val calcBounds: Bounds = {
    Bounds(Point(min(front.bounds.topLeft.x + dy, back.bounds.topLeft.x),
        	     min(front.bounds.topLeft.y + dy, back.bounds.topLeft.y)),
           Point(max(front.bounds.bottomRight.x + dx, back.bounds.bottomRight.x),
                 max(front.bounds.bottomRight.y + dy, back.bounds.bottomRight.y)))
  }
  
  def bounds = calcBounds
  
  def render(g2: Graphics2D) {
    val (dxBack, dyBack) = bounds.calcOffset(back, xAlign, yAlign, 0, 0)
    g2.translate(dxBack, dyBack)
    back.render(g2)
    val (dxFront, dyFront) = bounds.calcOffset(front, xAlign, yAlign, dx, dy)
    g2.translate(dxFront - dxBack, dyFront - dyBack)
    front.render(g2)
    g2.translate(-dxFront, -dyFront)
  }
}

object Stack {
  def apply(front: Image, back: Image) = new Stack(front, back, XAlign.center, YAlign.center, 0.0, 0.0)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign) = 
      new Stack(front, back, xAlign, yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, dx: Double, dy: Double) =
      new Stack(front, back, XAlign.center, YAlign.center, dx, dy)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) =
      new Stack(front, back, xAlign, yAlign, dx, dy)
}