package net.toddobryan.image

import scalafx.Includes._
import scalafx.scene.shape.Shape
import scalafx.scene.Node
import scalafx.scene.transform.Translate

private[image] abstract class Figure(val paint: Option[Paint], val pen: Option[Pen]) extends Image {
  private[image] def fxShape(): Shape
  
  def bounds(): Shape = fxShape()
  
  def buildImage(): Node = {
    val shape = fxShape()
    shape.fill = paint.getOrElse(Color.Transparent).fxPaint
    pen.getOrElse(Pen.Transparent).applyToShape(shape)
    shape
  }
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

private[image] abstract class FigureFilled(paint: Paint) extends Figure(Some(paint), None)

private[image] abstract class FigureOutlined(pen: Pen) extends Figure(None, Some(pen))
