package model.prototypes

import common.utils.DataStorage
import common.utils.geometry.Location
import dto.tcp.embeddable.StatusUpdateAttribute
import model.embedable.{AbnormalEffects, CharParams, ClanInfo, Stats}
import model.{L2Creature, L2Effect}

import scala.collection.mutable

/**
 * @author iRevThis
 */
case class L2Pet(objectId: Int,
                 title: String,
                 stats: Stats,
                 dead: Boolean,
                 effects: mutable.Buffer[L2Effect],
                 abnormalEffects: AbnormalEffects,
                 location: Location,
                 npcId: Int,
                 charParams: CharParams,
                 showName: Boolean,
                 showTitle: Boolean,
                 nameNpcString: Int,
                 titleNpcString: Int,
                 clanInfo: ClanInfo,
                 attackable: Boolean,
                 inCombat: Boolean,
                 isFlying: Boolean,
                 enchantEffect: Int,
                 team: Int,
                 fed: Int) extends L2Creature {

  val objectType = 0x03

  val name = DataStorage.npcsData.find(_.id == npcId).map(_.name).getOrElse("Undefined")

  def updateDead(dead: Boolean): L2Pet = {
    this.copy(dead = dead)
  }

  def updateLocation(loc: Location): L2Pet = {
    this.copy(location = loc)
  }

  def updateCreature(attributes: Seq[StatusUpdateAttribute]): L2Pet = {
    this.copy() //todo
  }
}
