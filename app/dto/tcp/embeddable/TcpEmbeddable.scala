package dto.tcp.embeddable

import model.L2Item
import model.embedable.Stats

/**
 * @author iRevThis
 */
class TcpEmbeddable

case class StatusUpdateAttribute(id: Int, value: Int)

case class ItemInfo(updateType: Int, item: L2Item)

case class PartyMemberInfo(name: String, objId: Int, level: Int,
                           classId: Int, stats: Stats, raceId: Int = 0,
                           pet: Option[PetInfo] = None)

case class PetInfo(id: Int, npcId: Int, petName: String,
                   curHp: Int, maxHp: Int, curMp: Int,
                   maxMp: Int, level: Int)
