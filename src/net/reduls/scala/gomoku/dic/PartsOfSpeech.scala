package net.reduls.scala.gomoku.dic

import net.reduls.scala.gomoku.util.Misc._
import scala.io.Source

object PartsOfSpeech {
  private val partsOfSpeeches = {
    val in = openDictionaryData("pos.bin")
    try
      Source.fromInputStream(in).getLines.toArray
    finally
      in.close()
  }
  
  def get(posId:Int): String = partsOfSpeeches(posId)
}
