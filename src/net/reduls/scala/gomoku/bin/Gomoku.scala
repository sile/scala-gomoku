package net.reduls.scala.gomoku.bin

import scala.io.Source
import net.reduls.scala.gomoku.Tagger

object Gomoku {
  def main(args:Array[String]) {
    args match {
      case Array("-wakati", _*) => wakati()
      case _                    => parse()
    }
  }
  
  private def wakati() {
    for(line <- Source.fromInputStream(System.in).getLines)
      println(Tagger.wakati(line).mkString(" "))
  }

  private def parse() {
    for(line <- Source.fromInputStream(System.in).getLines) {
      for(m <- Tagger.parse(line))
        println(m.surface+"\t"+m.feature)
      println("EOS")
    }
  }
}
