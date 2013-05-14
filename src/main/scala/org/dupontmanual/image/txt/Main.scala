package org.dupontmanual.image.txt

import java.io.{File, PrintWriter}

object Main extends App {
  pf("00", _00())
  pf("01", _01())
  pf("02", _02())
  pf("03", _03())
  pf("04", _04())
  pf("10", _10())
  
  def pf(name: String, text: => twirl.api.Txt) {
    val f = new PrintWriter(new File(s"src/pamflet/$name.md"))
    f.print(text)
    f.close()
  }

}