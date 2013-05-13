import java.io.{File, PrintWriter}

object Main extends App {
  pf("00", txt._00())
  pf("01", txt._01())
  pf("02", txt._02())
  pf("03", txt._03())
  pf("04", txt._04())
  pf("10", txt._10())
  
  def pf(name: String, text: => twirl.api.Txt) {
    val f = new PrintWriter(new File(s"src/pamflet/$name.md"))
    f.print(text)
    f.close()
  }

}