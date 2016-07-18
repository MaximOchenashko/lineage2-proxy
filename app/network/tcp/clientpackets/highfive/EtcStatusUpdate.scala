package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateType, WebSocketSendableImpl}
import model.L2Effect
import model.prototypes.L2User
import network.tcp.clientpackets.highfive.EtcStatusUpdate._
import network.tcp.packets.SHexPacket

/**
 * @author iRevThis
 */
case class EtcStatusUpdate(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val effects = effectsRefs.indices.map(f => L2Effect(effectsRefs(f), readD, -1)).toSeq
    () => actor ! EtcStatusUpdateAction(effects)
  }

}

object EtcStatusUpdate {
  private val effectsRefs = Array[Int](4271, 4270, 4269, 2468, 6209, 6213, 5041, 5076, 5446)

  case class EtcStatusUpdateAction(effects: Seq[L2Effect]) extends WebSocketSendableImpl with UpdateType[L2User] {
    val objectId = 0

    override def update(obj: L2User): Option[L2User] = {
      Option(obj) foreach (_.updateEtcEffects(effects))
      None
    }
  }
}
