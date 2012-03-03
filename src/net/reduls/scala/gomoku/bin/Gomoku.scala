package net.reduls.scala.gomoku.bin

import net.reduls.scala.gomoku.dic.Matrix
import net.reduls.scala.gomoku.dic.Morpheme
import net.reduls.scala.gomoku.dic.Char
import net.reduls.scala.gomoku.dic.PartsOfSpeech
import net.reduls.scala.gomoku.dic.WordDic
import net.reduls.scala.gomoku.Tagger

object Gomoku {
  def main(args:Array[String]): Unit = {
    println(Morpheme)
    println(Matrix)
    println(Char)
    println(PartsOfSpeech.get(10))
    
    println(Tagger.parse("漢字辞典"))
  }
}
