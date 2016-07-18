package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.geometry.Location
import model.embedable._
import model.prototypes.L2Char
import network.tcp.packets.SHexPacket

import scala.collection.mutable.ArrayBuffer

/**
 * @author iRevThis
 */
case class CharInfo(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val x: Int = readD
    val y: Int = readD
    val z: Int = readD
    val clanBoatObjId: Int = readD
    val objId: Int = readD
    val name: String = readS
    val race: Int = readD
    val sex: Int = readD
    val baseClass: Int = readD

    val inv = Array(Range(0, 21).map(_ => readD).toArray, Range(0, 21).map(_ => readD).toArray)

    readD
    readD

    val pvpFlag: Int = readD
    val karma: Int = readD
    val mAtkSpd: Int = readD
    val pAtkSpd: Int = readD
    readD
    val runSpd: Int = readD
    val walkSpd: Int = readD
    val swimSpd: Int = readD
    readD
    val flRunSpd: Int = readD
    val flWalkSpd: Int = readD
    val flyRunSpd: Int = readD
    val flyWalkSpd: Int = readD

    val speedMove: Float = readF.toFloat
    val speedAttack: Float = readF.toFloat
    val colRad: Float = readF.toFloat
    val colHei: Float = readF.toFloat
    val hairStyle: Int = readD
    val hairColor: Int = readD
    val face: Int = readD
    val title: String = readS
    val clanId: Int = readD
    val clanCrestId: Int = readD
    val allyId: Int = readD
    val allyCrestId: Int = readD
    val sit: Boolean = readC == 1
    val run: Boolean = readC == 1
    val combat: Boolean = readC == 1
    val dead: Boolean = readC == 1
    readC
    val mountType: Int = readC
    val privateStore: Int = readC
    val cubicLength: Int = readH

    val cubics = if (cubicLength > -1) Range(0, cubicLength).map(_ => readH).toSet else Set.empty[Int]

    val isPartyLeader: Boolean = readC == 1
    val abnormalEffect: Int = readD
    val isFlying: Boolean = readC == 1
    val recHave: Int = readH
    val mountId: Int = readD
    val classId: Int = readD
    readD
    val enchant: Int = readC
    val teamOrdinal: Int = readC
    val largeClanCrestId: Int = readD
    val noble: Boolean = readC == 1
    val hero: Boolean = readC == 1
    val fishing: Boolean = readC == 1
    val fishX: Int = readD
    val fishY: Int = readD
    val fishZ: Int = readD
    val nameColor: Int = readD
    val h: Int = readD
    val plgClass: Int = readD
    val plgType: Int = readD
    val titleColor: Int = readD
    val cwLevel: Int = readD
    val clanRepScore: Int = readD
    val transform: Int = readD
    val agathion: Int = readD
    readD
    val abnormalEffect2: Int = readD

    val stats = Stats(karma = karma, pvpFlag = pvpFlag)
    val abnormalEffects = AbnormalEffects(abnormalEffect, abnormalEffect2)
    val charParams = new CharParams(pAtkSpd, mAtkSpd, runSpd, walkSpd, swimSpd, flRunSpd, flWalkSpd,
      flyRunSpd, flyWalkSpd, speedMove, speedAttack, colRad, colHei)
    val location = new Location(x, y, z, h)
    val fishingLocation = new FishingLocation(fishX, fishY, fishZ)
    val charAdditionInfo = CharAdditionInfo(hero, noble, fishing, sit, run, combat, isFlying, isPartyLeader)
    val mountInfo = new MountInfo(mountType, mountId)
    val etcCharInfo = new EtcCharInfo(recHave, privateStore, enchant, teamOrdinal, inv)
    val appearance = new Appearance(sex, race, face, hairColor, hairStyle, nameColor, plgClass, plgType,
      titleColor, cwLevel, clanRepScore, transform, agathion)
    val clanInfo = new ClanInfo(clanId, allyId, clanCrestId, allyCrestId, largeClanCrestId, clanBoatObjId)

    val l2Char = L2Char(objId, name, title, stats, dead, abnormalEffects, location, ArrayBuffer.empty, appearance, clanInfo, fishingLocation,
      mountInfo, etcCharInfo, charParams, charAdditionInfo, classId, baseClass, 0, cubics)

    () => actor ! l2Char
  }

}
