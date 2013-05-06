package image

import math.{abs, round, Pi}

/** units for angles (currently just `degrees` and `radians`) */
private[image] object AngleUnit extends Enumeration {
  type AngleUnit = Value
  val degrees, radians = Value
}

/** 
 * represents an angle with a `Double` magnitude and a unit (`degrees` or `radians`).
 * Use the `Angle.apply` method from the companion object, or (better) an implicit
 * conversion from `Double` to `AngleBuilder` to create `Angle` instances.
 */
class Angle private[image] (val magnitude: Double, val units: AngleUnit.AngleUnit) {
  /** converts this `Angle` to radians. */
  def toRadians: Angle = units match {
    case AngleUnit.radians => this
    case AngleUnit.degrees => Angle(magnitude * Pi / 180.0, AngleUnit.radians)
  }
  
  /** converts this `Angle` to degrees. */
  def toDegrees: Angle = units match {
    case AngleUnit.degrees => this
    case AngleUnit.radians => Angle(magnitude * 180.0 / Pi, AngleUnit.degrees)
  }
  
  /**
   * returns a reference angle between `-Pi` and `Pi` `radians` 
   * (or `-180` and `180` `degrees`) that is 
   * co-terminal with this angle and in the same units.
   */
  lazy val refAngle: Angle = units match {
    case AngleUnit.degrees => {
      if (magnitude >= -180.0 && magnitude <= 180.0) this
      else {
        val revols = round(abs(magnitude) / 360.0)
        if (magnitude < 0) Angle(magnitude + 360.0 * revols, AngleUnit.degrees)
        else Angle(magnitude - 360.0 * revols, AngleUnit.degrees)
      }
    }
    case AngleUnit.radians => {
      if (magnitude >= -Pi && magnitude <= Pi) this
      else {
        val revols = round(abs(magnitude) / (2 * Pi))
        if (magnitude < 0) Angle(magnitude + (2 * Pi * revols), AngleUnit.radians)
        else Angle(magnitude - (2 * Pi * revols), AngleUnit.radians)
      }
    }
  }
  
  /**
   * returns the result of adding this `Angle` to `that`. The resulting
   * angle has the same units as this `Angle`.
   */
  def +(that: Angle) = {
    if (this.units == that.units) Angle(this.magnitude + that.magnitude, this.units)
    else this.units match {
      case AngleUnit.radians => Angle(this.magnitude + that.toRadians.magnitude, AngleUnit.radians)
      case AngleUnit.degrees => Angle(this.magnitude + that.toDegrees.magnitude, AngleUnit.degrees)
    }
  }
  
  /** 
   * returns the sine of this `Angle`.
   * Angles very close (using the `~=` function from the `Angle` object) to angles 
   * that would produce sines of 0, 1/2, -1/2, 1, and -1 will produce those values.
   */ 
  def sin: Double = {
    import Angle.~=
    val refRads: Double = refAngle.toRadians.magnitude
    if (~=(refRads, 0.0) || ~=(refRads, Pi) || ~=(refRads, -Pi)) 0.0
    else if (~=(refRads, Pi / 6.0) || ~=(refRads, 5.0 * Pi / 6.0)) 0.5
    else if (~=(refRads, -Pi / 6.0) || ~=(refRads, -5.0 * Pi / 6.0)) -0.5
    else if (~=(refRads, -Pi / 2.0)) -1.0
    else if (~=(refRads, Pi / 2.0)) 1.0
    else math.sin(refRads)
  }

  /** 
   * returns the cosine of this `Angle`.
   * Angles very close (using the `~=` function from the `Angle` object) to angles 
   * that would produce cosines of 0, 1/2, -1/2, 1, and -1 will produce those values.
   */   
  def cos: Double = {
    import Angle.~=
    val refRads: Double = refAngle.toRadians.magnitude
    if (~=(refRads, 0.0)) 1.0
    else if (~=(refRads, Pi) || ~=(refRads, -Pi)) -1.0
    else if (~=(refRads, Pi / 3.0) || ~=(refRads, -Pi / 3.0)) 0.5
    else if (~=(refRads, 2.0 * Pi / 3.0) || ~=(refRads, -2.0 * Pi / 3.0)) -0.5
    else if (~=(refRads, -Pi / 2.0) || ~=(refRads, Pi / 2.0)) 0.0
    else math.cos(refRads)
  }
  
  /** 
   * returns the tangent of this `Angle`.
   * Angles very close (using the `~=` function from the `Angle` object) to angles 
   * that would produce tangents of 0, 1, and -1 will produce those values. Angles
   * very close to angles with undefined tangents will produce `Double.NaN`.
   */ 
  def tan: Double = {
    import Angle.~=
    val refRads: Double = refAngle.toRadians.magnitude
    if (~=(refRads, 0.0) || ~=(refRads, Pi) || ~=(refRads, -Pi)) 0.0
    else if (~=(refRads, Pi / 4.0) || ~=(refRads, -3.0 * Pi / 4.0)) 1.0
    else if (~=(refRads, 3.0 * Pi / 4.0) || ~=(refRads, -Pi / 4.0)) -1.0
    else if (~=(refRads, -Pi / 2.0) || ~=(refRads, Pi / 2.0)) Double.NaN
    else math.tan(refRads)    
  }
  
  /** 
   * returns the `String` value of this `Angle`, a floating-point number
   * with six digits of precision, a space, and the name of the units
   * (`radians` or `degrees`).
   */
  override def toString: String = {
    "%f %s".format(magnitude, units)
  }
  
  /** returns `true` if `other.isInstanceOf[Angle]`. */
  private[Angle] def canEqual(other: Any): Boolean =
    other.isInstanceOf[Angle]

  /**
   * returns whether this `Angle` is equal to `other`.
   * If the two angles have the same units, returns whether
   * the `Double` magnitudes are the same. If not, both angles are
   * converted to `radians` and the magnitudes are compared. The 
   * `Double` values are compared using exact comparison, so
   * beware of round-off errors.  
   */
  override def equals(other: Any): Boolean = other match {
    case that: Angle => {
      (that canEqual this) &&
      (if (units == that.units) magnitude == that.magnitude
      else (toRadians.magnitude == that.toRadians.magnitude))
    }
    case _ => false
  }
}

/** companion object to the `Angle` class */
object Angle {
  /** returns an `Angle` with the given magnitude and units */
  def apply(magnitude: Double, units: AngleUnit.AngleUnit): Angle = new Angle(magnitude, units)
  
  private[image] def ~=(d1: Double, d2: Double): Boolean = abs(d1 - d2) < 0.000000001
  
  private[this] type AngleUnit = AngleUnit.AngleUnit
  
  /** a value representing degrees as units */
  val degrees = AngleUnit.degrees
  
  /** a value representing radians as units */
  val radians = AngleUnit.radians
}

/** 
 * wraps a `Double` value to allow the easy creation of `Angle`s.
 * 
 * Using the implicit conversion `doubleToAngleBuilder` in the `image`
 * package object, the idiomatic way to create `Angle`s is
 * {{{
 * 90.degrees
 * 45.degrees
 * 0.radians
 * (math.Pi / 2).radians
 * }}}
 */
class AngleBuilder(magnitude: Double) {
  /** returns an `Angle` of magnitude with units `degrees`. */
  def degrees: Angle = Angle(magnitude, AngleUnit.degrees)
  
  /** returns an `Angle` of magnitude with units `radians`. */
  def radians: Angle = Angle(magnitude, AngleUnit.radians)
}

