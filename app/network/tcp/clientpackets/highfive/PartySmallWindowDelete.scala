package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{PartyUpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.PartySmallWindowDelete.PartySmallWindowDeleteAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class PartySmallWindowDelete(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId: Int = readD
    val name: String = readS

    () => actor ! PartySmallWindowDeleteAction(objId, name)
  }

}

object PartySmallWindowDelete {
  case class PartySmallWindowDeleteAction(objId: Int, name: String) extends WebSocketSendableImpl with PartyUpdateAction
}