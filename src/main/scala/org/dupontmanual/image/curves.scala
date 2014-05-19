package org.dupontmanual.image

/** the parent class of filled ellipses */
private[image] class Ellipse(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double) extends Figure(paint, pen) {
  val awtShape = new java.awt.geom.Ellipse2D.Double(0, 0, width, height)
}

/** a factory for filled ellipses */
object Ellipse {
  private[Ellipse] def apply(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double): Image = {
    new Ellipse(paint, pen, width, height)
  }
  /** returns an ellipse with the given characteristics */
  def apply(paint: Paint, pen: Pen, width: Double, height: Double): Image = Ellipse(Some(paint), Some(pen), width, height)
  def apply(paint: Paint, width: Double, height: Double): Image = Ellipse(Some(paint), None, width, height)
  def apply(pen: Pen, width: Double, height: Double): Image = Ellipse(None, Some(pen), width, height)
  def outlined(penColor: Paint, width: Double, height: Double): Image = Ellipse(None, Some(Pen(penColor)), width, height)
}

/** a factory for filled ellipses */
object EllipseFilled {
  /** returns a filled ellipse with the given characteristics */
  def apply(paint: Paint, width: Double, height: Double): Image = {
    Ellipse(paint, width, height)
  }
}

/** a factory for outlined ellipses */
object EllipseOutlined {
  /** returns an outlined ellipse with the given characteristics. */
  @deprecated("Use Ellipse(pen, width, height) instead.", "0.9")
  def apply(pen: Pen, width: Double, height: Double): Image = {
    Ellipse(pen, width, height)
  }
  
  /** 
   * returns an outlined ellipse with the given characteristics.
   * It is drawn with `Pen(paint)`.
   */
  @deprecated("Use Ellipse.outlined(color, width, height) instead.", "0.9")  
  def apply(paint: Paint, width: Double, height: Double): Image = {
    Ellipse.outlined(paint, width, height)
  }
}

/** the parent class for circles */
private[image] class Circle(paint: Option[Paint], pen: Option[Pen], radius: Double) 
    extends Ellipse(paint, pen, 2 * radius, 2 * radius)

object Circle {
  private[Circle] def apply(paint: Option[Paint], pen: Option[Pen], radius: Double): Image = new Circle(paint, pen, radius)
  def apply(paint: Paint, pen: Pen, radius: Double): Image = Circle(Some(paint), Some(pen), radius)
  def apply(paint: Paint, radius: Double): Image = Circle(Some(paint), None, radius)
  def apply(pen: Pen, radius: Double): Image = Circle(None, Some(pen), radius)
  def outlined(penColor: Paint, radius: Double): Image = Circle(None, Some(Pen(penColor)), radius)
}


/** a factory for filled circles */
object CircleFilled {
  /** returns a filled circle with the given characteristics */
  @deprecated("Use Circle(paint, radius) instead.", "0.9")
  def apply(paint: Paint, radius: Double): Image = Circle(paint, radius)
}

/** a factory for outlined circles */
object CircleOutlined {
  /** returns an outlined circle with the given characteristics */
  @deprecated("Use Circle(pen, radius) instead.", "0.9")
  def apply(pen: Pen, radius: Double): Image = Circle(pen, radius)
  
  /** 
   * returns an outlined circle with the given characteristics.
   * The circle is drawn with `Pen(paint)`. 
   */
  @deprecated("Use Circle.outlined(paint, radius) instead.", "0.9")
  def apply(paint: Paint, radius: Double): Image = Circle(Pen(paint), radius)
}

/** an object with convenience methods for arcs */
private[image] object Arc {
  import java.awt.geom.Arc2D
  
  val OPEN = Arc2D.OPEN
  val CHORD = Arc2D.CHORD
  val PIE = Arc2D.PIE
  
  def shape(width: Double, height: Double, start: Angle, extent: Angle, kind: Int): java.awt.Shape = {
    val startDeg = start.toDegrees.magnitude
    val extentDeg = extent.toDegrees.magnitude
    val bounds = new Arc2D.Double(0, 0, width, height, startDeg, extentDeg, kind).getBounds2D()
    new Arc2D.Double(-bounds.getMinX, -bounds.getMinY, width, height, startDeg, extentDeg, kind)
  }
}

/** the parent class for circular arcs. */
private[image] class CircularArc(pen: Pen, radius: Double, start: Angle, extent: Angle)
    extends Figure(None, Some(pen)) {
 val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.OPEN)
}

/** a factory for creating circular arcs. */
object CircularArc {
  /** returns a circular arc with the given characteristics. */
  def apply(pen: Pen, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularArc(pen, radius, start, extent)
  }
  
  /** 
   * returns a circular arc with the given characteristics.
   * The arc is drawn with `Pen(paint)`.
   */
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularArc(Pen(paint), radius, start, extent)
  }
}

/** the parent class for circular segments */
private[image] class CircularSegment(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle)
    extends Figure(paint, pen) {
  val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.CHORD)
}

