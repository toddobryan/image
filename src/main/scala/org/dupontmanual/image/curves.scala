package org.dupontmanual.image

/** an convenience object with a shape method for java.awt.geom.Ellipse2D */
private[image] object Ellipse {
  def shape(width: Double, height: Double) = new java.awt.geom.Ellipse2D.Double(0, 0, width, height)
}

/** the parent class of filled ellipses */
private[image] class EllipseFilled(paint: Paint, width: Double, height: Double) extends FigureFilled(paint) {
  val awtShape = Ellipse.shape(width, height)
}

/** a factory for filled ellipses */
object EllipseFilled {
  /** returns a filled ellipse with the given characteristics */
  def apply(paint: Paint, width: Double, height: Double): Image = {
    new EllipseFilled(paint, width, height)
  }
}

/** the parent class of outlined ellipses. */
private[image] class EllipseOutlined(pen: Pen, width: Double, height: Double) extends FigureOutlined(pen) {
  val awtShape = Ellipse.shape(width, height)
}

/** a factory for outlined ellipses */
object EllipseOutlined {
  /** returns an outlined ellipse with the given characteristics. */
  def apply(pen: Pen, width: Double, height: Double): Image = {
    new EllipseOutlined(pen, width, height)
  }
  
  /** 
   * returns an outlined ellipse with the given characteristics.
   * It is drawn with `Pen(paint)`.
   */
  def apply(paint: Paint, width: Double, height: Double): Image = {
    new EllipseOutlined(Pen(paint), width, height)
  }
}

/** the parent class for filled circles */
private[image] class CircleFilled(paint: Paint, radius: Double) extends FigureFilled(paint) {
  val awtShape = Ellipse.shape(2 * radius, 2 * radius)
}

/** a factory for filled circles */
object CircleFilled {
  /** returns a filled circle with the given characteristics */
  def apply(paint: Paint, radius: Double): Image = new CircleFilled(paint, radius)
}

/** the parent class for outlined circles */
private[image] class CircleOutlined(pen: Pen, radius: Double) extends FigureOutlined(pen) {
  val awtShape = Ellipse.shape(2 * radius, 2 * radius)
}

/** a factory for outlined circles */
object CircleOutlined {
  /** returns an outlined circle with the given characteristics */
  def apply(pen: Pen, radius: Double): Image = new CircleOutlined(pen, radius)
  
  /** 
   * returns an outlined circle with the given characteristics.
   * The circle is drawn with `Pen(paint)`. 
   */
  def apply(paint: Paint, radius: Double): Image = new CircleOutlined(Pen(paint), radius)
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
    extends FigureOutlined(pen) {
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
private[image] class CircularSegment(paint: Paint, radius: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.CHORD)
}

/** 
 * a factory for circular segments. These are the portion of the circle 
 * enclosed by the chord connecting the point on the circle at angle `start`
 * to the point at `start + extent`, and the arc of the circle connecting
 * the same two points
 */
object CircularSegment {
  /** returns a circular segment with the given characteristics */
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularSegment(paint, radius, start, extent)
  }
}

/** the parent class for circular sectors */
private[image] class CircularSector(paint: Paint, radius: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.PIE)
}

/** 
 * a factory for circular wedges. These are the portion of the circle
 * enclosed by a radius at angle `start`, a radius at angle `start + extent`
 * and the arc of the circle connecting them.
 */
object CircularSector {
  /** returns a circular sector with the given characteristics */
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle): Image = {
    new CircularSector(paint, radius, start, extent)
  }
}

/** the parent class for elliptical arcs */
private[image] class EllipticalArc(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle)
    extends FigureOutlined(pen) {
 val awtShape = Arc.shape(width, height, start, extent, Arc.OPEN)
}

/** a factory for elliptical arcs */
object EllipticalArc {
  /** returns an elliptical arc with the given characteristics */
  def apply(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalArc(pen, width, height, start, extent)
  }
  
  /** 
   * returns an elliptical arc with the given characteristics. The arc is drawn
   * with `Pen(paint)`.
   */
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalArc(Pen(paint), width, height, start, extent)
  }
}

/** the parent class for elliptical segments */
private[image] class EllipticalSegment(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(width, height, start, extent, Arc.CHORD)
}

/** 
 * a factory for elliptical segments. These represent the area enclosed by a chord
 * connecting the points on the ellipse at angles `start` and `start + extent` and 
 * the ellipse's arc connecting those two points
 */
object EllipticalSegment {
  /** returns an elliptical segment with the given characteristics */
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalSegment(paint, width, height, start, extent)
  }
}

/** the parent class for elliptical sectors */
private[image] class EllipticalSector(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(width, height, start, extent, Arc.PIE)
}

/** 
 * a factory for elliptical sectors. These represent the area enclosed by a
 * the radii at angles `start` and `start + extent` and the arc of the 
 * ellipse connecting those radii 
 */
object EllipticalSector {
  /** returns an elliptical sector with the given characteristics */
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image = {
    new EllipticalSector(paint, width, height, start, extent)
  }
}

