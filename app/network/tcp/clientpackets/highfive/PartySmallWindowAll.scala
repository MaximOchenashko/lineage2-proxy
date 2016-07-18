package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{PartyUpdateAction, WebSocketSendableImpl}
import dto.tcp.embeddable.{PartyMemberInfo, PetInfo}
import model.embedable.Stats
import model.prototypes.{L2Char, L2CharBased, L2User}
import network.tcp.clientpackets.highfive.PartySmallWindowAll.PartySmallWindowAllAction
import network.tcp.packets.SHexPacket

import scala.collection.mutable

/**
 * @author iRevThis
 */
case class PartySmallWindowAll(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val leaderObjId = readD
    val loot = readD

    val membersSize = readD

    val members = Range(0, membersSize).map { _ =>
      val memberObjectId: Int = readD
      val memberName: String = readS
      val memberCurrentCp: Int = readD
      val memberMaxCp: Int = readD
      val memberCurrentHp: Int = readD
      val memberMaxHP: Int = readD
      val memberCurrentMp: Int = readD
      val memberMaxMP: Int = readD
      val memberLevel: Int = readD
      val memberClassId: Int = readD
      readD
      val memberRace: Int = readD
      readD
      readD

      val petObjectId: Int = readD

      val petInfo = if (petObjectId != 0) {
        val petNpcId: Int = readD
        val petName: String = readS
        val petCurrentHp: Int = readD
        val petMaxHp: Int = readD
        val petCurrentMp: Int = readD
        val petMaxMp: Int = readD
        val petLevel: Int = readD
        Some(PetInfo(petObjectId, petNpcId, petName, petCurrentHp, petMaxHp, petCurrentMp, petMaxMp, petLevel))
      } else {
        None
      }

      PartyMemberInfo(memberName, memberObjectId, memberLevel, memberClassId,
        Stats(memberMaxCp, memberMaxHP, memberMaxMP, memberCurrentCp, memberCurrentHp, memberCurrentMp),
        memberRace, petInfo)
    }.toBuffer

    () => actor ! PartySmallWindowAllAction(leaderObjId, loot, members)
  }

}

object PartySmallWindowAll {
  case class PartySmallWindowAllAction(leaderObjId: Int, loot: Int, members: mutable.Buffer[PartyMemberInfo]) extends WebSocketSendableImpl with PartyUpdateAction {

    def update(user: L2User, pLeader: Option[L2CharBased], chars: mutable.Buffer[L2Char]): Unit = {
      pLeader foreach { leader =>
        user.createParty(leader, loot, chars)
        user.party.foreach(_.members.foreach(c => c.updateChar(members.find(_.objId == c.objectId).get)))
      }
    }
  }
}
