package org.dupontmanual.image
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform
import java.awt.Shape

private[image] class Cropped(val image: Image, val x: Double, val y: Double, val w: Double, val h: Double) extends Image {
  require(x >= 0 && x <= image.width, "Illegal x value %d is outside the image, which is %d pixels wide.".format(x, image.width))
  require(y >= 0 && y <= image.height, "Illegal y value %d is outside the image, which is %d pixels tall.".format(y, image.height))
  require(w >= 0 && x + w <= image.width, "Illegal width %d (must be at least zero and %d + width must be less than %d).".format(w, x, image.width))
  require(h >= 0 && y + h <= image.height, "Illegal height %d (must be at least zero and %d + height must be less than %d).".format(h, y, image.height))

  private[image] def render(g2: Graphics2D) = {
    val origClip = g2.getClip
    g2.setClip(new Rectangle2D.Double(0, 0, w, h))
    g2.drawRenderedImage(image.displayedImg, AffineTransform.getTranslateInstance(-x, -y))
    g2.setClip(origClip)
  }
  private[image] def bounds: Shape = new Rectangle2D.Double(0, 0, w, h)
}