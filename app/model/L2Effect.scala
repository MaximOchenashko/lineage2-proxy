package model

import common.utils.DataStorage
import play.api.libs.json.{JsValue, Json, Writes}

/**
 * @author iRevThis
 */
class L2Effect(var skillId: Int, var level: Int, var duration: Int) {
  lazy val skillData = DataStorage.skillsData.find(_.id == skillId)

  lazy val displayId: Int = skillData.map(_.displayId).getOrElse(0)
  lazy val isMagic: Int = skillData.map(_.isMagic).getOrElse(0)
  lazy val isNegative: Int = skillData.map(_.isNegative).getOrElse(0)
  lazy val name: String = skillData.map(_.name).getOrElse("undefined")

  def updateEffect(skillId: Int, level: Int, duration: Int) {
    this.skillId = skillId
    this.level = level
    this.duration = duration
  }

  def updateEffect(eff: L2Effect): Unit = {
    if (eff.skillId == this.skillId)
      updateEffect(eff.skillId, eff.level, eff.duration)
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: L2Effect => that.skillId == this.skillId
    case _ => false
  }

}

object L2Effect {
  def apply(skillId: Int, level: Int, duration: Int) = new L2Effect(skillId, level, duration)

  implicit val serializer = new Writes[L2Effect] {
    override def writes(obj: L2Effect): JsValue = Json.obj(
      "skillId" -> obj.skillId,
      "level" -> obj.level,
      "name" -> obj.name,
      "duration" -> obj.duration,
      "displayId" -> obj.displayId,
      "isMagic" -> obj.isMagic,
      "isNegative" -> obj.isNegative
    )
  }
}