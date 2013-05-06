package image

abstract class Paint {
  private[image] def awtPaint: java.awt.Paint
}