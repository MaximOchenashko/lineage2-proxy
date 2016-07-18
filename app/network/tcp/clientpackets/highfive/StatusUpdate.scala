package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateAction, WebSocketSendableImpl}
import dto.tcp.embeddable.StatusUpdateAttribute
import network.tcp.clientpackets.highfive.StatusUpdate.StatusUpdateAction
import network.tcp.packets.SHexPacket

/**
 * @author iRevThis
 */
case class StatusUpdate(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    val argsSize = readD
    val attributes = Range(0, argsSize).map(_ => StatusUpdateAttribute(readD, readD))

    () => actor ! StatusUpdateAction(objId, attributes)
  }


}

object StatusUpdate {
  case class StatusUpdateAction(objectId: Int, attributes: Seq[StatusUpdateAttribute]) extends WebSocketSendableImpl with UpdateAction
}
