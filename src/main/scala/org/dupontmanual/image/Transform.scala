package org.dupontmanual.image

import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.scene.shape.Shape
import scalafx.scene.Node
import scalafx.scene.Group

/** represents an image with a `Transform` applied */
private[image] class Transform(image: Image, tforms: Iterable[SfxTransform]) extends Image {
  val img: Node = new Group(image.img) {
    transforms = tforms
  }
  
  def bounds: Shape = img.boundsInParent
}
