package org.dupontmanual.image

import scalafx.scene.paint.{ Color => SfxColor, Paint => SfxPaint }

/**
 * represents RGB(A) colors. Use the companion object to construct `Color` instances.
 */
class Color private[image] (val red: Int, val green: Int, val blue: Int, val opacity: Double = 1.0) extends Paint {
  require(red >= 0 && red <= 255, s"Illegal red value $red is outside the allowed range 0 to 255")
  require(green >= 0 && green <= 255, s"Illegal green value $green is outside the allowed range 0 to 255")
  require(blue >= 0 && blue <= 255, s"Illegal blue value $blue is outside the allowed range 0 to 255")
  require(opacity >= 0 && opacity <= 1.0, s"Illegal opacity value $opacity is outside the allowed range 0.0 to 1.0")
  
  private[Color] def this(hex: Int) = {
    this(hex >> 16 & 0x000000ff, hex >> 8 & 0x000000ff, hex & 0x000000ff, 1.0)
  }
  
  private[this] lazy val fxColor: SfxColor = SfxColor.rgb(red, green, blue, opacity)

  private[image] def fxPaint: SfxPaint = fxColor
  
  /**
   * returns this `Color` as a `String`.
   * 
   * If the value is an HTML color, produces `Color.Green`, `Color.Red`, `Color.BlanchedAlmond`,
   * etc. Be warned that a few HTML colors have identical RGB values, so, for example,
   * `Color.Fuchsia.toString` results in `Color.Magenta`.
   * 
   * If the value is not an HTML color, produces `Color(red = r, green = g, blue = b)` where
   * `r`, `g`, and `b` all represent values from `0` to `255`.
   * 
   * By default, the alpha value for a color is `255`, representing completely opaque. If the
   * alpha value is anything else, it will be displayed after the blue value.
   */
  override def toString: String = {
    val hex: Int = red << 16 | green << 8 | blue
    if (opacity == 1.0 && Color.hexToName.contains(hex)) s"Color.${Color.hexToName(hex)}"
    else {
      val alpha = if (opacity == 1.0) "" else s", opacity = ${opacity}"
      f"Color(red = $red%d, green = $green%d, blue = $blue%d$alpha%s)"
    }
  }
}

/**
 * The factory for constructing `Color` instances. It also contains values
 * for all HTML color names.
 */
object Color {
  /**
   * returns a new `Color` with the given RGBA values. All values should
   * be `Int`s from `0` to `255` inclusive, and an error will occur if 
   * values outside that range are provided.
   */
  def apply(red: Int = 0, green: Int = 0, blue: Int = 0, opacity: Double = 1.0): Color = {
    require(0 <= red && red <= 255, "red value must be between 0 and 255 inclusive")
    require(0 <= green && green <= 255, "green value must be between 0 and 255 inclusive")
    require(0 <= blue && blue <= 255, "blue value must be between 0 and 255 inclusive")
    require(0.0 <= opacity && opacity <= 1.0, "opacity value must be between 0.0 and 1.0 inclusive")
    new Color(red, green, blue, opacity)
  }
  
