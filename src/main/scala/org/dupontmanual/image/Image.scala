package org.dupontmanual.image

import scala.xml.NodeSeq

import math.ceil
import math.Pi
import java.awt.{Graphics2D, RenderingHints}
import java.awt.image.BufferedImage
import java.awt.geom._
import java.awt.image._
import javax.swing.ImageIcon
import scala.swing.Dialog
import javax.imageio.ImageIO
import java.io.File
import java.io.ByteArrayOutputStream
import org.apache.commons.codec.binary.Base64
import java.io.FileInputStream
import java.util.Arrays
import java.awt.Shape

/** represents an image */
abstract class Image private[image] () {  
  protected lazy val img: BufferedImage = {
    val image = new BufferedImage(ceil(displayBounds.getWidth).toInt, ceil(displayBounds.getHeight).toInt, BufferedImage.TYPE_INT_ARGB)
    val g2 = image.getGraphics.asInstanceOf[Graphics2D]
    g2.translate(-displayBounds.getX, -displayBounds.getY)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    render(g2)
    image
  }
  
  private[image] lazy val displayedImg: BufferedImage = {
    val bg = new BufferedImage(img.getWidth, img.getHeight, BufferedImage.TYPE_INT_ARGB)
    val g2 = bg.getGraphics.asInstanceOf[Graphics2D]
    g2.setPaint(java.awt.Color.WHITE)
    g2.fillRect(0, 0, img.getWidth, img.getHeight)
    g2.drawRenderedImage(img, new AffineTransform())
    bg
  }
  
  /** displays the image in a dialog box */
  def display() {
    Dialog.showMessage(message = null, icon = new ImageIcon(displayedImg))
  }
  
  /**
   * saves the image as a `.png` file. If the filename doesn't already end
   * in `.png`, the extension is added.
   */
  def saveAsDisplayed(filename: String) {
    val filenameWithExt = if (filename.endsWith(".png")) filename else filename + ".png"
    ImageIO.write(displayedImg, "png", new File(filenameWithExt))
  }
  
  /** returns a byte array representing the image as a PNG file */
  def bytesPng: Array[Byte] = {
    val bytes: ByteArrayOutputStream = new ByteArrayOutputStream()
    ImageIO.write(displayedImg, "png", bytes)
    bytes.toByteArray
  }
  
  /** returns the binary value of the image as PNG image, encoded as a base64 String */
  def base64png: String = {
    Base64.encodeBase64String(bytesPng)
  }
  
  def inlineImgTag: NodeSeq = {
    <img src={ s"data:image/png;base64,${this.base64png}" } />
  }
  
  private[image] def penWidth: Double = 0.0
  
  /** the bounding box */
  /*private[image]*/ def displayBounds: Rectangle2D = bounds.getBounds2D
  /** the actual bounds of the image (may not be rectangular) */
  /*private[image]*/ def bounds: Shape
  /** renders the image to the given `Graphics2D` instance */
  private[image] def render(g2: Graphics2D): Unit
  
  /** returns the width of this `Image` */
  def width: Double = displayBounds.getWidth
  /** returns the height of this `Image` */
  def height: Double = displayBounds.getHeight
  
  /** returns a new `Image` with this `Image` centered and superimposed on `img` */
  def stackOn(img: Image): Image = Stack(this, img)
  
  /**
   * returns a new `Image` with this `Image` superimposed on `img` and aligned
   * according to `xAlign` and `yAlign`
   */
  def stackOn(img: Image, xAlign: XAlign, yAlign: YAlign): Image = Stack(this, img, xAlign, yAlign)
  
  /**
   * returns a new `Image` with this `Image` superimposed on `img` and moved
   * `dx` pixels to the right and `dy` pixels down from center. `dx` and
   * `dy` may be negative to translate this `Image` to the left and up.
   */
  def stackOn(img: Image, dx: Double, dy: Double): Image = Stack(this, img, dx, dy)
  
  /**
   * returns a new `Image` with this `Image` superimposed on `img`, aligned
   * according to `xAlign` and `yAlign` and translated `dx` pixels to
   * the right and `dy` pixels down.
   */
  def stackOn(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): Image = 
      Stack(this, img, xAlign, yAlign, dx, dy)
  
  /** equivalent to `img.stackOn(this)` */    
  def slideUnder(img: Image): Image = Stack(img, this)
  /** equivalent to `img.stackOn(this, xAlign, yAlign)` */
  def slideUnder(img: Image, xAlign: XAlign, yAlign: YAlign): Image = Stack(img, this, xAlign, yAlign)
  /** equivalent to `img.stackOn(this, dx, dy)` */
  def slideUnder(img: Image, dx: Double, dy: Double): Image = Stack(img, this, dx, dy)
  /** equivalent to `img.stackOn(this, xAlign, yAlign, dx, dy)` */
  def slideUnder(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): Image = 
      Stack(img, this, xAlign, yAlign, dx, dy)
  
