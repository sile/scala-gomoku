package net.reduls.scala.gomoku.dic

object Char {
  case class Category(val id:Int, val invoke:Boolean, val group:Boolean, val length:Byte)

  private val charCode = 
    Util.withDictionaryDataWithCount("code-map.bin") {
      (in, codeLimit) => Array.tabulate(codeLimit)(_ => in.readChar)
    }

  private val (categories, compatibleMasks) = 
    Util.withDictionaryDataWithCount("category.bin", "code.bin") {
      (in1, charCategoryNum, in2, codeLimit) =>
        val charCategories = Array.tabulate(charCategoryNum){i => Category(i, in1.readBoolean, in1.readBoolean, in1.readByte)}
        val pair = Array.tabulate(codeLimit){_ => (charCategories(in2.readByte), in2.readShort)}.unzip
        (pair._1.toArray, pair._2.toArray)
    }

  def code(c:Char): Char = charCode(c)
  def category(c:Char): Category = categories(c)
  def isCompatible(c1:Char, c2:Char): Boolean = (compatibleMasks(c1) & compatibleMasks(c2)) != 0
}
