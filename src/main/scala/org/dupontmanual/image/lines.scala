package org.dupontmanual.image

import scala.math.{min, Pi}
import java.awt.Shape

/** a class with convenience methods for polygons */
private[image] object Poly {
  def topLeft(pts: List[Point]): Point = pts match {
    case Nil => throw new Exception("can't create Polygon with no vertices")
    case p :: Nil => p
    case p :: rest => {
      val tlOfRest = topLeft(rest)
      Point(min(p.x, tlOfRest.x), min(p.y, tlOfRest.y))
    }
  }

  def shape(vertices: List[Point], close: Boolean): Shape = {
    val tl = Poly.topLeft(vertices)
    val path = new java.awt.geom.Path2D.Double
    val transVs = vertices.map(p => p.translate(-tl.x, -tl.y))
    val start = transVs.head
    path.moveTo(start.x, start.y)
    for (v <- transVs.tail) {
      path.lineTo(v.x, v.y)
    }
    if (close) path.closePath()
    path    
  }
  
  def regular(sideLength: Double, numSides: Int): List[Point] = {
    if (numSides < 3) throw new Exception("a polygon must have at least three sides")
    val radius = sideLength / (2.0 * (Pi / numSides).radians.sin)
    val offsetAngle = {
      if (numSides % 2 == 1) (-Pi / 2).radians
      else if (numSides % 4 == 0) (-Pi / numSides).radians
      else 0.radians
    }
    val polarVertices = (0 to (numSides - 1)).toList.map(n => PointPolar(radius, (n * 2.0 * Pi / numSides).radians + offsetAngle))
    polarVertices.map(_.toCartesian)
  }
}

/** the parent class for filled polygons */
private[image] class Polygon(paint: Option[Paint], pen: Option[Pen], vertices: List[Point]) 
    extends Figure(paint, pen) {
  val awtShape = Poly.shape(vertices, true)
  override def toString = s"Polygon($paint, ${vertices.mkString(", ")})"
}

/** a factory for creating filled polygons */
object Polygon {
  /**
   * returns a polygon filled with the given `paint`. Since the polygon
   * is filled, the last vertex is automatically connected to the first vertex.
   * 
   * This method accepts any number of `Point` arguments greater than or equal
   * to 3.
   */
  def apply(
      paint: Option[Paint], pen: Option[Pen], 
      vertex1: Point, vertex2: Point, vertex3: Point, 
      restOfVertices: Point*): Image = {
    new Polygon(paint, pen, vertex1 :: vertex2 :: vertex3 :: restOfVertices.toList) 
  }
  def apply(paint: Paint, pen: Pen, v1: Point, v2: Point, v3: Point, restOfVertices: Point*): Image = {
    Polygon(Some(paint), Some(pen), v1, v2, v3, restOfVertices: _*)
  }
  def apply(paint: Paint, v1: Point, v2: Point, v3: Point, restOfVertices: Point*): Image = {
    Polygon(Some(paint), None, v1, v2, v3, restOfVertices: _*)
  }
  def apply(pen: Pen, v1: Point, v2: Point, v3: Point, restOfVertices: Point*): Image = {
    Polygon(None, Some(pen), v1, v2, v3, restOfVertices: _*)
  }
  def outlined(penColor: Paint, v1: Point, v2: Point, v3: Point, restOfVertices: Point*): Image = {
    Polygon(None, Some(Pen(penColor)), v1, v2, v3, restOfVertices: _*)
  }  
}

/** the parent class for connected lines */
private[image] class LineDrawing(paint: Option[Paint], pen: Option[Pen], vertices: List[Point]) 
    extends Figure(paint, pen) {
  val awtShape = Poly.shape(vertices, false)
  override def toString = {
    s"LineDrawing($pen, ${vertices.mkString(", ")})"
  }
}

/** a factory for creating an image consisting of connected lines */
object LineDrawing {
  def apply(paint: Option[Paint], pen: Option[Pen], vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new LineDrawing(paint, pen, vertex1 :: vertex2 :: restOfVertices.toList)
  }
  def apply(paint: Paint, pen: Pen, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new LineDrawing(Some(paint), Some(pen), vertex1 :: vertex2 :: restOfVertices.toList)
  }
  def apply(paint: Paint, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new LineDrawing(Some(paint), None, vertex1 :: vertex2 :: restOfVertices.toList)
  }
  def apply(pen: Pen, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new LineDrawing(None, Some(pen), vertex1 :: vertex2 :: restOfVertices.toList)
  }
  def outlined(penColor: Paint, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new LineDrawing(None, Some(Pen(penColor)), vertex1 :: vertex2 :: restOfVertices.toList)
  }
}


/** factory for filled regular polygons */
object RegularPolygon {
  /**
   * returns an image of a filled regular polygon with the given characteristics.
   * 
   * The polygon is drawn so that the bottom edge is horizontal. `numSides`
   * must be greater than 2.
   */
  def apply(paint: Option[Paint], pen: Option[Pen], sideLength: Double, numSides: Int): Image = {
    new Polygon(paint, pen, Poly.regular(sideLength, numSides))
  }
  def apply(paint: Paint, pen: Pen, sideLength: Double, numSides: Int): Image = {
    RegularPolygon(Some(paint), Some(pen), sideLength, numSides)
  }
  def apply(paint: Paint, sideLength: Double, numSides: Int): Image = {
    RegularPolygon(Some(paint), None, sideLength, numSides)
  }
  def apply(pen: Pen, sideLength: Double, numSides: Int): Image = {
    RegularPolygon(None, Some(pen), sideLength, numSides)
  }
  def outlined(penColor: Paint, sideLength: Double, numSides: Int): Image = {
    RegularPolygon(None, Some(Pen(penColor)), sideLength, numSides)
  }
}

/** the parent class for filled rectangles */
private[image] class Rectangle(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double) 
    extends Figure(paint, pen) {
  val awtShape = new java.awt.geom.Rectangle2D.Double(0, 0, width, height)
}

/** factory for filled rectangles */
object Rectangle {
  /** returns an image of a filled rectangle with the given characteristics */
  def apply(paint: Option[Paint], pen: Option[Pen], width: Double, height: Double): Image =
    new Rectangle(paint, pen, width, height)
  def apply(paint: Paint, pen: Pen, width: Double, height: Double): Image =
    Rectangle(Some(paint), Some(pen), width, height)
  def apply(paint: Paint, width: Double, height: Double): Image =
    Rectangle(Some(paint), None, width, height)
  def apply(pen: Pen, width: Double, height: Double): Image =
    Rectangle(None, Some(pen), width, height)
  def outlined(penColor: Paint, width: Double, height: Double): Image =
    Rectangle(None, Some(Pen(penColor)), width, height)
}

/** the parent class for filled squares */
private[image] class Square(paint: Option[Paint], pen: Option[Pen], side: Double) extends Figure(paint, pen) {
  val awtShape = new java.awt.geom.Rectangle2D.Double(0, 0, side, side)
}

/** factory for creating filled squares */
object Square {
  /** returns an image of a filled square with the given characteristics */
  def apply(paint: Option[Paint], pen: Option[Pen], side: Double): Image = 
    new Square(paint, pen, side)
  def apply(paint: Paint, pen: Pen, side: Double): Image = 
    Square(Some(paint), Some(pen), side)
  def apply(paint: Paint, side: Double): Image = 
    Square(Some(paint), None, side)
  def apply(pen: Pen, side: Double): Image = 
    Square(None, Some(pen), side)
  def outlined(penColor: Paint, side: Double): Image = 
    Square(None, Some(Pen(penColor)), side)
}