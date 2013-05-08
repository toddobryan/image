package org.dupontmanual.image

import java.io.InputStream
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.io.FileInputStream
import java.io.File
import java.awt.image.BufferedImage
import java.awt.geom.Rectangle2D
import java.net.URL

/**
 * represents a bitmap image
 */
class Bitmap private[image](val pathOrUrl: Either[File, URL]) extends Image {
  override protected lazy val img: BufferedImage = {
    val temp = Option(pathOrUrl match {
      case Left(path) => ImageIO.read(path)
      case Right(url) => ImageIO.read(url)
    })
    temp.getOrElse(throw new IllegalArgumentException("no image at the source indicated"))
  }
  
  private[image] def bounds = new Rectangle2D.Double(0, 0, img.getWidth, img.getHeight)
  private[image] def render(g2: Graphics2D) = g2.drawRenderedImage(img, new AffineTransform())
  
  override def toString: String = pathOrUrl match {
    case Left(path) => "Bitmap(%s)".format(repr(path.getPath()))
    case Right(url) => "Bitmap.fromUrl(%s)".format(repr(url.toString()))
  }
}

/**
 * companion object for creating `Bitmap`s from various sources
 */
object Bitmap {
  /**
   * produces a `Bitmap` representing the image file at the given path.
   * Any format recognized by `javax.imageio.ImageIO` is acceptable.
   */
  def apply(path: String): Bitmap = new Bitmap(Left(new File(path)))
  
  /**
   * produces a `Bitmap` representing the image at the given URL. The
   * image is fetched over the network, so the URL must be reachable.
   * Any format recognized by `javax.imageio.ImageIO` is acceptable.
   */
  def fromUrl(url: String): Bitmap = new Bitmap(Right(new URL(url)))
  
  /**
   * reads a file from the jar (for included images)
   */
  private[image] def fromWorkspace(path: String): Bitmap = new Bitmap(Right(getClass.getResource(path)))
}