package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{PartyUpdateAction, WebSocketSendableImpl}
import dto.tcp.embeddable.PartyMemberInfo
import model.embedable.Stats
import network.tcp.clientpackets.highfive.PartySmallWindowUpdate.PartySmallWindowUpdateAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class PartySmallWindowUpdate(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    val name = readS
    val currentCp = readD
    val maxCp = readD
    val currentHp = readD
    val maxHp = readD
    val currentMp = readD
    val maxMp = readD
    val level = readD
    val classId = readD

    val stats = Stats(maxCp, maxHp, maxMp, currentCp, currentHp, currentMp)
    val memberInfo = PartyMemberInfo(name, objId, level, classId, stats)
    () => actor ! PartySmallWindowUpdateAction(objId, memberInfo)
  }

}

object PartySmallWindowUpdate {
  case class PartySmallWindowUpdateAction(objectId: Int, member: PartyMemberInfo) extends WebSocketSendableImpl with PartyUpdateAction
}