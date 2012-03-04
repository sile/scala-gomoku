package net.reduls.scala.gomoku.dic

import scala.io.Source

object PartsOfSpeech {
  private val partsOfSpeeches = 
    Util.withDictionaryData("pos.bin") {
      in => Source.fromInputStream(in).getLines.toArray
    }
  
  def apply(posId:Int): String = partsOfSpeeches(posId)
}

