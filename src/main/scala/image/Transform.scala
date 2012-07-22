package image

import java.awt.image.BufferedImage
import java.awt.geom._
import java.awt.image._
import java.awt.{Graphics2D, Paint, RenderingHints}

class Transform(image: Image, transform: AffineTransform) extends Image {
  def render(g2: Graphics2D) = {
    g2.transform(transform)
    image.render(g2)
  }
  private[this] def boundsTransformed(oldBounds: Bounds): Bounds = {
    val boundRect = bounds.asRect2D
    val newBoundRect = transform.createTransformedShape(boundRect).getBounds2D
    Bounds(Point(newBoundRect.getMinX(), newBoundRect.getMinY()),
           Point(newBoundRect.getMaxX(), newBoundRect.getMaxY()))
  }
  
  def bounds: Bounds = boundsTransformed(image.bounds)
  def displayBounds: Bounds = boundsTransformed(image.displayBounds)
}
