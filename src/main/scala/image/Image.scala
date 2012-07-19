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
    val image = new BufferedImage(ceil(bounds.width + 1).toInt, ceil(bounds.height + 1).toInt, BufferedImage.TYPE_INT_ARGB)
    val g2 = image.getGraphics.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    render(g2)
    image
  }
  
  def display() {
    Dialog.showMessage(message = null, icon = new ImageIcon(img))
  }
  
  def save(filename: String) {
    ImageIO.write(this.img, "png", new File(filename))
  }
  
  def bytesPng: Array[Byte] = {
    val bytes: ByteArrayOutputStream = new ByteArrayOutputStream()
    ImageIO.write(this.img, "png", bytes)
    bytes.toByteArray
  }
  
  def base64png: String = {
    Base64.encodeBase64String(bytesPng)
  }
  
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
  
 /* def flipHorizontal(): Image = {
  
    
  } */
}
object Image {
  def sameBitmap(img: Image, filename: String): Boolean = {
    val inputStream = getClass.getResourceAsStream(filename)
    val imgBytes = img.bytesPng
    val bytes: Array[Byte] = new Array[Byte](imgBytes.length)
    inputStream.read(bytes)
    Arrays.equals(imgBytes, bytes)
  }
}
