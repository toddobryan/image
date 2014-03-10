package org.dupontmanual.image

import java.awt.{Graphics2D, GraphicsEnvironment, Shape}
import java.awt.image.BufferedImage
import java.awt.RenderingHints
import java.awt.font.FontRenderContext
import java.awt.{Font => JFont}
import java.awt.font.GlyphVector
import java.awt.FontMetrics
import java.awt.geom.AffineTransform
import java.awt.geom.Rectangle2D
import java.awt.geom.Path2D

/*

/** represents a font that can be used to draw text images */
class Font private[image] (private[image] val javaFont: JFont) {
  /** returns a representation of the font, including name, style, and size */
  override def toString: String = {
    f"Font($name%s, $style%s, ${javaFont.getSize}%d)"
  }
  
  private[this] def name: String = javaFont.getName match {
    case "Serif" => "Font.Serif"
    case "SansSerif" => "Font.SansSerif"
    case "Monospaced" => "Font.Monospaced"
    case _ => repr(javaFont.getName)
  }
  
  private[this] def style: String = (javaFont.isItalic, javaFont.isBold) match {
    case (false, false) => "Font.Plain"
    case (true, false) => "Font.Italic"
    case (false, true) => "Font.Bold"
    case (true, true) => "Font.BoldItalic"
  }
}

/** 
 *  contains objects representing built-in fonts, different text styles,
 *  and convenience methods for creating new fonts
 */
object Font {
  /** 
   * represent the style of a font, either plain or some combination
   *   of bold and italic
   */
  sealed abstract class Style private[image] (private[image] val asInt: Int)
  /** plain font style */
  case object Plain extends Style(JFont.PLAIN)
  /** italic font style */
  case object Italic extends Style(JFont.ITALIC)
  /** bold font style */
  case object Bold extends Style(JFont.BOLD)
  /** bold and italic font style */
  case object BoldItalic extends Style(JFont.BOLD | JFont.ITALIC)
  
  /** represents one of the fonts you're guaranteed to have */
  sealed abstract class BuiltIn private[image] (private[image] val asString: String)
  /** the built-in serif font */
  case object Serif extends BuiltIn(JFont.SERIF)
  /** the built-in sans serif font */
  case object SansSerif extends BuiltIn(JFont.SANS_SERIF)
  /** the built-in monospaced font */
  case object Monospaced extends BuiltIn(JFont.MONOSPACED)
  
  /**
   * returns a built-in font in the given style and size 
   */
  def apply(name: BuiltIn, style: Style, size: Int): Font = {
    require(size > 0, "the font size must be non-negative")
    new Font(new JFont(name.asString, style.asInt, size))
  }
  
  /**
   * returns a font from the operating system in the given
   *  style and size. If there is no font with the given
   *  name, an exception will be thrown.
   */
  def apply(name: String, style: Style, size: Int): Font = {
    require(allFonts.contains(name), s"the font ${repr(name)} is not available")
    require(size > 0, "the font size must be non-negative")
    new Font(new JFont(name, style.asInt, size))
  }
  
  private[this] lazy val allFonts: Set[String] = {
    GraphicsEnvironment.getLocalGraphicsEnvironment.getAvailableFontFamilyNames.toList.toSet
  }
}

private[image] class Text(paint: Paint, font: JFont, string: String) extends FigureFilled(paint) {
  val awtShape: Shape = {
    val sampGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics().asInstanceOf[Graphics2D]
    sampGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    val frc: FontRenderContext = sampGraphics.getFontRenderContext()
    val glyphs: GlyphVector = font.createGlyphVector(frc, string)
    val outline: Shape = glyphs.getOutline()
    val bounds: Rectangle2D = outline.getBounds2D()
    val dxAndDy: AffineTransform = new AffineTransform()
    dxAndDy.translate(-bounds.getX, -bounds.getY)
    val path = new Path2D.Double(outline)
    path.transform(dxAndDy)
    path
  }
}

/** object with convenience methods for creating text images */
object Text {
  private[this] val defaultFont = Font(Font.Serif, Font.Plain, 18)
  
  /** returns a text image with the given characteristics */
  def apply(paint: Paint, font: Font, text: String): Image = new Text(paint, font.javaFont, text)
  
  /** returns the text in the given font in black */
  def apply(font: Font, text: String): Image = Text(Color.Black, font, text)
  
  /** returns a text image in Font.Serif with the given color and size */
  def apply(paint: Paint, size: Int, text: String): Image = Text(paint, Font(Font.Serif, Font.Plain, size), text)
  
  /** returns a text image in the given color. The font is Font.Serif, size 18 */
  def apply(paint: Paint, text: String): Image = Text(paint, defaultFont, text)
  
  /** returns a text image in the given size. The font is Font.Serif and black */
  def apply(size: Int, text: String): Image = Text(Color.Black, size, text)
  
  /** returns a text image in Font.Serif, size 18, black */
  def apply(text: String): Image = Text(Color.Black, defaultFont, text)
}

*/