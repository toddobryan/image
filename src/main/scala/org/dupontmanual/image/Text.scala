package org.dupontmanual.image

import scalafx.scene.text.{ Font => SfxFont, FontPosture, FontWeight, Text => SfxText }
import scalafx.scene.shape.Shape

/** represents a font that can be used to draw text images */
class Font /* private[image] */ (/* private[image] */ val fxFont: SfxFont) {
  /** returns a representation of the font, including name, style, and size */
  override def toString: String = {
    s"Font($name, $style, ${fxFont.size})"
  }
  
  private[this] def name: String = fxFont.family match {
    case "Serif" => "Font.Serif"
    case "SansSerif" => "Font.SansSerif"
    case "Monospaced" => "Font.Monospaced"
    case _ => repr(fxFont.family)
  }
  
  private[this] def style: String = (fxFont.style.contains("Italic"), fxFont.style.contains("Bold")) match {
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
  sealed abstract class Style private[image] (private[image] val weight: FontWeight, private[image] val posture: FontPosture)
  /** plain font style */
  case object Plain extends Style(FontWeight.NORMAL, FontPosture.REGULAR)
  /** italic font style */
  case object Italic extends Style(FontWeight.NORMAL, FontPosture.ITALIC)
  /** bold font style */
  case object Bold extends Style(FontWeight.BOLD, FontPosture.REGULAR)
  /** bold and italic font style */
  case object BoldItalic extends Style(FontWeight.BOLD, FontPosture.ITALIC)
  
  /** represents one of the fonts you're guaranteed to have */
  sealed abstract class BuiltIn private[image] (private[image] val asString: String)
  /** the built-in serif font */
  case object Serif extends BuiltIn("Serif")
  /** the built-in sans serif font */
  case object SansSerif extends BuiltIn("SansSerif")
  /** the built-in monospaced font */
  case object Monospaced extends BuiltIn("Monospaced")
  
  /**
   * returns a built-in font in the given style and size 
   */
  def apply(name: BuiltIn, style: Style, size: Int): Font = {
    require(size > 0, "the font size must be non-negative")
    new Font(SfxFont(name.asString, style.weight, style.posture, size))
  }
  
  /**
   * returns a font from the operating system in the given
   *  style and size. If there is no font with the given
   *  name, an exception will be thrown.
   */
  def apply(name: String, style: Style, size: Int): Font = {
    require(allFonts.contains(name), s"the font ${repr(name)} is not available")
    require(size > 0, "the font size must be non-negative")
    new Font(SfxFont(name, style.weight, style.posture, size))
  }
  
  private[this] lazy val allFonts: Set[String] = {
    SfxFont.fontNames.toSet
  }
}

private[image] class Text(paint: Paint, fxFont: SfxFont, string: String) extends Figure(Some(paint), None) {
  def fxShape: Shape = new SfxText(string) {
    font = fxFont
  }
}

/** object with convenience methods for creating text images */
object Text {
  private[this] val defaultFont = Font(Font.Serif, Font.Plain, 18)
  
  /** returns a text image with the given characteristics */
  def apply(paint: Paint, font: Font, text: String): Image = new Text(paint, font.fxFont, text)
  
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
