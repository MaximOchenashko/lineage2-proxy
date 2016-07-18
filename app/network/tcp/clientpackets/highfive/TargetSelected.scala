package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.WebSocketSendableImpl
import network.tcp.clientpackets.highfive.TargetSelected.TargetSelectedAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class TargetSelected(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    val targetObjId = readD
    val x = readD
    val y = readD
    val z = readD
    readD

    () => actor ! TargetSelectedAction(objId, targetObjId)
  }

}

object TargetSelected {
  case class TargetSelectedAction(objectId: Int, targetObjId: Int) extends WebSocketSendableImpl
}