package net.reduls.scala.gomoku.dic

object Matrix {
  private val (matrix, leftNum) =
    Util.withDictionaryDataWithCount("matrix.bin") {
      (in, leftNum) =>
        val rightNum = in.readInt
        val matrix = new Array[Short](rightNum * leftNum)
        for(l <- 0 until leftNum; r <- 0 until rightNum)
          matrix(r*leftNum + l) = in.readShort
        (matrix, leftNum)
    }
  
  def linkCost(leftId:Short, rightId:Short): Short = matrix(rightId*leftNum + leftId)
}
