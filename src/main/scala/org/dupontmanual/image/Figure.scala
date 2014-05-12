package org.dupontmanual.image

import java.awt.{Graphics2D, RenderingHints}
import java.awt.Shape
import java.awt.geom.Rectangle2D

private[image] abstract class Figure(val paint: Option[Paint], val pen: Option[Pen]) extends Image {
  val awtShape: Shape
  def bounds: Shape = awtShape
  
  def render(g2: Graphics2D) = {
	paint.foreach { p =>
	  g2.setPaint(p.awtPaint)
	  g2.fill(awtShape)
	}
	pen.foreach { p =>
	  g2.setPaint(p.paint.awtPaint)
	  g2.setStroke(p.asStroke)
	  g2.draw(awtShape)
	}
  }
  
  override def displayBounds: Rectangle2D = {
    pen match {
      case Some(p) => 
        val myPen = if (p.width == 0.0) p.copy(width=1.0) else p
        myPen.asStroke.createStrokedShape(awtShape).getBounds2D()
      case None => awtShape.getBounds2D()
    }    
  }
  
  override def penWidth: Double = pen.map(_.width).getOrElse(0.0)

}
