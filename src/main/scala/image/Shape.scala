package image
import java.awt.{Graphics2D, Paint, RenderingHints}

abstract class Shape extends Image {
  val awtShape: java.awt.Shape
  def bounds: Bounds = {
    val rect = awtShape.getBounds2D()
    Bounds(Point(rect.getMinX, rect.getMinY), Point(rect.getMaxX, rect.getMaxY))
  }
}

abstract class ShapeFilled(val paint: Paint) extends Shape {
  def render(g2: Graphics2D) = {
    g2.setPaint(paint)
    g2.fill(awtShape)
  }
  def displayBounds: Bounds = bounds
}

abstract class ShapeOutlined(val pen: Pen) extends Shape {
  def render(g2: Graphics2D) = {
    g2.setPaint(pen.paint)
    g2.setStroke(pen.asStroke)
    g2.draw(awtShape)
  }
  def displayBounds: Bounds = {
    val rect = pen.asStroke.createStrokedShape(awtShape).getBounds2D()
    Bounds(Point(rect.getMinX, rect.getMinY), Point(rect.getMaxX, rect.getMaxY))
  }
}


