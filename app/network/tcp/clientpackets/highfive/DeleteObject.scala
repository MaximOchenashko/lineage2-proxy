package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.DeleteObject.DeleteObjectAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class DeleteObject(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objectId = readD
    () => actor ! DeleteObjectAction(objectId)
  }

}

object DeleteObject {
  case class DeleteObjectAction(objectId: Int) extends WebSocketSendableImpl with UpdateAction
}
