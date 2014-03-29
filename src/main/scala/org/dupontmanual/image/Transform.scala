package org.dupontmanual.image

import scalafx.Includes._
import scalafx.geometry.Bounds
import scalafx.scene.image.ImageView
import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.scene.Node
import scalafx.scene.Group
import scalafx.scene.shape.{ Rectangle => SfxRectangle, Shape }
import scalafx.application.Platform
import scalafx.concurrent.Task

/** represents an image with a `Transform` applied */
private[image] class Transform(image: Image, tforms: Iterable[SfxTransform]) extends Image {
  val img: Node = {
    def newNode(): Node = {
      new Group {
        content = image.img
        transforms = tforms
      }
    }
    if (Platform.isFxApplicationThread) {
      newNode()
    } else {
      val theNode = Task[Node] { newNode() }
      Platform.runLater(theNode)
      theNode.get()
    }
  }
  
  def bounds: Shape = {
    val bds = img.boundsInParent.value
    SfxRectangle(bds.minX, bds.minY, bds.width, bds.height)
  }
}
