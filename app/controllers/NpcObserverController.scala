package controllers

import java.sql.Timestamp
import java.util.UUID
import javax.inject.{Inject, Singleton}

import controllers.NpcObserverController.{Location, Npc}
import controllers.base.BaseController
import models.Tables._
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.json.Json
import services.security.AuthService
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

/**
  * @author iRevThis
  */
@Singleton
class NpcObserverController @Inject()(val authService: AuthService,
                                      val dbConfigProvider: DatabaseConfigProvider)
                                     (implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  implicit val locationFormat = Json.format[Location]
  implicit val npcFormat = Json.format[Npc]

  def list = authorized().async { implicit request =>
    val tokenId = UUID.fromString(request.keys.xApiToken.get)
    val query = for {
      n <- NpcsSpawns.applyOData.sortBy(_.updateDate.desc) if n.tokenId === tokenId
    } yield (n.npcId, n.npcName, n.npcIsDead, n.npcX, n.npcY, n.npcZ, n.updateDate)

    db.run(query.page.result zip query.length.result) map {
      case (list, count) => ok(list.map(r => Npc(r._1, r._2, r._3, Location(r._4, r._5, r._6), r._7)), offset, limit, count)
    }
  }

}

object NpcObserverController {
  case class Location(x: Int, y: Int, z: Int)
  case class Npc(npcId: Int, name: String, dead: Boolean, location: Location, update: Timestamp)
}
