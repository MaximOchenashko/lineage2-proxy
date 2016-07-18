package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{PartyUpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.PartySmallWindowDeleteAll.PartySmallWindowDeleteAllAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class PartySmallWindowDeleteAll(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    () => actor ! PartySmallWindowDeleteAllAction
  }

}

object PartySmallWindowDeleteAll {
  case object PartySmallWindowDeleteAllAction extends WebSocketSendableImpl with PartyUpdateAction
}