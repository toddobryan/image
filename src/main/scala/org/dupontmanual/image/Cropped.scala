package org.dupontmanual.image
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform
import java.awt.Shape

private[image] class Cropped(val image: Image, val x: Double, val y: Double, val w: Double, val h: Double) extends Image {
  require(x >= 0 && x <= image.width, f"Illegal x value $x is outside the image, which is ${image.width} pixels wide.")
  require(y >= 0 && y <= image.height, f"Illegal y value $y is outside the image, which is ${image.height} pixels tall.")
  require(w >= 0 && x + w <= image.width, f"Illegal width $w (must be at least zero and $x + width must be less than ${image.width}).")
  require(h >= 0 && y + h <= image.height, f"Illegal height $h (must be at least zero and $y + height must be less than ${image.height}).")

  private[image] def render(g2: Graphics2D) = {
    val origClip = g2.getClip
    g2.setClip(new Rectangle2D.Double(0, 0, w, h))
    g2.drawRenderedImage(image.displayedImg, AffineTransform.getTranslateInstance(-x, -y))
    g2.setClip(origClip)
  }
  /*private[image]*/ def bounds: Shape = new Rectangle2D.Double(0, 0, w, h)
}