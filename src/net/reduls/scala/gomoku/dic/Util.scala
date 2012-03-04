package net.reduls.scala.gomoku.dic

import java.io.InputStream
import java.io.BufferedInputStream
import java.io.DataInputStream

object Util {
  def withDictionaryData[T](filename:String)(body: DataInputStream => T): T = {
    val in = openDictionaryData(filename)
    try
      body(in)
    finally
      in.close()
  }

  def withDictionaryDataWithCount[T](filename:String)(body: (DataInputStream,Int) => T): T = 
    withDictionaryData(filename) {
      in => body(in, in.readInt)
    }

  def withDictionaryDataWithCount[T](filename1:String, filename2:String)(body: (DataInputStream,Int,DataInputStream,Int) => T): T =
    withDictionaryDataWithCount(filename1) {
      (in1,count1) => withDictionaryDataWithCount(filename2) {
        (in2,count2) => body(in1, count1, in2, count2)
      }
    }

  private def openDictionaryData(filename:String): DataInputStream = 
    new DataInputStream(
      new BufferedInputStream(
        getClass.getResourceAsStream("/net/reduls/scala/gomoku/data/"+filename)))
}
