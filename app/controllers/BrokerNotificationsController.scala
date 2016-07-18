package controllers

import java.util.UUID
import javax.inject.{Singleton, Inject}

import BrokerNotificationsController._
import controllers.base.BaseController
import models.Tables._
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.json.Json
import services.security.AuthService
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author iRevThis
  */
@Singleton
class BrokerNotificationsController @Inject()(val authService: AuthService,
                                              val dbConfigProvider: DatabaseConfigProvider)
                                             (implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  implicit val itemInfoFormat = Json.format[ItemInfo]
  implicit val itemInfoSaveFormat = Json.format[ItemInfoSave]

  def list = authorized().async { implicit request =>
    val user = request.authInfo

    val query = for {n <- BrokerNotifications.applyOData if n.userId === user.uuid} yield (n.id, n.itemId, n.price, n.count, n.enabled)

    db.run(query.page.result zip query.length.result) map {
      case (items, total) => ok(items.map(ItemInfo.tupled), offset, limit, total)
    }
  }

  def save = authorized().async(parse.json[ItemInfoSave]) { implicit request =>
    val user = request.authInfo
    val dto = request.body

    val query = BrokerNotifications += BrokerNotificationsRow(UUID.randomUUID, now, now, user.uuid,
                  enabled = true, dto.itemId, dto.price, dto.count, dto.enchant, dto.attributeType, dto.attributeValue)

    db.run(query) map {
      case 1 => Created
      case _ => BadRequest
    }
  }

  def changeState(id: UUID, enabled: Boolean) = authorized().async { request =>
    val user = request.authInfo

    val query = for {n <- BrokerNotifications if n.userId === user.uuid && n.id === id} yield n.enabled
    db.run(query.update(enabled)) map {
      case 1 => Ok
      case _ => BadRequest
    }
  }

  def delete(id: UUID) = authorized().async { request =>
    val user = request.authInfo

    val query = for {n <- BrokerNotifications if n.userId === user.uuid && n.id === id} yield n
    db.run(query.delete) map {
      case 1 => NoContent
      case _ => BadRequest
    }
  }

}

object BrokerNotificationsController {
  case class ItemInfo(id: UUID, itemId: Int, price: Option[Long], count: Option[Long], enabled: Boolean)
  case class ItemInfoSave(itemId: Int, price: Option[Long], count: Option[Long], enchant: Option[Int], attributeType: Option[Int], attributeValue: Option[Int])
}
