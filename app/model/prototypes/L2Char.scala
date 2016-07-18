package model.prototypes

import common.utils.geometry.Location
import dto.tcp.embeddable.{PartyMemberInfo, StatusUpdateAttribute}
import model.embedable._
import model.{L2Creature, L2Effect}
import play.api.libs.json.{Json, JsValue, Writes}

import scala.collection.mutable

/**
 * @author iRevThis
 */
case class L2Char(objectId: Int,
                  name: String,
                  title: String,
                  stats: Stats,
                  dead: Boolean,
                  abnormalEffects: AbnormalEffects,
                  /*targetObjId: Int,*/
                  location: Location,
                  effects: mutable.Buffer[L2Effect],
                  appearance: Appearance,
                  clanInfo: ClanInfo,
                  fishingLocation: FishingLocation,
                  mountInfo: MountInfo,
                  etcCharInfo: EtcCharInfo,
                  charParams: CharParams,
                  charAdditionInfo: CharAdditionInfo,
                  classId: Int,
                  mainClassId: Int,
                  level: Int,
                  cubics: Set[Int]
                  /*party: L2Party,*/
                  /*summon: L2Summon*/
                  /*cast: L2Effect*/) extends L2Creature with L2CharBased {

  val objectType = 0x02

  def updateChar(container: PartyMemberInfo): L2Char = {
    this.copy(stats = container.stats, classId = container.classId)
  }

  def updateLocation(loc: Location): L2Char = {
    this.copy(location = loc)
  }

  def updateCreature(attributes: Seq[StatusUpdateAttribute]): L2Char = {
    this.copy() //todo
  }

  def updateDead(dead: Boolean): L2Char = {
    this.copy(dead = dead)
  }

  override def updateEffect(eff: L2Effect) {
    if (effects.contains(eff)) {
      if (eff.level == 0)
        effects -= eff
      else
        effects(effects.indexOf(eff)).updateEffect(eff)
    }
  }

  override def updateEffects(effs: Seq[L2Effect]) {
    effs.foreach(updateEffect)
  }

}


object L2Char {

  implicit val writes: Writes[L2Char] = new Writes[L2Char] {
    override def writes(obj: L2Char): JsValue = Json.obj(
      "objectId" -> obj.objectId,
      "location" -> obj.location,
      "name" -> obj.name,
      "title" -> obj.title,
      "stats" -> obj.stats,
      "dead" -> obj.dead,
      "effects" -> obj.effects,
      "charAdditionInfo" -> obj.charAdditionInfo,
      "classId" -> obj.classId,
      "mainClassId" -> obj.mainClassId,
      "level" -> obj.level,
      "objectType" -> obj.objectType
      //"party" -> obj.party
      //"summon" -> obj.summon TODO
    )
  }

}