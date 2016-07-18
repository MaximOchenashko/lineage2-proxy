package model.prototypes

import common.utils.DataStorage
import common.utils.geometry.Location
import dto.tcp.embeddable.StatusUpdateAttribute
import model.embedable.{AbnormalEffects, CharParams, ClanInfo, Stats}
import model.{L2Creature, L2Effect}
import play.api.libs.json.{Json, JsValue, Writes}

import scala.collection.mutable


/**
 * @author iRevThis
 */
case class L2Npc(objectId: Int,
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
                 team: Int) extends L2Creature {

  val objectType = 0x03

  val name = DataStorage.npcsData.find(_.id == npcId).map(_.name).getOrElse("Undefined")

  def updateDead(dead: Boolean): L2Npc = {
    this.copy(dead = dead)
  }

  def updateLocation(loc: Location): L2Npc = {
    this.copy(location = loc)
  }

  def updateCreature(attributes: Seq[StatusUpdateAttribute]): L2Npc = {
    this.copy() //todo
  }
}

object L2Npc {

  implicit val writes: Writes[L2Npc] = new Writes[L2Npc] {
    override def writes(obj: L2Npc): JsValue = Json.obj(
      "objectId" -> obj.objectId,
      "location" -> obj.location,
      "name" -> obj.name,
      "title" -> obj.title,
      "stats" -> obj.stats,
      "dead" -> obj.dead,
      "effects" -> obj.effects,
      "objectType" -> obj.objectType
      //"party" -> obj.party
      //"summon" -> obj.summon TODO
    )
  }

}

