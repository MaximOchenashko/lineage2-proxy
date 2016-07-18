package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.WebSocketSendableImpl
import network.tcp.clientpackets.highfive.TargetUnselected.TargetUnselectedAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class TargetUnselected(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val targetObjId = readD
    val x = readD
    val y = readD
    val z = readD
    readD

    () => actor ! TargetUnselectedAction(targetObjId)
  }

}

object TargetUnselected {
  case class TargetUnselectedAction(targetObjId: Int) extends WebSocketSendableImpl
}