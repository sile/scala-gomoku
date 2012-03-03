package net.reduls.scala.gomoku.dic

import net.reduls.scala.gomoku.util.Misc._

object Morpheme {
  private val surId_to_morps = 
    withDictionayDataAsDIS("id-morphemes-map.bin", "morpheme.bin") {
      (in1, in2) => 
        val surfaceIdLimit = in1.readInt
        val morphemeCount = in2.readInt

        {for(i <- 0 until surfaceIdLimit; length = in1.readByte) yield
           {for(j <- 0 until length)
             yield encodeInfo(in2.readShort, in2.readShort)}.toArray
       }.toArray
    }

  private def encodeInfo(posId:Short, cost:Short): Int = 
    (posId << 16) + (cost & 0xFFFF)
}
