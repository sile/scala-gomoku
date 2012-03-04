package net.reduls.scala.gomoku.bin

import net.reduls.scala.gomoku.Morpheme
import net.reduls.scala.gomoku.Tagger
import scala.io.Source

object Gomoku {
  def main(args:Array[String]): Unit = {
    if (args.length >= 1 && args(0) == "-wakati")
      wakati
    else
      parse
  }
  
  def wakati: Unit =
    for(line <- Source.fromInputStream(System.in).getLines) {
      for(w <- Tagger.wakati(line))
        print(w + " ")
      println("")
    }

  def parse: Unit = 
    for(line <- Source.fromInputStream(System.in).getLines) {
      for(m <- Tagger.parse(line))
        println(m.surface+"\t"+m.feature)
      println("EOS")
    }
}