/** 
 * a factory for circular segments. These are the portion of the circle 
 * enclosed by the chord connecting the point on the circle at angle `start`
 * to the point at `start + extent`, and the arc of the circle connecting
 * the same two points
 */
object CircularSegment {
  def apply(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle): Image = {
    new CircularSegment(paint, pen, radius, start, extent)
  }
  def apply(paint: Paint, pen: Pen, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSegment(Some(paint), Some(pen), radius, start, extent)
  }
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSegment(Some(paint), None, radius, start, extent)
  }
  def apply(pen: Pen, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSegment(None, Some(pen), radius, start, extent)
  }
  def outlined(penColor: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSegment(None, Some(Pen(penColor)), radius, start, extent)
  }
}

/** 
 * a factory for circular segments. These are the portion of the circle 
 * enclosed by the chord connecting the point on the circle at angle `start`
 * to the point at `start + extent`, and the arc of the circle connecting
 * the same two points
 */
object CircularSegmentFilled {
  /** returns a circular segment with the given characteristics */
  @deprecated("Use CircularSegment(paint, radius, start, extent) instead.", "0.9")
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = CircularSegment(paint, radius, start, extent)
}

object CircularSegmentOutlined {
  @deprecated("Use CircularSegment.outlined(paint, radius, start, extent) instead.", "0.9")
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = CircularSegment(Pen(paint), radius, start, extent)
  @deprecated("User CircularSegment(pen, radius, start, extent) instead.", "0.9")
  def apply(pen: Pen, radius: Double, start: Angle, extent: Angle): Image = CircularSegment(pen, radius, start, extent)
}

/** the parent class for circular sectors */
private[image] class CircularSector(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle)
    extends Figure(paint, pen) {
  val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.PIE)
}

/** 
 * a factory for circular wedges. These are the portion of the circle
 * enclosed by a radius at angle `start`, a radius at angle `start + extent`
 * and the arc of the circle connecting them.
 */
object CircularSector {
  def apply(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle): Image = {
    new CircularSector(paint, pen, radius, start, extent)
  }
  def apply(paint: Paint, pen: Pen, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSector(Some(paint), Some(pen), radius, start, extent)
  }
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSector(Some(paint), None, radius, start, extent)
  }
  def apply(pen: Pen, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSector(None, Some(pen), radius, start, extent)
  }
  def outlined(penColor: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    CircularSector(None, Some(Pen(penColor)), radius, start, extent)
  }
}


/** the parent class for elliptical arcs */
private[image] class EllipticalArc(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double, start: Angle, extent: Angle)
    extends Figure(paint, pen) {
 val awtShape = Arc.shape(width, height, start, extent, Arc.OPEN)
}

/** a factory for elliptical arcs */
object EllipticalArc {
  /** returns an elliptical arc with the given characteristics */
  def apply(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalArc(paint, pen, width, height, start, extent)
  }
  def apply(paint: Paint, pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalArc(Some(paint), Some(pen), width, height, start, extent)
  }
  def apply(penColor: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalArc(None, Some(Pen(penColor)), width, height, start, extent)
  }
  def apply(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalArc(None, Some(pen), width, height, start, extent)
  }
  def filled(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalArc(Some(paint), None, width, height, start, extent)
  }
}


/** the parent class for elliptical segments */
private[image] class EllipticalSegment(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double, start: Angle, extent: Angle)
    extends Figure(paint, pen) {
  val awtShape = Arc.shape(width, height, start, extent, Arc.CHORD)
}

/** 
 * a factory for elliptical segments. These represent the area enclosed by a chord
 * connecting the points on the ellipse at angles `start` and `start + extent` and 
 * the ellipse's arc connecting those two points
 */
object EllipticalSegment {
  /** returns an elliptical segment with the given characteristics */
  def apply(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalSegment(paint, pen, width, height, start, extent)
  }
  def apply(paint: Paint, pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSegment(Some(paint), Some(pen), width, height, start, extent)
  }
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSegment(Some(paint), None, width, height, start, extent)
  }
  def apply(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSegment(None, Some(pen), width, height, start, extent)
  }
  def outlined(penColor: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSegment(None, Some(Pen(penColor)), width, height, start, extent)
  }
}


/** the parent class for elliptical sectors */
private[image] class EllipticalSector(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double, start: Angle, extent: Angle)
    extends Figure(paint, pen) {
  val awtShape = Arc.shape(width, height, start, extent, Arc.PIE)
}

object EllipticalSector {
  /** returns an elliptical sector with the given characteristics */
  def apply(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalSector(paint, pen, width, height, start, extent)
  }
  def apply(paint: Paint, pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSector(Some(paint), Some(pen), width, height, start, extent)
  }
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSector(Some(paint), None, width, height, start, extent)
  }
  def apply(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSector(None, Some(pen), width, height, start, extent)
  }
  def outlined(penColor: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    EllipticalSector(None, Some(Pen(penColor)), width, height, start, extent)
  }
}