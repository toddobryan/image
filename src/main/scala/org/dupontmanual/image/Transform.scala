package org.dupontmanual.image

import scalafx.Includes._
import scalafx.geometry.Bounds
import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.scene.Node
import scalafx.scene.Group
import scalafx.scene.shape.{ Rectangle => SfxRectangle, Shape }

/** represents an image with a `Transform` applied */
private[image] class Transform(image: Image, tforms: Iterable[SfxTransform]) extends Image {
  val img: Node = new Node(image.img) {
    transforms = tforms
  }
  
  def bounds: Shape = {
    val bds = img.boundsInParent.value
    SfxRectangle(bds.minX, bds.minY, bds.width, bds.height)
  }
}
