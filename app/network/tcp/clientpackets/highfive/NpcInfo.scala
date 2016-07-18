package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.geometry.Location
import model.embedable._
import model.prototypes.L2Npc
import network.tcp.packets.SHexPacket

import scala.collection.mutable.ArrayBuffer

/**
 * @author iRevThis
 */
case class NpcInfo(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    //TODO 2 bytes remaining wtf?!
    val objId: Int = readD
    val npcId: Int = readD - 1000000
    val attackable: Boolean = readD == 1
    val x: Int = readD
    val y: Int = readD
    val z: Int = readD
    val h: Int = readD
    readD
    val pAtkSpeed: Int = readD
    val mAtkSpeed: Int = readD

    val runSpd: Int = readD
    val walkSpd: Int = readD

    readD
    readD

    val flRunSpd: Int = readD
    val flWalkSpd: Int = readD

    val flyRunSpd: Int = readD
    val flyWalkSpd: Int = readD

    val constVal: Double = readF
    val pAtkSpd: Double = readF
    val colRad: Double = readF
    val colHei: Double = readF

    val rHand: Int = readD
    readD
    val lHand: Int = readD
    val isNameAbove: Boolean = readC == 1
    val running: Boolean = readC == 1

    val inCombat: Boolean = readC == 1
    val dead: Boolean = readC == 1
    val showSpawnAnim: Boolean = readC == 1

    val nameNpcString: Int = readD
    val name: String = readS
    val titleNpcString: Int = readD
    val title: String = readS

    val titleColor: Int = readD
    val pvpFlag: Int = readD

    val karma: Int = readD
    val abnormalEffect: Int = readD
    val clanId: Int = readD
    val clanCrestId: Int = readD
    val allyId: Int = readD
    val allyCrestId: Int = readD

    val isFlying: Boolean = readC == 2
    val team: Int = readC
    val currColRad: Double = readF
    val currColHei: Double = readF
    val enchantEffect: Int = readD

    readD
    readD

    val formId: Int = readD
    val showName: Boolean = readC == 1
    val showTitle: Boolean = readC == 1

    val abnormalEffect2: Int = readD
    val state: Int = readD

    val stats = Stats(karma = karma, pvpFlag = pvpFlag)
    val location = Location(x, y, z, h)
    val abnormalEffects = AbnormalEffects(abnormalEffect, abnormalEffect2)
    val charParams = CharParams(pAtkSpeed, mAtkSpeed, runSpd, walkSpd, -1, flRunSpd, flWalkSpd,
      flyRunSpd, flyWalkSpd, -1, -1, colRad.toFloat, colHei.toFloat)
    val clanInfo = ClanInfo(clanId, allyId, clanCrestId, allyCrestId, -1, -1)

    val l2Npc = L2Npc(objId, title, stats, dead, ArrayBuffer.empty, abnormalEffects, location, npcId, charParams, showName, showTitle,
      nameNpcString, titleNpcString, clanInfo, attackable, inCombat, isFlying, enchantEffect, team)

    () => actor ! l2Npc
  }

}
