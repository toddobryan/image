package org.dupontmanual.image

import scalafx.Includes._
import scalafx.geometry.Bounds
import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.scene.Node
import scalafx.scene.Group

/** represents an image with a `Transform` applied */
private[image] class Transform(image: Image, tforms: Iterable[SfxTransform]) extends Image {
  val img: Node = new Node(image.img) {
    transforms = tforms
  }
  
  override def bounds: Bounds = img.boundsInParent.value
}
