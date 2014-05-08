package org.dupontmanual.image

trait World[W <: World[_]] {
  self: W =>
  val width: Int = 200
  val height: Int = 200

  def asImage(): Image = RectangleFilled(Color.White, width, height)

  def afterTick(): W = this

  def afterKeyPressed(ke: String): W = this

  def afterMouseClicked(x: Double, y: Double): W = this
}