  private[this] val hexNameTuples = List(
    (0xf0f8ff, "AliceBlue"),
    (0xfaebd7, "AntiqueWhite"),
    (0x00ffff, "Aqua"),
    (0x7fffd4, "Aquamarine"),
    (0xf0ffff, "Azure"),
    (0xf5f5dc, "Beige"),
    (0xffe4c4, "Bisque"),
    (0x000000, "Black"),
    (0xffebcd, "BlanchedAlmond"),
    (0x0000ff, "Blue"),
    (0x8a2be2, "BlueViolet"),
    (0xa52a2a, "Brown"),
    (0xdeb887, "Burlywood"),
    (0x5f9ea0, "CadetBlue"),
    (0x7fff00, "Chartreuse"),
    (0xd2691e, "Chocolate"),
    (0xff7f50, "Coral"),
    (0x6495ed, "CornflowerBlue"),
    (0xfff8dc, "Cornsilk"),
    (0xdc143c, "Crimson"),
    (0x00ffff, "Cyan"),
    (0x00008b, "DarkBlue"),
    (0x008b8b, "DarkCyan"),
    (0xb8860b, "DarkGoldenrod"),
    (0xa9a9a9, "DarkGray"),
    (0x006400, "DarkGreen"),
    (0xbdb76b, "DarkKhaki"),
    (0x8b008b, "DarkMagenta"),
    (0x556b2f, "DarkOliveGreen"),
    (0xff8c00, "DarkOrange"),
    (0x9932cc, "DarkOrchid"),
    (0x8b0000, "DarkRed"),
    (0xe9967a, "DarkSalmon"),
    (0x8fbc8f, "DarkSeaGreen"),
    (0x483d8b, "DarkSlateBlue"),
    (0x2f4f4f, "DarkSlateGray"),
    (0x00ced1, "DarkTurquoise"),
    (0x9400d3, "DarkViolet"),
    (0xff1493, "DeepPink"),
    (0x00bfff, "DeepSkyBlue"),
    (0x696969, "DimGray"),
    (0x1e90ff, "DodgerBlue"),
    (0xb22222, "FireBrick"),
    (0xfffaf0, "FloralWhite"),
    (0x228b22, "ForestGreen"),
    (0xff00ff, "Fuchsia"),
    (0xdcdcdc, "Gainsboro"),
    (0xf8f8ff, "GhostWhite"),
    (0xffd700, "Gold"),
    (0xdaa520, "Goldenrod"),
    (0x808080, "Gray"),
    (0x008000, "Green"),
    (0xadff2f, "GreenYellow"),
    (0xf0fff0, "Honeydew"),
    (0xff69b4, "HotPink"),
    (0xcd5c5c, "IndianRed"),
    (0x4b0082, "Indigo"),
    (0xfffff0, "Ivory"),
    (0xf0e68c, "Khaki"),
    (0xe6e6fa, "Lavender"),
    (0xfff0f5, "LavenderBlush"),
    (0x7cfc00, "LawnGreen"),
    (0xfffacd, "LemonChiffon"),
    (0xadd8e6, "LightBlue"),
    (0xf08080, "LightCoral"),
    (0xe0ffff, "LightCyan"),
    (0xfafad2, "LightGoldenrodYellow"),
    (0x90ee90, "LightGreen"),
    (0xd3d3d3, "LightGrey"),
    (0xffb6c1, "LightPink"),
    (0xffa07a, "LightSalmon"),
    (0x20b2aa, "LightSeaGreen"),
    (0x87cefa, "LightSkyBlue"),
    (0x778899, "LightSlateGray"),
    (0xb0c4de, "LightSteelBlue"),
    (0xffffe0, "LightYellow"),
    (0x00ff00, "Lime"),
    (0x32cd32, "LimeGreen"),
    (0xfaf0e6, "Linen"),
    (0xff00ff, "Magenta"),
    (0x800000, "Maroon"),
    (0x66cdaa, "MediumAquamarine"),
    (0x0000cd, "MediumBlue"),
    (0xba55d3, "MediumOrchid"),
    (0x9370db, "MediumPurple"),
    (0x3cb371, "MediumSeaGreen"),
    (0x7b68ee, "MediumSlateBlue"),
    (0x00fa9a, "MediumSpringGreen"),
    (0x48d1cc, "MediumTurquoise"),
    (0xc71585, "MediumVioletRed"),
    (0x191970, "MidnightBlue"),
    (0xf5fffa, "MintCream"),
    (0xffe4e1, "MistyRose"),
    (0xffe4b5, "Moccasin"),
    (0xffdead, "NavajoWhite"),
    (0x000080, "Navy"),
    (0xfdf5e6, "OldLace"),
    (0x808000, "Olive"),
    (0x6b8e23, "OliveDrab"),
    (0xffa500, "Orange"),
    (0xff4500, "OrangeRed"),
    (0xda70d6, "Orchid"),
    (0xeee8aa, "PaleGoldenrod"),
    (0x98fb98, "PaleGreen"),
    (0xafeeee, "PaleTurquoise"),
    (0xdb7093, "PaleVioletRed"),
    (0xffefd5, "PapayaWhip"),
    (0xffdab9, "PeachPuff"),
    (0xcd853f, "Peru"),
    (0xffc0cb, "Pink"),
    (0xdda0dd, "Plum"),
    (0xb0e0e6, "PowderBlue"),
    (0x800080, "Purple"),
    (0xff0000, "Red"),
    (0xbc8f8f, "RosyBrown"),
    (0x4169e1, "RoyalBlue"),
    (0x8b4513, "SaddleBrown"),
    (0xfa8072, "Salmon"),
    (0xf4a460, "SandyBrown"),
    (0x2e8b57, "SeaGreen"),
    (0xfff5ee, "Seashell"),
    (0xa0522d, "Sienna"),
    (0xc0c0c0, "Silver"),
    (0x87ceeb, "SkyBlue"),
    (0x6a5acd, "SlateBlue"),
    (0x708090, "SlateGray"),
    (0xfffafa, "Snow"),
    (0x00ff7f, "SpringGreen"),
    (0x4682b4, "SteelBlue"),
    (0xd2b48c, "Tan"),
    (0x008080, "Teal"),
    (0xd8bfd8, "Thistle"),
    (0xff6347, "Tomato"),
    (0x40e0d0, "Turquoise"),
    (0xee82ee, "Violet"),
    (0xf5deb3, "Wheat"),
    (0xffffff, "White"),
    (0xf5f5f5, "WhiteSmoke"),
    (0xffff00, "Yellow"),
    (0x9acd32, "YellowGreen")
  )

