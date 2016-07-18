package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.geometry.Location
import model.embedable._
import model.prototypes.L2User
import network.tcp.packets.SHexPacket

import scala.collection.mutable.ArrayBuffer

/**
 * @author iRevThis
 */
case class UserInfo(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    () => actor ! parsePacket
  }

  def parsePacket = {
    val x: Int = readD
    val y: Int = readD
    val z: Int = readD
    val clanBoatObjId: Int = readD
    val objId: Int = readD
    val name: String = readS
    val race: Int = readD
    val sex: Int = readD
    val baseClass: Int = readD

    val level: Int = readD
    val exp: Long = readQ
    val expPercent: Long = readQ
    val str: Int = readD
    val dex: Int = readD
    val con: Int = readD
    val int: Int = readD
    val wit: Int = readD
    val men: Int = readD
    val maxHp: Int = readD
    val curHp: Int = readD
    val maxMp: Int = readD
    val curMp: Int = readD
    val sp: Int = readD
    val curLoad: Int = readD
    val maxLoad: Int = readD
    val weaponFlag: Int = readD

    val inv = Array(
      Range(0, 26).map(_ => readD).toArray,
      Range(0, 26).map(_ => readD).toArray,
      Range(0, 26).map(_ => readD).toArray
    )

    val talismans: Int = readD
    val cloakOpen: Int = readD
    val pAtk: Int = readD
    val pAtkSpd: Int = readD
    val pDef: Int = readD
    val evasion: Int = readD
    val accuracy: Int = readD
    val crit: Int = readD
    val mAtk: Int = readD
    val mAtkSpd: Int = readD
    readD
    val mDef: Int = readD
    val pvpFlag: Int = readD
    val karma: Int = readD
    val runSpd: Int = readD
    val walkSpd: Int = readD
    val swimRunSpd: Int = readD
    val swimWalkSpd: Int = readD
    val flRunSpd: Int = readD
    val flWalkSpd: Int = readD
    val flyRunSpd: Int = readD
    val flyWalkSpd: Int = readD
    val speedMove: Float = readF.toFloat
    val speedAttack: Float = readF.toFloat
    val colRad: Long = readQ
    val colHei: Long = readQ
    val hairStyle: Int = readD
    val hairColor: Int = readD
    val face: Int = readD
    val gmCommands: Int = readD
    val title: String = readS
    val clanId: Int = readD
    val clanCrestId: Int = readD
    val allyId: Int = readD
    val allyCrestId: Int = readD

    val relation: Int = readD
    val mountType: Int = readC
    val privateStore: Int = readC
    val canCrystallize: Boolean = readC == 1
    val pkKills: Int = readD
    val pvpKills: Int = readD
    val cubicLength: Int = readH

    val cubics = if (cubicLength > 0) Range(0, cubicLength).map(_ => readH).toSet else Set.empty[Int]

    val partyRoom: Int = readC
    val abnormalEffect: Int = readD
    val isFlying: Boolean = readC == 1
    val clanPrivs: Int = readD
    val recLeft: Int = readH
    val recHave: Int = readH
    val mountId: Int = readD
    val inventoryLimit: Int = readH
    val classId: Int = readD
    val specialEffects: Int = readD
    val maxCp: Int = readD
    val curCp: Int = readD
    val enchant: Int = readC
    val teamOrdinal: Int = readC
    val largeClanCrestId: Int = readD
    val noble: Boolean = readC == 1
    val hero: Boolean = readC == 1
    readC
    val fishX: Int = readD
    val fishY: Int = readD
    val fishZ: Int = readD
    val nameColor: Int = readD
    val running: Boolean = readC == 1
    val plgClass: Int = readD
    val plgType: Int = readD
    val titleColor: Int = readD
    val cwLevel: Int = readD
    val transform: Int = readD
    val attackElementId: Int = readH
    val attackElementValue: Int = readH
    val defenseFire: Int = readH
    val defenseWater: Int = readH
    val defenceWind: Int = readH
    val defenceEarth: Int = readH
    val defenceHoly: Int = readH
    val defenceUnholy: Int = readH
    val agathion: Int = readD
    val fame: Int = readD
    val allowMap: Int = readD
    val vitality: Int = readD
    val abnormalEffect2: Int = readD

    val stats = Stats(maxCp, maxHp, maxMp, curCp, curHp, curMp, karma, pvpFlag, curLoad, maxLoad)
    val abnormalEffects = AbnormalEffects(abnormalEffect, abnormalEffect2)
    val location = Location(x, y, z)
    val charParams = CharParams(pAtkSpd, mAtkSpd, runSpd, walkSpd, swimRunSpd, flRunSpd, flWalkSpd,
      flyRunSpd, flyWalkSpd, speedMove, speedAttack, colRad, colHei)
    val fishingLocation = FishingLocation(fishX, fishY, fishZ)
    val charAdditionInfo = CharAdditionInfo(hero, noble, fishing = false, sitting = false, running,
      inCombat = false, isFlying, isPartyLeader = false)
    val mountInfo = MountInfo(mountType, mountId)
    val etcCharInfo = EtcCharInfo(recHave, privateStore, enchant, teamOrdinal, inv)
    val appearance = Appearance(sex, race, face, hairColor, hairStyle, nameColor, plgClass, plgType,
      titleColor, cwLevel, 0, transform, agathion)
    val clanInfo = ClanInfo(clanId, allyId, clanCrestId, allyCrestId, largeClanCrestId, clanBoatObjId)
    val hennaInfo = HennaInfo(str, dex, con, int, wit, men)
    val realStatsInfo = RealStatsInfo(pAtk, pDef, evasion, accuracy, crit, mAtk, mDef)
    val elementInfo = ElementInfo(attackElementId, attackElementValue, defenseFire, defenseWater,
      defenceWind, defenceEarth, defenceHoly, defenceUnholy)
    val etcUserInfo = EtcUserInfo(sp, weaponFlag, pkKills, pvpKills, exp, expPercent, talismans,
      cloakOpen, fame, allowMap, vitality, relation, gmCommands, canCrystallize,
      partyRoom, clanPrivs, recLeft, inventoryLimit, specialEffects)

    L2User(objId, name, title, stats, dead = false, ArrayBuffer.empty, abnormalEffects, location, appearance, clanInfo,
      fishingLocation, mountInfo, etcCharInfo, charParams, charAdditionInfo, classId, baseClass, level, cubics, None,
      hennaInfo, realStatsInfo, elementInfo, etcUserInfo)
  }
}
