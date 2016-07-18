package model.prototypes

import common.utils.geometry.Location
import dto.tcp.embeddable.{ItemInfo, StatusUpdateAttribute}
import model.embedable._
import model.{L2Effect, L2Item, L2Party}
import play.Logger
import play.api.libs.json.{JsValue, Json, Writes}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * @author iRevThis
  */
case class L2User(objectId: Int,
                  name: String,
                  title: String,
                  stats: Stats,
                  dead: Boolean,
                  effects: mutable.Buffer[L2Effect],
                  abnormalEffects: AbnormalEffects,
                  /*targetObjId: Int,*/
                  location: Location,
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
                  cubics: Set[Int],
                  party: Option[L2Party],
                  hennaInfo: HennaInfo,
                  realStatsInfo: RealStatsInfo,
                  elementInfo: ElementInfo,
                  etcUserInfo: EtcUserInfo,
                  inventory: mutable.Buffer[L2Item] = ArrayBuffer.empty) extends L2CharBased {

  val objectType = 0x01

  def updateCreature(attributes: Seq[StatusUpdateAttribute]): L2User = {
    this.copy() //todo
  }

  def updateDead(dead: Boolean): L2User = {
    this.copy(dead = dead)
  }

  def updateLocation(loc: Location): L2User = {
    this.copy(location = loc)
  }

  def updateEtcEffects(effects: Seq[L2Effect]) {
    effects.foreach(updateEffect)
  }

  def update(user: L2User): L2User = {
    this.updateEffects(user.effects)
    this.copy(location = user.location, stats = user.stats)
  }

  def createParty(leader: L2CharBased, loot: Int, members: mutable.Buffer[L2Char]): L2User = {
    this.copy(party = Some(new L2Party(leader, loot, members)))
  }

  def removeParty(): L2User = this.copy(party = None)

  def getItem(p: L2Item => Boolean): Option[L2Item] = {
    inventory.find(p)
  }

  def getItemCount(p: L2Item => Boolean): Long = {
    getItem(p).map(_.count).getOrElse(0L)
  }

  def updateInventory(items: Seq[ItemInfo]) {
    for (inf: ItemInfo <- items) {
      inf.updateType match {
        case 1 => inventory += inf.item
        case 2 => if (inf.item.stackable) { inventory -= inf.item }; inventory += inf.item
        case 3 => inventory -= inf.item
        case _ => Logger.info("Unhandled inventory update")
      }
    }
  }

}

object L2User {

  implicit val serializer: Writes[L2User] = new Writes[L2User] {
    override def writes(obj: L2User): JsValue = Json.obj(
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
      "objectType" -> obj.objectType,
      //"party" -> obj.party,
      //"inventory" -> obj.inventory, TODO
      "etcUserInfo" -> obj.etcUserInfo,
      "realStatsInfo" -> obj.realStatsInfo
    )
  }
}
