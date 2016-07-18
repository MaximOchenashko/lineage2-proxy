package model

import model.prototypes.{L2Char, L2CharBased}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * @author iRevThis
 */
class L2Party(var leader: L2CharBased, var loot: Int, var members: mutable.Buffer[L2Char]) {

  def this() = {
    this(null, 0, ArrayBuffer.empty[L2Char])
  }

  def addPartyMember(char: L2Char): Unit = {
    if (!members.contains(char))
      members += char
  }

  def removePartyMember(objId: Int): Unit = {
    val member = members.find(_.objectId == objId)
    if (member.isDefined)
      members -= member.get
  }

  def removeParty(): Unit = {
    members.clear()
  }

  def getMember(p: L2Char => Boolean) = {
    members.find(p)
  }

}

object L2Party {
 /* implicit val serializer = new Writes[L2Party] {
    override def writes(obj: L2Party): JsValue = Json.obj(
      "leader" -> obj.leader,
      "loot" -> obj.loot,
      "members" -> obj.members
    )
  }*/
}
