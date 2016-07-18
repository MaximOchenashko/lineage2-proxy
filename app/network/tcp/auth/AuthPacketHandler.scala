package network.tcp.auth

import java.nio.ByteBuffer

import network.errors.{NetworkError, UnknownPacket}
import network.tcp.auth.clientpackets.Auth
import network.tcp.packets.handlers.PacketHandler
import network.tcp.packets.handlers.PacketHandler.{PacketReadResult, packetReader}

import scalaz.Scalaz._
import scalaz._

/**
  * @author iRevThis
  */
final class AuthPacketHandler extends PacketHandler {

  override def handlePacket(buf: ByteBuffer): \/[NetworkError, PacketReadResult] = {
    val id = buf.get & 0xFF
    id match {
      case 0xFB => packetReader(Auth.apply).right
      case unknown => UnknownPacket(unknown).left
    }
  }

}
