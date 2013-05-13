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
private[image] class PolygonFilled(paint: Paint, vertices: List[Point]) extends FigureFilled(paint) {
  val awtShape = Poly.shape(vertices, true)
  override def toString = s"Polygon($paint, ${vertices.mkString(", ")})"
}

/** a factory for creating filled polygons */
object PolygonFilled {
  /**
   * returns a polygon filled with the given `paint`. Since the polygon
   * is filled, the last vertex is automatically connected to the first vertex.
   * 
   * This method accepts any number of `Point` arguments greater than or equal
   * to 3.
   */
  def apply(
      paint: Paint, 
      vertex1: Point, vertex2: Point, vertex3: Point, 
      restOfVertices: Point*): Image = {
    new PolygonFilled(paint, vertex1 :: vertex2 :: vertex3 :: restOfVertices.toList) 
  }
}

/** the parent class for connected lines */
private[image] class LineDrawing(pen: Pen, vertices: List[Point], isClosed: Boolean) extends FigureOutlined(pen) {
  val awtShape = Poly.shape(vertices, isClosed)
  override def toString = {
    val verts = if (isClosed) vertices.last :: vertices else vertices
    s"LineDrawing($pen, ${verts.mkString(", ")})"
  }
}

/** a factory for creating an image consisting of connected lines */
object LineDrawing {
  /**
   * returns an image consisting of lines connecting the vertices in order,
   * drawn with the given `pen`.
   * 
   * This method requires at least two `Point` arguments, but will accept more.
   */
  def apply(pen: Pen, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    val vertices = vertex1 :: vertex2 :: restOfVertices.toList
    val isClosed = vertices.head == vertices.last
    val neededVertices = if (isClosed) vertices.init else vertices
    new LineDrawing(pen, neededVertices, isClosed)
  }
  /**
   * returns an image consisting of lines connecting the vertices in order, drawn
   * with `Pen(color)`.
   * 
   * This method requires at least two `Point` arguments, but will accept more.
   */
  def apply(color: Color, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    LineDrawing(Pen(color), vertex1, vertex2, restOfVertices: _*)
  }
}

/** factory for filled regular polygons */
object RegularPolygonFilled {
  /**
   * returns an image of a filled regular polygon with the given characteristics.
   * 
   * The polygon is drawn so that the bottom edge is horizontal. `numSides`
   * must be greater than 2.
   */
  def apply(paint: Paint, sideLength: Double, numSides: Int): Image = {
    new PolygonFilled(paint, Poly.regular(sideLength, numSides))
  }
}

/** factory for outlined regular polygons */
object RegularPolygonOutlined {
  /**
   * returns an image of an outlined regular polygon with the given characteristics.
   * 
   * The polygon is drawn so that the bottom edge is horizontal. `numSides`
   * must be greater than 2.
   */
  def apply(pen: Pen, sideLength: Double, numSides: Int): Image = {
    require(numSides > 2, "a polygon must have more than 2 sides")
    val vts = Poly.regular(sideLength, numSides)
    LineDrawing(pen, vts.last, vts.head, vts.tail: _*)
  }
  /**
   * returns an image of an outlined regular polygon drawn in `Pen(color)`.
   * 
   * The polygon is drawn so that the bottom edge is horizontal. `numSides`
   * must be greater than 2.
   */
  def apply(color: Color, sideLength: Double, numSides: Int): Image = {
    RegularPolygonOutlined(Pen(color), sideLength, numSides)
  }
}

/** class with a convenience method for rectangles */
private[image] object Rectangle {
  def shape(width: Double, height: Double) = new java.awt.geom.Rectangle2D.Double(0, 0, width, height)
}

/** the parent class for filled rectangles */
private[image] class RectangleFilled(paint: Paint, width: Double, height: Double) extends FigureFilled(paint) {
  val awtShape = Rectangle.shape(width, height)
}

/** factory for filled rectangles */
object RectangleFilled {
  /** returns an image of a filled rectangle with the given characteristics */
  def apply(paint: Paint, width: Double, height: Double): Image =
    new RectangleFilled(paint, width, height)
}

/** the parent class for outlined rectangles */
private[image] class RectangleOutlined(pen: Pen, width: Double, height: Double) extends FigureOutlined(pen) {
  val awtShape = Rectangle.shape(width, height)
}

/** factory for outlined rectangles */
object RectangleOutlined {
  /** returns an image of an outlined rectangle with the given characteristics. */
  def apply(pen: Pen, width: Double, height: Double): Image = {
    new RectangleOutlined(pen, width, height)
  }
  
  /** returns an image of an outlined rectangle drawn with `Pen(color)`. */
  def apply(color: Color, width: Double, height: Double): Image = {
    new RectangleOutlined(Pen(color), width, height)
  }
}

/** object with convenience method for squares */
private[image] object Square {
  def shape(side: Double) = new java.awt.geom.Rectangle2D.Double(0, 0, side, side)
}

/** the parent class for filled squares */
private[image] class SquareFilled(paint: Paint, side: Double) extends FigureFilled(paint) {
  val awtShape = Square.shape(side)
}

/** factory for creating filled squares */
object SquareFilled {
  /** returns an image of a filled square with the given characteristics */
  def apply(paint: Paint, side: Double): Image = 
    new SquareFilled(paint, side)
}

/** the parent class for outlined squares */
private[image] class SquareOutlined(pen: Pen, side: Double) extends FigureOutlined(pen) {
  val awtShape = Square.shape(side)
}

/** factory for outlined squares */
object SquareOutlined {
  /** returns an image of an outlined square with the given characteristics. */
  def apply(pen: Pen, side: Double): Image =
    new SquareOutlined(pen, side)
  
  /** returns an image of an outlined square drawn with `Pen(color)`. */
  def apply(color: Color, side: Double): Image =
    new SquareOutlined(Pen(color), side)
}