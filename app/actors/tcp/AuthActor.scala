package actors.tcp

import java.nio.ByteBuffer

import actors.tcp.AuthActor._
import actors.tcp.TcpServerActor.BindNewConnection
import akka.actor.{Actor, ActorRef, Props}
import akka.io.Tcp
import model.prototypes.L2User
import network.config.ConnectionConfig
import network.tcp.auth.AuthPacketHandler
import network.tcp.base.ConnectionType
import network.tcp.packets.enums.PacketVersion
import network.tcp.packets.handlers.PacketHandler

/**
  * @author iRevThis
  */
//todo connection timeout
final class AuthActor(remoteConnect: ActorRef, tcpManager: ActorRef, cfg: ConnectionConfig) extends Actor {

  val BufferSize = cfg.bufferSize
  val PacketHeaderSize = cfg.packetHeaderSize

  val packetHandler = new AuthPacketHandler
  val buffer = ByteBuffer allocate BufferSize

  override def receive: Receive = {
    case Tcp.Received(data) =>
      buffer.put(data.toArray)
      Stream.continually(tryReadPacket).takeWhile(_ == true).force

    case a: AuthInfo =>
      tcpManager ! BindNewConnection(remoteConnect, self, a)

    case RegisterTcpHandler(handler) =>
      context.become(connected(handler))

    case _: Tcp.ConnectionClosed =>
      context.system stop self
  }

  //todo
  def connected(tcpHandler: ActorRef): Receive = {
    case t: Any => tcpHandler forward t
  }

  /**
    * Try parse packet from current buffer.
    * If buffer remaining bytes < data header(2 bytes) - false
    * If buffer remaining bytes < packet size - false
    * Otherwise - true
    */
  private def tryReadPacket: Boolean = {
    buffer.position < PacketHeaderSize match {
      case true =>
        false

      case false =>
        val pos = buffer.position
        buffer.position(0)
        val packetSize: Int = (buffer.getShort & 0xFFFF) - PacketHeaderSize
        pos - PacketHeaderSize < packetSize match {
          case false =>
            val packet: ByteBuffer = ByteBuffer.allocate(packetSize)
            val packetData: Array[Byte] = new Array[Byte](packetSize)
            buffer.get(packetData, 0, packetSize)
            packet.put(packetData)
            val bufferData = buffer.slice().array()
            buffer.clear
            buffer.put(bufferData)
            buffer.position(pos - (packetSize + PacketHeaderSize))
            parsePacket(packetHandler, packet)
            true

          case true =>
            buffer.position(pos)
            false
        }
    }
  }

  /**
    * Parse packet and execute it
    *
    * @param packetHandler //
    * @param buffer        readable buffer with packet
    */
  private def parsePacket(packetHandler: PacketHandler, buffer: ByteBuffer): Unit = {
    buffer.flip
    if (buffer.hasRemaining) {
      packetHandler.handlePacket(buffer) foreach { f => f(buffer, self).readAndExecute() }
    }
  }
}

object AuthActor {

  sealed trait AuthEvents

  case class AuthInfo(user: L2User, server: String, token: String, packetVersion: PacketVersion, connectionType: ConnectionType) extends AuthEvents

  case class RegisterTcpHandler(actorRef: ActorRef) extends AuthEvents

  def props(remoteConnect: ActorRef, tcpManager: ActorRef, connConfig: ConnectionConfig) =
    Props(classOf[AuthActor], remoteConnect, tcpManager, connConfig)

}