  private[image] lazy val hexToName: Map[Int, String] = Map(hexNameTuples: _*)
  private[image] lazy val nameToHex: Map[String, Int] = 
    Map(hexNameTuples.map((is: (Int, String)) => (is._2, is._1)): _*)
  
  val AliceBlue: Color = new Color(0xf0f8ff)
  val AntiqueWhite: Color = new Color(0xfaebd7)
  val Aqua: Color = new Color(0x00ffff)
  val Aquamarine: Color = new Color(0x7fffd4)
  val Azure: Color = new Color(0xf0ffff)
  val Beige: Color = new Color(0xf5f5dc)
  val Bisque: Color = new Color(0xffe4c4)
  val Black: Color = new Color(0x000000)
  val BlanchedAlmond: Color = new Color(0xffebcd)
  val Blue: Color = new Color(0x0000ff)
  val BlueViolet: Color = new Color(0x8a2be2)
  val Brown: Color = new Color(0xa52a2a)
  val Burlywood: Color = new Color(0xdeb887)
  val CadetBlue: Color = new Color(0x5f9ea0)
  val Chartreuse: Color = new Color(0x7fff00)
  val Chocolate: Color = new Color(0xd2691e)
  val Coral: Color = new Color(0xff7f50)
  val CornflowerBlue: Color = new Color(0x6495ed)
  val Cornsilk: Color = new Color(0xfff8dc)
  val Crimson: Color = new Color(0xdc143c)
  val Cyan: Color = new Color(0x00ffff)
  val DarkBlue: Color = new Color(0x00008b)
  val DarkCyan: Color = new Color(0x008b8b)
  val DarkGoldenrod: Color = new Color(0xb8860b)
  val DarkGray: Color = new Color(0xa9a9a9)
  val DarkGreen: Color = new Color(0x006400)
  val DarkKhaki: Color = new Color(0xbdb76b)
  val DarkMagenta: Color = new Color(0x8b008b)
  val DarkOliveGreen: Color = new Color(0x556b2f)
  val DarkOrange: Color = new Color(0xff8c00)
  val DarkOrchid: Color = new Color(0x9932cc)
  val DarkRed: Color = new Color(0x8b0000)
  val DarkSalmon: Color = new Color(0xe9967a)
  val DarkSeaGreen: Color = new Color(0x8fbc8f)
  val DarkSlateBlue: Color = new Color(0x483d8b)
  val DarkSlateGray: Color = new Color(0x2f4f4f)
  val DarkTurquoise: Color = new Color(0x00ced1)
  val DarkViolet: Color = new Color(0x9400d3)
  val DeepPink: Color = new Color(0xff1493)
  val DeepSkyBlue: Color = new Color(0x00bfff)
  val DimGray: Color = new Color(0x696969)
  val DodgerBlue: Color = new Color(0x1e90ff)
  val FireBrick: Color = new Color(0xb22222)
  val FloralWhite: Color = new Color(0xfffaf0)
  val ForestGreen: Color = new Color(0x228b22)
  val Fuchsia: Color = new Color(0xff00ff)
  val Gainsboro: Color = new Color(0xdcdcdc)
  val GhostWhite: Color = new Color(0xf8f8ff)
  val Gold: Color = new Color(0xffd700)
  val Goldenrod: Color = new Color(0xdaa520)
  val Gray: Color = new Color(0x808080)
  val Green: Color = new Color(0x008000)
  val GreenYellow: Color = new Color(0xadff2f)
  val Honeydew: Color = new Color(0xf0fff0)
  val HotPink: Color = new Color(0xff69b4)
  val IndianRed: Color = new Color(0xcd5c5c)
  val Indigo: Color = new Color(0x4b0082)
  val Ivory: Color = new Color(0xfffff0)
  val Khaki: Color = new Color(0xf0e68c)
  val Lavender: Color = new Color(0xe6e6fa)
  val LavenderBlush: Color = new Color(0xfff0f5)
  val LawnGreen: Color = new Color(0x7cfc00)
  val LemonChiffon: Color = new Color(0xfffacd)
  val LightBlue: Color = new Color(0xadd8e6)
  val LightCoral: Color = new Color(0xf08080)
  val LightCyan: Color = new Color(0xe0ffff)
  val LightGoldenrodYellow: Color = new Color(0xfafad2)
  val LightGreen: Color = new Color(0x90ee90)
  val LightGrey: Color = new Color(0xd3d3d3)
  val LightPink: Color = new Color(0xffb6c1)
  val LightSalmon: Color = new Color(0xffa07a)
  val LightSeaGreen: Color = new Color(0x20b2aa)
  val LightSkyBlue: Color = new Color(0x87cefa)
  val LightSlateGray: Color = new Color(0x778899)
  val LightSteelBlue: Color = new Color(0xb0c4de)
  val LightYellow: Color = new Color(0xffffe0)
  val Lime: Color = new Color(0x00ff00)
  val LimeGreen: Color = new Color(0x32cd32)
  val Linen: Color = new Color(0xfaf0e6)
  val Magenta: Color = new Color(0xff00ff)
  val Maroon: Color = new Color(0x800000)
  val MediumAquamarine: Color = new Color(0x66cdaa)
  val MediumBlue: Color = new Color(0x0000cd)
  val MediumOrchid: Color = new Color(0xba55d3)
  val MediumPurple: Color = new Color(0x9370db)
  val MediumSeaGreen: Color = new Color(0x3cb371)
  val MediumSlateBlue: Color = new Color(0x7b68ee)
  val MediumSpringGreen: Color = new Color(0x00fa9a)
  val MediumTurquoise: Color = new Color(0x48d1cc)
  val MediumVioletRed: Color = new Color(0xc71585)
  val MidnightBlue: Color = new Color(0x191970)
  val MintCream: Color = new Color(0xf5fffa)
  val MistyRose: Color = new Color(0xffe4e1)
  val Moccasin: Color = new Color(0xffe4b5)
  val NavajoWhite: Color = new Color(0xffdead)
  val Navy: Color = new Color(0x000080)
  val OldLace: Color = new Color(0xfdf5e6)
  val Olive: Color = new Color(0x808000)
  val OliveDrab: Color = new Color(0x6b8e23)
  val Orange: Color = new Color(0xffa500)
  val OrangeRed: Color = new Color(0xff4500)
  val Orchid: Color = new Color(0xda70d6)
  val PaleGoldenrod: Color = new Color(0xeee8aa)
  val PaleGreen: Color = new Color(0x98fb98)
  val PaleTurquoise: Color = new Color(0xafeeee)
  val PaleVioletRed: Color = new Color(0xdb7093)
  val PapayaWhip: Color = new Color(0xffefd5)
  val PeachPuff: Color = new Color(0xffdab9)
  val Peru: Color = new Color(0xcd853f)
  val Pink: Color = new Color(0xffc0cb)
  val Plum: Color = new Color(0xdda0dd)
  val PowderBlue: Color = new Color(0xb0e0e6)
  val Purple: Color = new Color(0x800080)
  val Red: Color = new Color(0xff0000)
  val RosyBrown: Color = new Color(0xbc8f8f)
  val RoyalBlue: Color = new Color(0x4169e1)
  val SaddleBrown: Color = new Color(0x8b4513)
  val Salmon: Color = new Color(0xfa8072)
  val SandyBrown: Color = new Color(0xf4a460)
  val SeaGreen: Color = new Color(0x2e8b57)
  val Seashell: Color = new Color(0xfff5ee)
  val Sienna: Color = new Color(0xa0522d)
  val Silver: Color = new Color(0xc0c0c0)
  val SkyBlue: Color = new Color(0x87ceeb)
  val SlateBlue: Color = new Color(0x6a5acd)
  val SlateGray: Color = new Color(0x708090)
  val Snow: Color = new Color(0xfffafa)
  val SpringGreen: Color = new Color(0x00ff7f)
  val SteelBlue: Color = new Color(0x4682b4)
  val Tan: Color = new Color(0xd2b48c)
  val Teal: Color = new Color(0x008080)
  val Thistle: Color = new Color(0xd8bfd8)
  val Tomato: Color = new Color(0xff6347)
  val Turquoise: Color = new Color(0x40e0d0)
  val Violet: Color = new Color(0xee82ee)
  val Wheat: Color = new Color(0xf5deb3)
  val White: Color = new Color(0xffffff)
  val WhiteSmoke: Color = new Color(0xf5f5f5)
  val Yellow: Color = new Color(0xffff00)
  val YellowGreen: Color = new Color(0x9acd32)
  
  val Transparent: Color = new Color(255, 255, 255, 0)
}