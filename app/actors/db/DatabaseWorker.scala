package actors.db

import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

import actors.db.DatabaseWorker._
import actors.db.types.{BrokerDatabaseWorker, SpawnWatcherWorker}
import akka.actor.{Actor, Props}
import model.prototypes.L2CharBased
import models.Tables._
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.json.Json
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author iRevThis
  */
class DatabaseWorker(val dbConfigProvider: DatabaseConfigProvider) extends Actor
  with BrokerDatabaseWorker
  with SpawnWatcherWorker
  with HasDatabaseConfigProvider[JdbcProfile] {

  override def receive: Receive = brokerReceiver.orElse(spawnWatcherReceive).orElse {
    case SavePlayer(serverId, charInfo) =>
      val playerExists = for {p <- Players if p.serverId === serverId.toId && p.name === charInfo.name} yield p
      db.run(playerExists.result).map(_.headOption) onSuccess {
        case Some(player) =>
          val updateQuery = for {
            p <- Players if p.id === player.id
          } yield (p.lastSeen, p.clanId, p.title, p.baseClass, p.race, p.sex, p.isNoble, p.isHero, p.paperDolls, p.x, p.y, p.z)
          db.run(updateQuery.update(now, Option(charInfo.clanInfo.clanId),
            Option(charInfo.title), charInfo.mainClassId, charInfo.appearance.race, charInfo.appearance.sex,
            charInfo.charAdditionInfo.noble, charInfo.charAdditionInfo.hero, Json.toJson(charInfo.etcCharInfo.paperDolls).toString(),
            charInfo.location.x, charInfo.location.y, charInfo.location.z))
        case None =>
          val insertQuery = Players += PlayersRow(randomId, serverId.toId, charInfo.objectId, now, now,
            charInfo.name, Option(charInfo.title), charInfo.appearance.race,
            charInfo.appearance.sex, charInfo.mainClassId,
            Option(charInfo.clanInfo.clanId), charInfo.charAdditionInfo.noble, charInfo.charAdditionInfo.hero,
            Json.toJson(charInfo.etcCharInfo.paperDolls).toString(), charInfo.location.x, charInfo.location.y, charInfo.location.z)
          db.run(insertQuery) /* onComplete {
            case Success(_) =>
            case Failure(e) => Logger.error("Cant save player", e)
          }*/
      }

    /*case SaveUserNotification(userId, telegram, items: Seq[NotificationItemInfo]) =>
      val queries = items map {
        case NotificationItemInfo(id, maxPrice, count, enchant, attType, attVal) => BrokerNotifications += BrokerNotificationsRow(UUID.randomUUID, now, now, userId, id, maxPrice, count, enchant, attType, attVal)
      }

      db.run(DBIO.sequence(queries).transactionally) onComplete {
        case Success(_) =>
        case Failure(e) =>
      }*/
  }

  protected def now = Timestamp.from(Instant.now)

  protected def randomId = UUID.randomUUID

  protected implicit class String2Uuid(str: String) {
    def toId: UUID = UUID.fromString(str)
  }

}

object DatabaseWorker {
  def props(dbConfigProvider: DatabaseConfigProvider) = Props(classOf[DatabaseWorker], dbConfigProvider)

  private[db] trait DatabaseWorkerMessage

  case class SavePlayer(serverId: String, charInfo: L2CharBased) extends DatabaseWorkerMessage

  //Item broker
  case class NotificationItemInfo(id: Int, maxPrice: Long, count: Int, enchant: Int, attributeType: Int, attributeValue: Int)

  case class SaveUserNotification(userId: UUID, telegram: Int, items: Seq[NotificationItemInfo]) extends DatabaseWorkerMessage

}
