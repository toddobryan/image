package org.dupontmanual.image

/** the parent class for horizontal alignments */
sealed abstract class XAlign private[image] ()
/** an object with the three types of horizontal alignments */
object XAlign {
  /** represents alignment to the left */
  object Left extends XAlign
  /** represents alignment on the center */
  object Center extends XAlign
  /** represents alignment to the right */
  object Right extends XAlign
}

/** the parent class for vertical alignments */
sealed abstract class YAlign
/** an object with the three types of vertical alignments */
object YAlign {
  /** represents alignment on the top */
  object Top extends YAlign
  /** represents alignment on the center */
  object Center extends YAlign
  /** represents alignment on the bottom */
  object Bottom extends YAlign
}

sealed abstract class Align(val xAlign: XAlign, val yAlign: YAlign)
object Align {
  object TopLeft extends Align(XAlign.Left, YAlign.Top)
  object Top extends Align(XAlign.Center, YAlign.Top)
  object TopRight extends Align(XAlign.Right, YAlign.Top)
  object Left extends Align(XAlign.Left, YAlign.Center)
  object Center extends Align(XAlign.Center, YAlign.Center)
  object Right extends Align(XAlign.Right, YAlign.Center)
  object BottomLeft extends Align(XAlign.Left, YAlign.Bottom)
  object Bottom extends Align(XAlign.Center, YAlign.Bottom)
  object BottomRight extends Align(XAlign.Right, YAlign.Bottom)
}