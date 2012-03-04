package net.reduls.scala.gomoku.dic

import net.reduls.scala.gomoku.util.Misc._

object Char {
  final class Category(val id:Int, val invoke:Boolean, val group:Boolean, val length:Byte)

  private val charCode = 
    withDictionayData("code-map.bin") {
      (in, codeLimit) => for(_ <- Array.range(0, codeLimit)) yield in.readChar
    }

  private val (categories, compatibleMasks) = 
    withDictionayData("category.bin", "code.bin") {
      (in1, charCategoryNum, in2, codeLimit) =>
        val charCategories = 
          for(i <- Array.range(0, charCategoryNum))
            yield new Category(i, in1.readByte==1, in1.readByte==1, in1.readByte)

        val pair = (for(i <- Array.range(0, codeLimit))
                      yield (charCategories(in2.readByte), in2.readShort)).unzip
        (pair._1.toArray, pair._2.toArray)
    }

  def code(c:Char): Char = charCode(c)
  def category(c:Char): Category = categories(c)
  def isCompatible(c1:Char, c2:Char): Boolean = (compatibleMasks(c1) & compatibleMasks(c2)) != 0
}
