package org.dupontmanual.image

import scalafx.Includes._
import scalafx.scene.shape.Shape
import scalafx.scene.Node
import scalafx.scene.transform.Translate

private[image] class Figure(shape: Shape, val paint: Option[Paint], val pen: Option[Pen]) extends Image {
  val fxShape = {
    shape.fill = paint.getOrElse(Color.Transparent).fxPaint
    pen.getOrElse(Pen.Transparent).applyToShape(shape)
    val bds = shape.boundsInParent.value
    shape
  }
  def bounds: Shape = fxShape
  
  val img: Node = fxShape
}

private[image] object Figure {
  def offsets(shape: Shape, paint: Option[Paint], pen: Option[Pen]): (Double, Double) = {
    val bounds = {
      shape.fill = paint.getOrElse(Color.Transparent).fxPaint
      pen.getOrElse(Pen.Transparent).applyToShape(shape)
      shape.boundsInParent.value
    }
    (bounds.minX, bounds.minY)
  }
}

private[image] abstract class FigureFilled(shape: Shape, paint: Paint) extends Figure(shape, Some(paint), None)

private[image] abstract class FigureOutlined(shape: Shape, pen: Pen) extends Figure(shape, None, Some(pen))
