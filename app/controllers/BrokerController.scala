package controllers

import java.sql.Timestamp
import java.time.{Duration, Instant, OffsetDateTime}
import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import controllers.BrokerController.{ItemOnBuy, ItemOnSell, Location}
import controllers.base.BaseController
import models.Tables.{ItemTemplates, ItemsOnBuy, ItemsOnSell, Players, QueryExtension}
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.json.Json
import services.DomainsService
import services.security.AuthService
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

/**
  * @author iRevThis
  */
@Singleton
class BrokerController @Inject()(val authService: AuthService,
                                 val dbConfigProvider: DatabaseConfigProvider,
                                 system: ActorSystem,
                                 domainsService: DomainsService)
                                (implicit ec: ExecutionContext) extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  implicit val locationFormat = Json.format[Location]
  implicit val itemOnSellFormat = Json.format[ItemOnSell]
  implicit val itemOnBuyFormat = Json.format[ItemOnBuy]

  def listItemsOnSell = authorized().async { implicit request =>
    //val validDate = Timestamp.from(OffsetDateTime.now().minusHours(10).toInstant)
    val itemQuery = for {
      q <- ItemsOnSell.applyOData if q.actual //&& q.createDate > validDate
      p <- Players if p.id === q.sellerId
      i <- ItemTemplates.applyOData if q.itemId === i.itemId
    } yield (q, p, i.name)

    val query = ItemsOnSell.queryOrderBy(orderBy).map(f => itemQuery.sortBy(q => f(q._1))).getOrElse(itemQuery)

    db.run(query.page.result zip query.length.result) map {
      case (list, count) =>
        val items = list map {
          case (item, player, itemName) =>
            val loc = Location(domainsService.territory(player.x, player.y).getOrElse("Unknown"), player.x, player.y, player.z)
            val fromLastSeen = scala.math.abs(Duration.between(Instant.now(), item.createDate.toInstant).toMinutes)
            ItemOnSell(item.itemId, itemName, item.count, item.ownersPrice, item.storePrice, player.name, fromLastSeen, loc)
        }
        ok(items, offset, limit, count)
    }
  }

  def listItemsOnBuy = authorized().async { implicit request =>
    val validDate = Timestamp.from(OffsetDateTime.now().minusHours(10).toInstant)
    val itemQuery = for {
      q <- ItemsOnBuy.queryFilter(filter) if q.actual && q.createDate > validDate
      p <- Players if p.id === q.customerId
      i <- ItemTemplates if q.itemId === i.itemId
    } yield (q, p, i.name)

    val query = ItemsOnBuy.queryOrderBy(orderBy).map(f => itemQuery.sortBy(q => f(q._1))).getOrElse(itemQuery)

    db.run(query.page.result zip itemQuery.length.result) map {
      case (list, count) =>
        val items = list map {
          case (item, player, itemName) =>
            val loc = Location(domainsService.territory(player.x, player.y).getOrElse("Unknown"), player.x, player.y, player.z)
            val fromLastSeen = scala.math.abs(Duration.between(Instant.now(), item.createDate.toInstant).toMinutes)
            ItemOnBuy(item.itemId, itemName, item.customerPrice, player.name, fromLastSeen, loc)
        }
        ok(items, offset, limit, count)
    }
  }

}

object BrokerController {

  case class Location(city: String, x: Int, y: Int, z: Int)

  case class ItemOnSell(itemId: Int, name: String, count: Long, ownersPrice: Long, storePrice: Long, seller: String, createDate: Long, location: Location)

  case class ItemOnBuy(itemId: Int, name: String, price: Long, customer: String, createDate: Long, location: Location)

}
