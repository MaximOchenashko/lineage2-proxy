package network.tcp.packets.base

import scalaz.\/

/**
  * @author iRevThis
  */
trait ReceivablePacket extends AbstractPacket {
  
  def read: Throwable \/ (() => Unit)

  def readAndExecute(): Unit

  protected def getAvailableBytes: Int = buffer.remaining

  protected def readB(dst: Array[Byte]): Unit = buffer.get(dst)

  protected def readB(dst: Array[Byte], offset: Int, len: Int): Unit = buffer.get(dst, offset, len)

  protected def readC: Int = buffer.get & 0xFF

  protected def readH: Int = buffer.getShort & 0xFFFF

  protected def readD: Int = buffer.getInt

  protected def readQ: Long = buffer.getLong

  protected def readF: Double = buffer.getDouble

  protected def readS: String = Stream.continually(buffer.getChar).takeWhile(_ != 0).mkString
}
