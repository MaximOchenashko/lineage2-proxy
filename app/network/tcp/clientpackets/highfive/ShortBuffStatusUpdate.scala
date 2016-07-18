package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateType, WebSocketSendableImpl}
import model.L2Effect
import model.prototypes.L2User
import network.tcp.clientpackets.highfive.ShortBuffStatusUpdate.ShortBuffStatusUpdateAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class ShortBuffStatusUpdate(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val skillId = readD
    val skillLevel = readD
    val skillDuration = readD

    () => actor ! ShortBuffStatusUpdateAction(skillId, skillLevel, skillDuration)
  }

}

object ShortBuffStatusUpdate {
  case class ShortBuffStatusUpdateAction(skillId: Int, skillLevel: Int, skillDuration: Int) extends WebSocketSendableImpl with UpdateType[L2User] {
    val objectId: Int = 0
    val eff = new L2Effect(skillId, skillLevel, skillDuration)

    override def update(obj: L2User): Option[L2User] = Option(obj) map { u => u.updateEffect(eff); u }
  }
}