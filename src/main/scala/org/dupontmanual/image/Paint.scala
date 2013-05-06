package org.dupontmanual.image

/** 
 * abstract class for the paint used to fill images.
 * 
 * Currently, the only subclass is `[[Color]]`, though this may be
 * extended to patterns and gradients in the future.
 */
abstract class Paint private[image]() {
  private[image] def awtPaint: java.awt.Paint
}