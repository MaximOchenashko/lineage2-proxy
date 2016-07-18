package model.embedable

import common.utils.geometry.Point3D
import dto.tcp.embeddable.PartyMemberInfo
import network.tcp.clientpackets.highfive.PartySmallWindowUpdate.PartySmallWindowUpdateAction
import network.tcp.clientpackets.highfive.StatusUpdate.StatusUpdateAction
import play.api.libs.json.{Format, Json}

/**
  * @author iRevThis
  */
class Embeddable

case class AbnormalEffects(abnormalEffects: Int, abnormalEffects2: Int = 0, abnormalEffects3: Int = 0)

case class Stats(maxCp: Int = 0, maxHp: Int = 0, maxMp: Int = 0,
                 currentCp: Int = 0, currentHp: Int = 0, currentMp: Int = 0,
                 karma: Int = 0, pvpFlag: Int = 0, curLoad: Int = 0, maxLoad: Int = 0) {

  def update(stats: PartySmallWindowUpdateAction): Stats = {
    update(stats.member)
  }

  def update(stats: PartyMemberInfo): Stats = {
    update(stats.stats)
  }

  def update(stats: Stats): Stats = {
    this.copy(currentCp = stats.currentCp, maxCp = stats.maxCp, currentHp = stats.currentHp,
      maxHp = stats.maxHp, currentMp = stats.currentMp, maxMp = stats.maxMp)
  }

  def update(container: StatusUpdateAction): Stats = {
    container.attributes.foldLeft(this) {
      case (stats, att) =>
        att.id match {
          case 0x09 => stats.copy(currentHp = att.value)
          case 0x0a => stats.copy(maxHp = att.value)
          case 0x0b => stats.copy(currentMp = att.value)
          case 0x0c => stats.copy(maxMp = att.value)
          case 0x21 => stats.copy(currentCp = att.value)
          case 0x22 => stats.copy(maxCp = att.value)
          case 0x1b => stats.copy(karma = att.value)
          case 0x1a => stats.copy(pvpFlag = att.value) //pvp flag
          case 0x0e => stats.copy(curLoad = att.value) //cur load
          case 0x0f => stats.copy(maxLoad = att.value) //max load
          case _ => stats
        }
    }
  }

}

case class CharParams(pAtkSpd: Int, mAtkSpd: Int, runSpd: Int,
                      walkSpd: Int, swimSpd: Int, flRunSpd: Int,
                      flWalkSpd: Int, flyRunSpd: Int, flyWalkSpd: Int,
                      speedMove: Float, speedAttack: Float, colRad: Float,
                      colHei: Float)

case class Appearance(sex: Int, race: Int, face: Int,
                      hairStyle: Int, hairColor: Int, nameColor: Int,
                      plgClass: Int, plgType: Int, titleColor: Int,
                      cwLevel: Int, clanRepScore: Int, transform: Int,
                      agathion: Int)

case class ClanInfo(clanId: Int, allyId: Int, clanCrestId: Int,
                    allyCrestId: Int, largeClanCrestId: Int, clanBoatObjId: Int)

case class FishingLocation(x: Int, y: Int, z: Int) extends Point3D

case class CharAdditionInfo(hero: Boolean, noble: Boolean, fishing: Boolean,
                            sitting: Boolean, running: Boolean, inCombat: Boolean,
                            isFlying: Boolean, isPartyLeader: Boolean)

case class MountInfo(mountType: Int, mountId: Int)

case class EtcCharInfo(recommendationHave: Int, protectedStore: Int,
                       enchant: Int, teamOrdinal: Int, paperDolls: Array[Array[Int]])

case class HennaInfo(str: Int, dex: Int, con: Int, int: Int, wit: Int, men: Int)

case class RealStatsInfo(pAtk: Int, pDef: Int, evasion: Int,
                         accuracy: Int, critical: Int, mAtk: Int,
                         mDef: Int)

case class ElementInfo(attackElementId: Int, attackElementValue: Int,
                       defenseFire: Int, defenseWater: Int, defenceWind: Int,
                       defenceEarth: Int, defenceHoly: Int, defenceUnholy: Int)

case class EtcUserInfo(sp: Int, weaponFlag: Int,
                       pkKills: Int, pvpKills: Int, exp: Long, expPercent: Long,
                       talismans: Int, cloakOpen: Int, fame: Int, allowMap: Int,
                       vitality: Int, relation: Int, gmCommands: Int, canCrystallize: Boolean,
                       partyRoom: Int, clanPrivileges: Int, recLeft: Int,
                       inventoryLimit: Int, specialEffects: Int)

object Stats {
  implicit val format: Format[Stats] = Json.format[Stats]
}

object CharAdditionInfo {
  implicit val format: Format[CharAdditionInfo] = Json.format[CharAdditionInfo]
}

object RealStatsInfo {
  implicit val format: Format[RealStatsInfo] = Json.format[RealStatsInfo]
}

object EtcUserInfo {
  implicit val format: Format[EtcUserInfo] = Json.format[EtcUserInfo]
}






