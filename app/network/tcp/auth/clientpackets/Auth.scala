package network.tcp.auth.clientpackets

import java.nio.ByteBuffer

import actors.tcp.AuthActor.AuthInfo
import akka.actor.ActorRef
import network.tcp.base.ConnectionType
import network.tcp.clientpackets.highfive.UserInfo
import network.tcp.packets.ClientPacket
import network.tcp.packets.enums.PacketVersion
import play.api.Logger

/**
  * @author iRevThis
  */
case class Auth(buffer: ByteBuffer, actor: ActorRef) extends ClientPacket {

  override protected def readImpl: () => Unit = {
    val token = readS
    val server = readS
    val packetVersionOrdinal = readD
    val connectionTypeOrdinal = readD
    val hexUserInfo = buffer.slice()

    val packetVersion = PacketVersion.byCode(packetVersionOrdinal).get
    val connectionType = ConnectionType.byCode(connectionTypeOrdinal).get
    val l2User = UserInfo(hexUserInfo, actor).parsePacket

    () => actor ! AuthInfo(l2User, server, token, packetVersion, connectionType)
  }

}

