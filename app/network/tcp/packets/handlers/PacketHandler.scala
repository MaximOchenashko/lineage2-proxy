package network.tcp.packets.handlers

import java.nio.ByteBuffer

import akka.actor.ActorRef
import network.errors.NetworkError
import network.tcp.packets.base.ReceivablePacket
import network.tcp.packets.handlers.PacketHandler.PacketReadResult

import scalaz.{Reader, \/}

/**
  * @author iRevThis
  */
trait PacketHandler {

  def handlePacket(buf: ByteBuffer): \/[NetworkError, PacketReadResult]

}

object PacketHandler {

  type PacketReadResult = Reader[(ByteBuffer, ActorRef), ReceivablePacket]

  private[network] def packetReader(constructor: (ByteBuffer, ActorRef) => ReceivablePacket): PacketReadResult =
    Reader { case (buffer, actor) => constructor(buffer, actor) }
}
