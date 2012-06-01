package image

import scala.math._
import java.awt.image.BufferedImage
import scala.swing.Dialog
import javax.swing.ImageIcon
import java.awt.{Color, Graphics2D}
import java.awt.Font
import java.awt.FontMetrics
import java.awt.RenderingHints

/*
case class Point(x: Int, y: Int) {
  def translate(dx: Int, dy: Int): Point = Point(x + dx, y + dy)
}

class Image(theWidth: Int, theHeight: Int) {
  protected[image] val img: BufferedImage = new BufferedImage(theWidth, theHeight, BufferedImage.TYPE_INT_ARGB)
  
  lazy val g2d: Graphics2D = {
    val g2 = img.getGraphics.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    g2
  }
  
  def display() {
    // add 1 pixel frame around image
    val displayImg: Image = new Image(this.width + 2, this.height + 2)
    displayImg.g2d.setPaint(Color.WHITE)
    displayImg.g2d.fillRect(0, 0, displayImg.width - 1, displayImg.height - 1)
    displayImg.g2d.setPaint(Color.BLACK)
    displayImg.g2d.drawRect(0, 0, displayImg.width - 1, displayImg.height - 1)
    displayImg.g2d.drawImage(img, 1, 1, null)
    Dialog.showMessage(message = null, icon = new ImageIcon(displayImg.img))
  }
  
  /**
   * creates a new image with this image on top of other
   */
  def stackOn(other: Image): Image = {
    Image.stack(other, this)
  }
  
  def slideUnder(other: Image): Image = {
    Image.stack(this, other)
  }
  
  def width: Int = img.getWidth
  def height: Int = img.getHeight
}

object Image {
  def stack(back: Image, front: Image): Image = {
    val newWidth: Int = max(back.width, front.width)
    val newHeight: Int = max(back.height, front.height)
    val newImage: Image = new Image(newWidth, newHeight)
    newImage.g2d.drawImage(back.img, splitDiff(newWidth, back.width), splitDiff(newHeight, back.height), null)
    newImage.g2d.drawImage(front.img, splitDiff(newWidth, front.width), splitDiff(newHeight, front.height), null)
    newImage
  }
  
  def xs(vertices: List[Point]): List[Int] = vertices.map(_.x)
  
  def ys(vertices: List[Point]): List[Int] = vertices.map(_.y)
  
  def range(nums: List[Int]): Int = {
    val numsSorted = nums.sorted
    numsSorted.last - numsSorted.head
  }

  def splitDiff(big: Int, small: Int): Int = (big - small) / 2

  def lawOfCos(s1: Int, s2: Int, ang3: Double): Int = {
    round(sqrt(s1 * s1 + s2 * s2 - 2 * s1 * s2 * cos(toRadians(ang3)))).toInt
  }
  
  def lawOfSin(s1: Int, ang1: Double, ang2: Double): Int = {
    round((s1 / sin(toRadians(ang1))) * sin(toRadians(ang2))).toInt
  }
}

class Ellipse(width: Int, height: Int, mode: Mode, color: Color) extends Image(width, height) {
  g2d.setPaint(color)
  if (mode == Mode.SOLID) g2d.fillOval(0, 0, width - 1, height - 1)
  else g2d.drawOval(0, 0, width - 1, height - 1)
}

object Ellipse {
  def apply(width: Int, height: Int, mode: Mode, color: Color): Ellipse = {
    new Ellipse(width, height, mode, color)
  }
}

class Circle(radius: Int, mode: Mode, color: Color) extends Ellipse(radius, radius, mode, color) {
}

object Circle {
  def apply(radius: Int, mode: Mode, color: Color): Circle = {
    new Circle(radius, mode, color)
  }
}

class Rectangle(width: Int, height: Int, mode: Mode, color: Color) extends Image(width, height) {
  g2d.setPaint(color)
  if (mode == Mode.SOLID) g2d.fillRect(0, 0, width - 1, height - 1)
  else g2d.drawRect(0, 0, width - 1, height - 1)
}

object Rectangle {
  def apply(width: Int, height: Int, mode: Mode, color: Color): Rectangle = {
    new Rectangle(width, height, mode, color)
  }
}

class Square(side: Int, mode: Mode, color: Color) extends Rectangle(side, side, mode, color) {
}

object Square {
  def apply(side: Int, mode: Mode, color: Color): Square = {
    new Square(side, mode, color)
  }
}

class Text(width: Int, height: Int, text: String, font: Font, color: Color) extends Image(width, height) {
  g2d.setPaint(color)
  g2d.setFont(font)
  g2d.drawString(text, 0, g2d.getFontMetrics.getMaxAscent)
}

