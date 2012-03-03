package net.reduls.scala.gomoku.util

import java.io.InputStream
import java.io.BufferedInputStream
import java.io.DataInputStream

object Misc {
  def openDictionaryData(filename:String): InputStream = {
    getClass.getResourceAsStream("/net/reduls/scala/gomoku/data/"+filename)
  }

  def openDictionaryDataAsDIS(filename:String): DataInputStream = {
    new DataInputStream(new BufferedInputStream(openDictionaryData(filename)))
  }

  def withDictionayData[T](filename:String)(body: InputStream => T): T = {
    val in = openDictionaryData(filename)
    try
      body(in)
    finally
      in.close()
  }

  def withDictionayDataAsDIS[T](filename:String)(body: DataInputStream => T): T = {
    val in = openDictionaryDataAsDIS(filename)
    try
      body(in)
    finally
      in.close()
  }

  def withDictionayDataAsDIS[T](filename1:String, filename2:String)(body: (DataInputStream,DataInputStream) => T): T = {
    withDictionayDataAsDIS(filename1) {
      in1 => withDictionayDataAsDIS(filename2) {
        in2 => body(in1, in2)
      }
    }
  }
}
