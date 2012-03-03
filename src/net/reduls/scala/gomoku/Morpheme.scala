package net.reduls.scala.gomoku

final class Morpheme(val surface:String, val feature:String, val start:Int) {
  override def toString: String = surface+"@"+feature
}