object Text {
  def apply(text: String, size: Int, color: Color): Text = {
    val font: Font = new Font(Font.SERIF, Font.PLAIN, size)
    val dummyImage: Image = Square(10, Mode.SOLID, Color.BLUE)
    val metrics: FontMetrics = dummyImage.g2d.getFontMetrics(font)
    new Text(metrics.stringWidth(text), metrics.getMaxAscent + metrics.getMaxDescent, text, font, color)
  }
}

/**
 * represents a triangle with one of its segments horizontal
 * that segment is of length width, with a vertex at each end
 * the third vertex is descent pixels below the horizontal line
 * and offset pixels to the right of its left edge (both descent
 * and offset may be negative)
 */
private[image] class Triangle(width: Int, descent: Int, offset: Int, mode: Mode, color: Color)
	extends Image(if (offset >= 0) max(width, offset) else width + abs(offset), abs(descent)) {
  g2d.setPaint(color)
  val xs = if (offset >= 0) Array(0, width - 1, offset - 1)
  			else Array(abs(offset) - 1, abs(offset) + width - 1, 0)
  val ys = if (descent >= 0) Array(0, 0, descent - 1)
  			else Array(abs(descent) - 1, abs(descent) - 1, 0)
  if (mode == Mode.SOLID) g2d.fillPolygon(xs, ys, 3)
  else g2d.drawPolygon(xs, ys, 3)
}

object Triangle {
  def sideSideSide(a: Int, b: Int, c: Int, mode: Mode, color: Color): Triangle = {
    if (a + b < c || b + c < a || a + c < b) throw new Exception("your edges should obey the triangle inequality")
    val x = (a * a + c * c - b * b) / (2 * a)
    val y = round(sqrt(c * c - x * x)).asInstanceOf[Int]
    new Triangle(a, -y, x, mode, color)
  }
  
  def equilateral(s: Int, mode: Mode, color: Color): Triangle = sideSideSide(s, s, s, mode, color)
  
  def angleSideSide(angB: Double, a: Int, c: Int, mode: Mode, color: Color): Triangle = {
    sideSideSide(a, Image.lawOfCos(a, c, angB), c, mode, color)
  }
  
  def sideAngleSide(a: Int, angC: Double, b: Int, mode: Mode, color: Color): Triangle = {
    sideSideSide(a, b, Image.lawOfCos(a, b, angC), mode, color)
  }
  
  def sideSideAngle(b: Int, c: Int, angA: Double, mode: Mode, color: Color): Triangle = {
    sideSideSide(Image.lawOfCos(b, c, angA), b, c, mode, color)
  }
  
  def sideAngleAngle(a: Int, angB: Double, angC: Double, mode: Mode, color: Color): Triangle = {
    val angA: Double = 180 - angB - angC
    sideSideSide(a, Image.lawOfSin(a, angA, angB), Image.lawOfSin(a, angA, angC), mode, color)
  }
  
  def angleSideAngle(angA: Double, b: Int, angC: Double, mode: Mode, color: Color): Triangle = {
    val angB: Double = 180 - angA - angC
    sideSideSide(Image.lawOfSin(b, angB, angA), b, Image.lawOfSin(b, angB, angC), mode, color)
  }
  
  def angleAngleSide(angA: Double, angB: Double, c: Int, mode: Mode, color: Color): Triangle = {
    val angC: Double = 180 - angA - angB
    sideSideSide(Image.lawOfSin(c, angC, angA), Image.lawOfSin(c, angC, angB), c, mode, color)
  }
  
  def isosceles(s: Int, ang: Double, mode: Mode, color: Color): Triangle = {
    sideSideAngle(s, s, ang, mode, color)
  }
}

class Rhombus(s: Int, ang: Double, mode: Mode, color: Color)
    extends Image(Image.lawOfCos(s, s, ang), Image.lawOfCos(s, s, 180 - ang)) {
  g2d.setPaint(color)
  val xs = Array(0, (width - 1) / 2, width - 1, (width - 1) / 2)
  val ys = Array((height - 1) / 2, 0, (height - 1) / 2 , height - 1)
  if (mode == Mode.SOLID) g2d.fillPolygon(xs, ys, 4)
  else g2d.drawPolygon(xs, ys, 4)  
}

object Rhombus {
  def apply(s: Int, ang: Double, mode: Mode, color: Color): Rhombus = new Rhombus(s, ang, mode, color)
}

private[image] class Polygon(poly: java.awt.Polygon, mode: Mode, color: Color)
    extends Image(poly.getBounds.width, poly.getBounds.height) {
  g2d.setPaint(color)
  if (mode == Mode.SOLID) g2d.fillPolygon(poly)
  else g2d.drawPolygon(poly)
}

object Polygon {
  def apply(vertices: List[Point], mode: Mode, color: Color): Polygon = {
    new Polygon(new java.awt.Polygon(Image.xs(vertices).toArray, Image.ys(vertices).toArray, vertices.length),
                mode, color)
  }
}
*/