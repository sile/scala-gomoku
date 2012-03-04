package net.reduls.scala.gomoku.dic

object Morpheme {
  private val idToMorps = 
    Util.withDictionaryDataWithCount("id-morphemes-map.bin", "morpheme.bin") {
      (in1, surfaceIdLimit, in2, morphemeCount) => 
        Array.tabulate(surfaceIdLimit)(_ => 
          Array.tabulate(in1.readByte)(_ => 
            encodeInfo(in2.readShort, in2.readShort)))
    }

  private def encodeInfo(posId:Short, cost:Short): Int = 
    (posId << 16) + (cost & 0xFFFF)

  def getMorphemes(surfaceId:Int): Array[Int] = idToMorps(surfaceId)
  def posId(info:Int): Short = (info>>16).toShort
  def cost(info:Int): Short = (info&0xFFFF).toShort
}
