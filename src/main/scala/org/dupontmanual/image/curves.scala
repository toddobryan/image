package org.dupontmanual.image

import scalafx.scene.shape.{ Arc => SfxArc, ArcType, Circle => SfxCircle, Ellipse => SfxEllipse, Shape }

/** a convenience object with a shape method for java.awt.geom.Ellipse2D */
private[image] class Ellipse(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double) extends Figure(SfxEllipse(width, height), paint, pen)

object Ellipse {
  def apply(paint: Paint, pen: Pen, width: Double, height: Double): Image = new Ellipse(Some(paint), Some(pen), width, height)
  def apply(paint: Paint, width: Double, height: Double): Image = new Ellipse(Some(paint), None, width, height)
  def apply(pen: Pen, width: Double, height: Double): Image = new Ellipse(None, Some(pen), width, height)
  def outlined(penColor: Paint, width: Double, height: Double): Image = new Ellipse(None, Some(Pen(penColor)), width, height)
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
  def apply(pen: Pen, width: Double, height: Double): Image = {
    Ellipse(pen, width, height)
  }
  
  /** 
   * returns an outlined ellipse with the given characteristics.
   * It is drawn with `Pen(paint)`.
   */
  def apply(paint: Paint, width: Double, height: Double): Image = {
    Ellipse.outlined(paint, width, height)
  }
}
 

/** the parent class for filled circles */
private[image] class Circle(paint: Option[Paint], pen: Option[Pen], radius: Double) extends Figure(SfxCircle(radius), paint, pen)

object Circle {
  def apply(paint: Paint, pen: Pen, radius: Double): Image = new Circle(Some(paint), Some(pen), radius)
  def apply(paint: Paint, radius: Double): Image = new Circle(Some(paint), None, radius)
  def apply(pen: Pen, radius: Double): Image = new Circle(None, Some(pen), radius)
  def outlined(penColor: Paint, radius: Double): Image = new Circle(None, Some(Pen(penColor)), radius)
}

/** a factory for filled circles */
object CircleFilled {
  /** returns a filled circle with the given characteristics */
  def apply(paint: Paint, radius: Double): Image = Circle(paint, radius)
}

/** a factory for outlined circles */
object CircleOutlined {
  /** returns an outlined circle with the given characteristics */
  def apply(pen: Pen, radius: Double): Image = Circle(pen, radius)
  
  /** 
   * returns an outlined circle with the given characteristics.
   * The circle is drawn with `Pen(paint)`. 
   */
  def apply(paint: Paint, radius: Double): Image = Circle.outlined(paint, radius)
}


/** an object with convenience methods for arcs */
private[image] object Arc {  
  def shape(width: Double, height: Double, start: Angle, extent: Angle, arcType: ArcType): Shape = {
    new SfxArc {
      centerX = 0
      centerY = 0
      radiusX = width
      radiusY = height
      startAngle = start.toDegrees.magnitude
      length = extent.toDegrees.magnitude
      `type` = arcType
    }
  }
}

/** the parent class for circular arcs. */
private[image] class CircularArc(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle)
    extends Figure(Arc.shape(2 * radius, 2 * radius, start, extent, ArcType.OPEN), paint, pen)

/** a factory for creating circular arcs. */
object CircularArc {
  def apply(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle) = {
      new CircularArc(paint, pen, radius, start, extent)
  }
  def apply(paint: Paint, pen: Pen, radius: Double, start: Angle, extent: Angle) = {
      new CircularArc(Some(paint), Some(pen), radius, start, extent)
  }
  
  /** returns a circular arc with the given characteristics. */
  def apply(pen: Pen, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularArc(None, Some(pen), radius, start, extent)
  }
  
  /** 
   * returns a circular arc with the given characteristics.
   * The arc is drawn with `Pen(paint)`.
   */
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularArc(None, Some(Pen(paint)), radius, start, extent)
  }
  
  def filled(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularArc(Some(paint), None, radius, start, extent)
  }
}

/** the parent class for circular segments */
private[image] class CircularSegment(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle)
    extends Figure(Arc.shape(2 * radius, 2 * radius, start, extent, ArcType.CHORD), paint, pen)

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

/** the parent class for circular sectors */
private[image] class CircularSector(paint: Option[Paint], pen: Option[Pen], radius: Double, start: Angle, extent: Angle)
    extends Figure(Arc.shape(2 * radius, 2 * radius, start, extent, ArcType.ROUND), paint, pen) 

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
    extends Figure(Arc.shape(width, height, start, extent, ArcType.OPEN), paint, pen)

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
    extends Figure(Arc.shape(width, height, start, extent, ArcType.CHORD), paint, pen)

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
    extends Figure(Arc.shape(width, height, start, extent, ArcType.ROUND), paint, pen)

/** 
 * a factory for elliptical sectors. These represent the area enclosed by a
 * the radii at angles `start` and `start + extent` and the arc of the 
 * ellipse connecting those radii 
 */
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
