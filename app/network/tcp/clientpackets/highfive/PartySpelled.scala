package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateType, WebSocketSendableImpl}
import model.L2Effect
import model.prototypes.L2Char
import network.tcp.clientpackets.highfive.PartySpelled.PartySpelledDTO
import network.tcp.packets.SHexPacket

import scala.collection.mutable

/**
 * @author iRevThis
 */
case class PartySpelled(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val varType = readD
    val objId = readD
    val effectsCount = readD

    val effects = Range(0, effectsCount).map {_ =>
      val skillId = readD
      val level = readH
      val duration = readD

      L2Effect(skillId, level, duration)
    }.toBuffer

    () => actor ! PartySpelledDTO(objId, varType, effects)
  }

}

object PartySpelled {
  case class PartySpelledDTO(objectId: Int, cType: Int, effects: mutable.Buffer[L2Effect]) extends WebSocketSendableImpl with UpdateType[L2Char] {
    override def update(obj: L2Char): Option[L2Char] = Option(obj) map (_.copy(effects = effects))
  }
}
