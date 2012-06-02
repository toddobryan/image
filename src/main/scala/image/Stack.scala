package image

import java.awt.Graphics2D

import math.{abs, min, max}

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
  val (calcBounds, backTopLeft, frontTopLeft) = 
    Bounds.calcBoundsAndOffsets(front, back, xAlign, yAlign, dx, dy)
  
  def bounds = calcBounds
  
  def render(g2: Graphics2D) {
    g2.translate(backTopLeft.x, backTopLeft.y)
    back.render(g2)
    g2.translate(frontTopLeft.x - backTopLeft.x, frontTopLeft.y - backTopLeft.y)
    front.render(g2)
    g2.translate(-frontTopLeft.x, -frontTopLeft.y)
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