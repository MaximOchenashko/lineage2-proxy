package network.tcp.packets

import java.nio.{ByteBuffer, ByteOrder}

/**
  * @author iRevThis
  */
abstract class SHexPacket extends ClientPacket {

  def hexBuffer: ByteBuffer

  val buffer = {
    val hexString = Stream.continually(hexBuffer.getChar).takeWhile(_ != 0).mkString
    ByteBuffer.wrap(hexStringToByteArray(hexString)).order(ByteOrder.LITTLE_ENDIAN)
  }

}
