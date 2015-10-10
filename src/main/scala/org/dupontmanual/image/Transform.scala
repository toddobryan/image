package org.dupontmanual.image

import scalafx.Includes._
import scalafx.geometry.Bounds
import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import scalafx.scene.transform.{ Transform => SfxTransform }
import scalafx.scene.{ Group, Node, Parent }
import scalafx.scene.shape.{ Rectangle => SfxRectangle, Shape }
import scalafx.application.Platform
import scalafx.concurrent.Task

/** represents an image with a `Transform` applied */
private[image] class Transform(image: Image, tforms: Iterable[SfxTransform]) extends Image {
  private[this] val bds = new Pane() {
    children = image.bounds
    transforms = tforms
  }.boundsInParent.value

  
  def buildImage(): Node = {
    val correction = SfxTransform.translate(-bds.minX, -bds.minY)
    def newNode(): Node = {
      new Pane {
        children = image.buildImage()
        transforms = Iterable(correction) ++ tforms
        prefWidth = bds.width
        prefHeight = bds.height
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
  
  def bounds: Shape = SfxRectangle(bds.minX, bds.minY, bds.width, bds.height)
}
