package net.reduls.scala.gomoku.bin

import net.reduls.scala.gomoku.Morpheme
import net.reduls.scala.gomoku.Tagger
import scala.io.Source

object Gomoku {
  def main(args:Array[String]): Unit = {
    for(line <- Source.fromInputStream(System.in).getLines) {
      for(m <- Tagger.parse(line))
        println(m.surface+"\t"+m.feature)
      println("EOS")
    }
  }
}
