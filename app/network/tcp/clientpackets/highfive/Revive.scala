package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.Revive.ReviveAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class Revive(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    () => actor ! ReviveAction(objId)
  }

}

object Revive {
  case class ReviveAction(objectId: Int) extends WebSocketSendableImpl with UpdateAction
}