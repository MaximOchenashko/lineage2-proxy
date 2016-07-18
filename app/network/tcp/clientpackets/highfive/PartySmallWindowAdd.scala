package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{PartyUpdateAction, WebSocketSendableImpl}
import dto.tcp.embeddable.PartyMemberInfo
import model.embedable.Stats
import model.prototypes.{L2Char, L2User}
import network.tcp.clientpackets.highfive.PartySmallWindowAdd.PartySmallWindowAddAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class PartySmallWindowAdd(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    readD
    val memberId = readD
    val memberName = readS
    val memberCurrentCp = readD
    val memberMaxCp = readD
    val memberCurrentHp = readD
    val memberMaxHP = readD
    val memberCurrentMp = readD
    val memberMaxMP = readD
    val memberLevel = readD
    val memberClassId = readD
    readD
    val memberRace = readD
    readD
    readD

    val stats = Stats(memberMaxCp, memberMaxHP, memberMaxMP, memberCurrentCp, memberCurrentHp, memberCurrentMp)
    val partyMemberInfo = PartyMemberInfo(memberName, memberId, memberLevel, memberClassId, stats, memberRace)
    () => actor ! PartySmallWindowAddAction(objId, partyMemberInfo)
  }

}

object PartySmallWindowAdd {
  case class PartySmallWindowAddAction(objectId: Int, member: PartyMemberInfo) extends WebSocketSendableImpl with PartyUpdateAction {

    def update(user: L2User, char: L2Char): Unit = {
      if (user != null && char != null) {
        char.updateChar(member)
        user.party.foreach(_.addPartyMember(char))
      }
    }

    def memberObjId = member.objId
  }
}