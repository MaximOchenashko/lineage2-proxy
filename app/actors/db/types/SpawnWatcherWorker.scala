package actors.db.types

import java.sql.Timestamp
import java.time.{LocalDateTime, ZoneOffset}
import java.util.UUID

import actors.db.DatabaseWorker
import actors.db.DatabaseWorker.DatabaseWorkerMessage
import actors.db.types.SpawnWatcherWorker._
import common.utils.geometry.Location
import models.Tables._
import network.tcp.clientpackets.highfive.DropItem.DropItemAction
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * @author iRevThis
  */
trait SpawnWatcherWorker {
  handler: DatabaseWorker =>

  def spawnWatcherReceive: Receive = {
    case SaveNpc(tokenId, userName, userLoc, npcInfo) =>
      val tokenUuid = UUID.fromString(tokenId)
      val npcExists = for {n <- NpcsSpawns if n.tokenId === tokenUuid && n.npcObjectId === npcInfo.objectId} yield n
      lazy val loc = npcInfo.location
      db.run(npcExists.result).map(_.headOption) onSuccess {
        case Some(npc) =>
          val updateQuery = for {n <- NpcsSpawns if n.id === npc.id} yield (n.updateDate, n.npcX, n.npcY, n.npcZ, n.npcIsDead)
          db.run(updateQuery.update(now, loc.x, loc.y, loc.z, npcInfo.dead))
        case None =>
          val insertQuery = NpcsSpawns += NpcsSpawnsRow(UUID.randomUUID, now, now, tokenUuid, userName, userLoc.x,
            userLoc.y, userLoc.z, npcInfo.objectId, npcInfo.npcId, npcInfo.npcLevel, npcInfo.name,
            npcInfo.dead, loc.x, loc.y, loc.z)
          db.run(insertQuery)
      }
    case SaveNpcDeath(tokenId, objectId) =>
      val tokenUuid = UUID.fromString(tokenId)
      val updateQuery = for {n <- NpcsSpawns if n.tokenId === tokenUuid && n.npcObjectId === objectId} yield (n.updateDate, n.npcIsDead)
      db.run(updateQuery.update(now, true))
    case SaveDroppedItem(tokenId, DropItemAction(dropperId, itemObjId, itemId, loc, stackable, count)) =>
      val tokenUuid = UUID.fromString(tokenId)
      val insertQuery = DroppedItems += DroppedItemsRow(UUID.randomUUID, now, tokenUuid, dropperId, itemObjId,
        itemId, loc.x, loc.y, loc.z, stackable, count)
      db.run(insertQuery)
    case LoadLatestNpcsIds(tokenId) =>
      val tokenUuid = UUID.fromString(tokenId)
      val time = Timestamp.from(LocalDateTime.now().minusMinutes(10).toInstant(ZoneOffset.UTC))
      val query = for {n <- NpcsSpawns if n.tokenId === tokenUuid && n.updateDate >= time && !n.npcIsDead} yield n.npcObjectId
      sender ! db.run(query.result)
  }

}

object SpawnWatcherWorker {
  case class SaveNpc(tokenId: String, userName: String, userLocation: Location, npcInfo: NpcSaveInfo) extends DatabaseWorkerMessage
  case class SaveNpcDeath(tokenId: String, objectId: Int) extends DatabaseWorkerMessage
  case class SaveDroppedItem(tokenId: String, dropItemAction: DropItemAction) extends DatabaseWorkerMessage
  case class LoadLatestNpcsIds(tokenId: String) extends DatabaseWorkerMessage

  case class NpcSaveInfo(objectId: Int, npcId: Int, npcLevel: Int, name: String, dead: Boolean, location: Location)
}
