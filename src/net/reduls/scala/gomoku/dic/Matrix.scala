package net.reduls.scala.gomoku.dic

import net.reduls.scala.gomoku.util.Misc._

object Matrix {
  private val matrix =
    withDictionayData("matrix.bin") {
      (in,leftNum) =>
        val rightNum = in.readInt()
        val matrix = Array.ofDim[Short](rightNum, leftNum)
        for(l <- 0 until leftNum; r <- 0 until rightNum)
          matrix(r)(l) = in.readShort()
        matrix
    }
  
  def linkCost(leftId:Short, rightId:Short): Short = 
    matrix(rightId)(leftId)
}
