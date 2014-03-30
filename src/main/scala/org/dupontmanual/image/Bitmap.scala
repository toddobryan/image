package org.dupontmanual.image

import java.io.File
import java.net.URL
import scalafx.scene.Node
import scalafx.scene.image.{ Image => SfxImage, ImageView }
import scalafx.scene.shape.{ Shape, Rectangle => SfxRectangle }

/**
 * represents a bitmap image
 */
class Bitmap private[image](val pathOrUrl: Either[File, URL], val name: Option[String] = None) extends Image {
  val bitmap: SfxImage = Option(pathOrUrl match {
      case Left(path) => new SfxImage(path.toURI.toURL.toString)
      case Right(url) => new SfxImage(url.toString)
  }).getOrElse(throw new IllegalArgumentException("no image at the source indicated"))
  
  /* private[image] */ def buildImage(): Node = new ImageView(bitmap)
    
  def bounds: Shape = SfxRectangle(0, 0, bitmap.width.value, bitmap.height.value)
  
  override def toString: String = name match {
    case Some(str) => str
    case None => pathOrUrl match {
      case Left(path) => s"Bitmap(${repr(path.getPath())})"
      case Right(url) => s"Bitmap.fromUrl(${repr(url.toString())})"
    }
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
  def apply(path: String, name: Option[String] = None): Bitmap = {
    new Bitmap(Left(new File(path)), name)
  }
  
  /**
   * produces a `Bitmap` representing the image at the given URL. The
   * image is fetched over the network, so the URL must be reachable.
   * Any format recognized by `javax.imageio.ImageIO` is acceptable.
   */
  def fromUrl(url: String, name: Option[String] = None): Bitmap = {
    new Bitmap(Right(new URL(url)), name)
  }
  
  /**
   * reads a file from the jar (for included images)
   */
  private[image] def fromWorkspace(path: String, name: Option[String] = None): Bitmap = {
    new Bitmap(Right(getClass.getResource(path)), name)
  }
}