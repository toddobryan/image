package org.dupontmanual.image

import scalafx.geometry.{ Bounds, BoundingBox }
import scalafx.scene.image.{ ImageView }
import scalafx.scene.Node
import scalafx.geometry.Rectangle2D


private[image] class Cropped(val image: Image, val x: Double, val y: Double, val w: Double, val h: Double) extends Image {
  require(x >= 0 && x <= image.width, f"Illegal x value $x is outside the image, which is ${image.width} pixels wide.")
  require(y >= 0 && y <= image.height, f"Illegal y value $y is outside the image, which is ${image.height} pixels tall.")
  require(w >= 0 && x + w <= image.width, f"Illegal width $w (must be at least zero and $x + width must be less than ${image.width}).")
  require(h >= 0 && y + h <= image.height, f"Illegal height $h (must be at least zero and $y + height must be less than ${image.height}).")
  
  override val img: Node = new ImageView(image.writableImg) {
    viewport = new Rectangle2D(Cropped.this.x, Cropped.this.y, w, h)
  }

  override /*private[image]*/ def bounds: Bounds = new BoundingBox(0, 0, w, h)
}