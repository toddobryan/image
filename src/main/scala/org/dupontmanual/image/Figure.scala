package org.dupontmanual.image

import scalafx.scene.shape.Shape
import scalafx.scene.Node

private[image] class Figure(shape: Shape, val paint: Option[Paint], val pen: Option[Pen]) extends Image {
  val fxShape = {
    shape.fill = paint.getOrElse(Color.Transparent).fxPaint
    pen.getOrElse(Pen.Transparent).applyToShape(shape)
    shape
  }
  def bounds: Shape = fxShape
  
  val img: Node = fxShape
}

private[image] abstract class FigureFilled(shape: Shape, paint: Paint) extends Figure(shape, Some(paint), None)

private[image] abstract class FigureOutlined(shape: Shape, pen: Pen) extends Figure(shape, None, Some(pen))
