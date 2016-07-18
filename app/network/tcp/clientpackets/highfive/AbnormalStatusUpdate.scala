package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateType, WebSocketSendableImpl}
import model.L2Effect
import model.prototypes.L2User
import network.tcp.clientpackets.highfive.AbnormalStatusUpdate.AbnormalStatusUpdateAction
import network.tcp.packets.SHexPacket
import play.api.libs.json.{Json, Writes}

/**
 * @author iRevThis
 */
case class AbnormalStatusUpdate(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val effectsCount = readH
    val effects = Range(0, effectsCount).map { f =>
      val skillId = readD
      val dat = readH
      val duration = readD
      L2Effect(skillId, dat, duration)
    }.toSeq

    () => actor ! AbnormalStatusUpdateAction(effects)
  }

}

object AbnormalStatusUpdate {
  case class AbnormalStatusUpdateAction(effects: Seq[L2Effect]) extends WebSocketSendableImpl with UpdateType[L2User] {
    override def objectId: Int = 0

    override def update(obj: L2User): Option[L2User] = {
      Option(obj) foreach (_.updateEffects(effects))
      None
    }
  }

  object AbnormalStatusUpdateAction {
    implicit val format: Writes[AbnormalStatusUpdateAction] = Json.writes[AbnormalStatusUpdateAction]
  }
}
