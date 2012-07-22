package image

import math.ceil

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

abstract class Image {  
  lazy val img: BufferedImage = {
    val image = new BufferedImage(ceil(displayBounds.width).toInt, ceil(displayBounds.height).toInt, BufferedImage.TYPE_INT_ARGB)
    val g2 = image.getGraphics.asInstanceOf[Graphics2D]
    g2.translate(-displayBounds.topLeft.x, -displayBounds.topLeft.y)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    render(g2)
    image
  }
  
  lazy val displayedImg: BufferedImage = {
    val bg = new BufferedImage(img.getWidth, img.getHeight, BufferedImage.TYPE_INT_ARGB)
    val g2 = bg.getGraphics.asInstanceOf[Graphics2D]
    g2.setColor(java.awt.Color.WHITE)
    g2.fillRect(0, 0, img.getWidth, img.getHeight)
    g2.drawRenderedImage(img, new AffineTransform())
    bg
  }
  
  def display() {
    Dialog.showMessage(message = null, icon = new ImageIcon(displayedImg))
  }
  
  def saveAsDisplayed(filename: String) {
    ImageIO.write(displayedImg, "png", new File(filename))
  }
  
  def bytesPng: Array[Byte] = {
    val bytes: ByteArrayOutputStream = new ByteArrayOutputStream()
    ImageIO.write(displayedImg, "png", bytes)
    bytes.toByteArray
  }
  
  def base64png: String = {
    Base64.encodeBase64String(bytesPng)
  }
  
  def displayBounds: Bounds
  def bounds: Bounds
  def render(g2: Graphics2D)
  
  def width: Double = bounds.width
  def height: Double = bounds.height
  
  def stackOn(img: Image): Image = Stack(this, img)
  def stackOn(img: Image, xAlign: XAlign, yAlign: YAlign): Image = Stack(this, img, xAlign, yAlign)
  def stackOn(img: Image, dx: Double, dy: Double): Image = Stack(this, img, dx, dy)
  def stackOn(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): Image = 
      Stack(this, img, xAlign, yAlign, dx, dy)
  
  def slideUnder(img: Image): Image = Stack(img, this)
  def slideUnder(img: Image, xAlign: XAlign, yAlign: YAlign): Image = Stack(img, this, xAlign, yAlign)
  def slideUnder(img: Image, dx: Double, dy: Double): Image = Stack(img, this, dx, dy)
  def slideUnder(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): Image = 
      Stack(img, this, xAlign, yAlign, dx, dy)
      
  def beside(img: Image): Image = this.beside(img, YAlign.center)
  def beside(img: Image, yAlign: YAlign): Image = Stack(img, this, XAlign.left, yAlign, this.width, 0)

  def above(img: Image): Image = this.above(img, XAlign.center)
  def above(img: Image, xAlign: XAlign): Image = Stack(img, this, xAlign, YAlign.top, 0, this.height)
  
  def flipHorizontal: Image = {
    val transformer = new AffineTransform()
    transformer.translate(width, 0)
    transformer.scale(-1, 1)
    new Transform(this, transformer)
  }
  
  def flipVertical: Image = {
    val transformer = new AffineTransform()
    transformer.translate(0, height)
    transformer.scale(1, -1)
    new Transform(this, transformer)
  }
  
  def sameBitmap(other: Image): Boolean = {
    Arrays.equals(this.bytesPng, other.bytesPng)
  }
  
}

