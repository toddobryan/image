package net.toddobryan.image

import scalafx.geometry.{ Bounds, BoundingBox }
import scalafx.scene.image.{ ImageView }
import scalafx.scene.{ Group, Node, Scene }
import scalafx.scene.shape.{ Rectangle => SfxRectangle, Shape }
import scalafx.geometry.Rectangle2D


private[image] class Cropped(val theImg: Image, val x: Double, val y: Double, val w: Double, val h: Double) extends Image {
  require(x >= 0 && x <= theImg.width, f"Illegal x value $x is outside the image, which is ${theImg.width} pixels wide.")
  require(y >= 0 && y <= theImg.height, f"Illegal y value $y is outside the image, which is ${theImg.height} pixels tall.")
  require(w >= 0 && x + w <= theImg.width, f"Illegal width $w (must be at least zero and $x + width must be less than ${theImg.width}).")
  require(h >= 0 && y + h <= theImg.height, f"Illegal height $h (must be at least zero and $y + height must be less than ${theImg.height}).")
  
  def buildImage(): Node = new ImageView(new Scene { root = new Group(theImg.buildImage()) }.snapshot(null)) {
    viewport = new Rectangle2D(Cropped.this.x, Cropped.this.y, w, h)
  }

  /*private[image]*/ def bounds: Shape = SfxRectangle(0, 0, w, h)
}