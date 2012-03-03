package net.reduls.scala.gomoku.dic

import net.reduls.scala.gomoku.util.Misc._

object Morpheme {
  type MorphemeInfo = Int

  private val surId_to_morps = 
    withDictionayData("id-morphemes-map.bin", "morpheme.bin") {
      (in1, surfaceIdLimit, in2, morphemeCount) => 
        {for(_ <- 1 to surfaceIdLimit; length = in1.readByte) yield
           {for(_ <- 1 to length)
             yield encodeInfo(in2.readShort, in2.readShort)}.toArray
       }.toArray
    }

  private def encodeInfo(posId:Short, cost:Short): MorphemeInfo = 
    (posId << 16) + (cost & 0xFFFF)

  def getMorphemes(surfaceId:Int): Array[MorphemeInfo] = 
    surId_to_morps(surfaceId)

  def postId(info:MorphemeInfo): Short = 
    (info>>16).toShort

  def cost(info:MorphemeInfo): Short = 
    (info&0xFFFF).toShort
}