  /** produces a new `Image` with `img` to the right of this `Image`, centered vertically */
  def beside(img: Image): Image = this.beside(img, YAlign.Center)
  /** produces a new `Image` with `img` to the right of this `Image`, aligned according to `yAlign` */
  def beside(img: Image, yAlign: YAlign): Image = Stack(img, this, XAlign.Left, yAlign, this.width, 0)

  /** produces a new `Image` with `img` vertically below this `Image`, centered horizontally */
  def above(img: Image): Image = this.above(img, XAlign.Center)
  /** produces a new `Image` with `img` vertically below this `Image`, aligned according to `xAlign` */
  def above(img: Image, xAlign: XAlign): Image = Stack(img, this, xAlign, YAlign.Top, 0, this.height)
  
  /** produces the `Image` obtained by reflecting this `Image` left to right */
  def flipHorizontal: Image = {
    val transformer = new AffineTransform()
    transformer.translate(width, 0)
    transformer.scale(-1, 1)
    new Transform(this, transformer)
  }
  
  /** produces the `Image` obtained by reflecting this `Image` top to bottom */
  def flipVertical: Image = {
    val transformer = new AffineTransform()
    transformer.translate(0, height)
    transformer.scale(1, -1)
    new Transform(this, transformer)
  }
  
  /** 
   * produces the `Image` obtained by scaling this `Image` horizontally
   * by `xFactor` and vertically by `yFactor`
   */
  def scale(xFactor: Double, yFactor: Double): Image = {
    val transformer = new AffineTransform()
    transformer.scale(xFactor, yFactor)
    transformer.translate(
      if (xFactor < 0) -width * xFactor else 0,
      if (yFactor < 0) -height * yFactor else 0
    )
    new Transform(this, transformer)
  }
  /** produces the `Image` obtained by scaling this `Image` horizontally by `xFactor` */
  def scaleX(xFactor: Double): Image = scale(xFactor, 1.0)
  /** produces the `Image` obtained by scaling this `Image` vertically by `yFactor` */
  def scaleY(yFactor: Double): Image = scale(1.0, yFactor)
  
  /** 
   * produces the `Image` obtained by rotating this `Image` by the angle `factor`
   * around its center
   */
  def rotate(factor: Angle): Image = {
    val transformer = new AffineTransform()
    transformer.rotate(factor.toRadians.magnitude)
    val wrongShape = new Transform(this, transformer)
    val wrongBounds = wrongShape.displayBounds
    val unmoved = new Transform(this,transformer)
    unmoved.translate(-wrongBounds.getX,-wrongBounds.getY)
  }
  
  /** 
   * produces the `Image` obtained by moving this `Image` `x` pixels right
   * and `y` pixels down. (`x` and `y` may be negative.)
   */
  def translate(x: Double, y: Double): Image = {
    val transformer = new AffineTransform()
    transformer.translate(x, y)
    new Transform(this,transformer)
  }
  
  /**
   * produces the `Image` obtained by cutting a rectangle `cropWidth` pixels
   * wide and `cropHeight` pixels tall out of this `Image` starting at a
   * point `x` pixels to the right of and `y` pixels down from the top left
   * corner
   */
  def crop(x: Double, y: Double, cropWidth: Double, cropHeight: Double): Image = {
    new Cropped(this, x, y, cropWidth, cropHeight)
  }
  /** produces the `Image` obtained by cutting `numPixels` off the left side of this `Image` */
  def cropLeft(numPixels: Double): Image = {
    require(numPixels >= 0 && numPixels <= width, s"numPixels must be a non-negative number less than the width of this image ($width).")
    this.crop(numPixels, 0, width - numPixels, height)
  }
  /** produces the `Image` obtained by cutting `numPixels` off the right side of this `Image` */
  def cropRight(numPixels: Double): Image = {
    require(numPixels >= 0 && numPixels <= width, s"numPixels must be a non-negative number less than the width of this image ($width).")
    this.crop(0, 0, width - numPixels, height)    
  }
  /** produces the `Image` obtained by cutting `numPixels` off the top side of this `Image` */
  def cropTop(numPixels: Double): Image = {
    require(numPixels >= 0 && numPixels <= height, s"numPixels must be a non-negative number less than the height of this image ($height).")
    this.crop(0, numPixels, width, height - numPixels)
  }
  /** produces the `Image` obtained by cutting `numPixels` off the bottom side of this `Image` */
  def cropBottom(numPixels: Double): Image = {
    require(numPixels >= 0 && numPixels <= height, s"numPixels must be a non-negative number less than the height of this image ($height).")
    this.crop(0, 0, width, height - numPixels)
  }
  
  /** produces whether this `Image` is bitmap-identical to `other` */
  def sameBitmapAs(other: Image): Boolean = {
    Arrays.equals(this.bytesPng, other.bytesPng)
  } 
}


