import scala.language.implicitConversions

package object image {
  import AngleUnit._
  
  implicit def doubleToAngleBuilder(d: Double) = new AngleBuilder(d)
  
  lazy val Calendar = Bitmap.fromWorkspace("/calendar.png")
  lazy val Hacker = Bitmap.fromWorkspace("/mad_hacker.png")
  lazy val Glyphs = Bitmap.fromWorkspace("/hieroglyphics.png")
  lazy val Book = Bitmap.fromWorkspace("/qbook.png")
  lazy val StickPerson = Bitmap.fromWorkspace("/stick-figure.png")
  lazy val TrainCar = Bitmap.fromWorkspace("/train_car.png")
  lazy val TrainEngine = Bitmap.fromWorkspace("/train_engine.png")
}