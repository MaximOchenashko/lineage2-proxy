package actors.db.types

import java.util.UUID

import actors.db.DatabaseWorker
import actors.db.DatabaseWorker.DatabaseWorkerMessage
import actors.db.types.BrokerDatabaseWorker.{SavePrivateStoreBuy, SavePrivateStoreSell, TraderLogout}
import models.Tables._
import network.tcp.clientpackets.highfive.PrivateStoreListBuy.{BuyItem, PrivateStoreBuyInfo}
import network.tcp.clientpackets.highfive.PrivateStoreListSell.{PrivateStoreSellInfo, TradeItem}

import scala.util.{Failure, Success}

/**
  * @author iRevThis
  */
trait BrokerDatabaseWorker {
  handler: DatabaseWorker =>

  import profile.api._
  import context.dispatcher

  def brokerReceiver: Receive = {
    case SavePrivateStoreSell(serverId, PrivateStoreSellInfo(sellerId, isPackage, items)) =>
      savePrivateStoreSell(serverId, sellerId, isPackage, items)

    case SavePrivateStoreBuy(serverId, PrivateStoreBuyInfo(sellerId, items)) =>
      savePrivateStoreBuy(serverId, sellerId, items)

    case TraderLogout(serverId, objectId) =>
      traderLogout(serverId, objectId)
  }

  private def savePrivateStoreSell(serverId: String, sellerId: Int, isPackage: Boolean, items: Seq[TradeItem]): Unit = {
    val sellerExists = for {s <- Players if s.serverId === serverId.toId && s.objectId === sellerId} yield s
    def withTemplates[X](f: => X) = createItemTemplates(items.map(_.item.itemId))(f)
    db.run(sellerExists.result).map(_.headOption) onSuccess {
      case Some(seller) =>
        val actualItems = for {i <- ItemsOnSell if i.sellerId === seller.id && i.actual} yield i
        db.run(actualItems.result) onSuccess {
          case dbItems if dbItems.nonEmpty =>
            val queries = dbItems flatMap { r =>
              val itemQuery = ItemsOnSell.filter(_.id === r.id)
              items.find(_.item.objectId == r.itemObjectId) match {
                case Some(x) =>
                  if (r.ownersPrice == x.ownersPrice) {
                    Seq(itemQuery.map(r => (r.count, r.createDate)).update((x.item.count, now)))
                  } else {
                    Seq(itemQuery.map(_.actual).update(false), sellItem2query(isPackage, x, seller.id))
                  }
                case None => Seq(itemQuery.map(_.actual).update(false))
              }
            }
            val insertItems = items.filterNot(i => dbItems.exists(_.itemObjectId == i.item.objectId))
            withTemplates(db.run(DBIO.sequence(queries ++ sellItems2queries(isPackage, insertItems, seller.id)).transactionally))
          case _ =>
            val updateQuery = for {i <- ItemsOnSell if i.sellerId === seller.id} yield i.actual
            withTemplates(db.run(DBIO.sequence(Seq(updateQuery.update(false)) ++ sellItems2queries(isPackage, items, seller.id)).transactionally))
        }
      case _ =>
        /*val sellerUuid = randomId
        val insertQuery = Players += PlayersRow(sellerUuid, serverId.toId, sellerId, now, now, "Undefined",
          None, -1, -1, -1, None, isNoble = false, isHero = false)

        withTemplates(db.run(DBIO.sequence(Seq(insertQuery) ++ sellItems2queries(isPackage, items, sellerUuid)).transactionally))*/
    }
  }

  private def createItemTemplates[X](itemIds: Seq[Int])(onComplete: => X) = {
    val itemTemplateExists = for {i <- ItemTemplates if i.itemId inSet itemIds.distinct} yield i.itemId
    db.run(itemTemplateExists.result) onComplete {
      case Success(seq) =>
        val newItemTemplatesQ = itemIds.diff(seq).map(r => ItemTemplates += ItemTemplatesRow(randomId, r, "Unknown"))
        db.run(DBIO.sequence(newItemTemplatesQ)) onComplete { case _ => onComplete }
      case Failure(e) => onComplete
    }
  }

  private def savePrivateStoreBuy(serverId: String, sellerId: Int, items: Seq[BuyItem]): Unit = {
    val customerExists = for {s <- Players if s.serverId === serverId.toId && s.objectId === sellerId} yield s
    def withTemplates[X](f: => X) = createItemTemplates(items.map(_.item.itemId))(f)
    db.run(customerExists.result).map(_.headOption) onSuccess {
      case Some(seller) =>
        val actualItems = for {i <- ItemsOnBuy if i.customerId === seller.id && i.actual} yield i
        db.run(actualItems.result) onSuccess {
          case dbItems if dbItems.nonEmpty =>
            val queries = dbItems flatMap { r =>
              val itemQuery = ItemsOnBuy.filter(_.id === r.id)
              items.find(_.item.objectId == r.itemObjectId) match {
                case Some(x) =>
                  if (r.customerPrice == x.ownersPrice) {
                    Seq(itemQuery.map(r => (r.count, r.createDate)).update((x.item.count, now)))
                  } else {
                    Seq(itemQuery.map(_.actual).update(false), buyItem2query(x, seller.id))
                  }
                case None => Seq(itemQuery.map(_.actual).update(false))
              }
            }
            val insertItems = items.filterNot(i => dbItems.exists(_.itemObjectId == i.item.objectId))
            withTemplates(db.run(DBIO.sequence(queries ++ insertItems.map(buyItem2query(_, seller.id))).transactionally))
          case _ =>
            val updateQuery = for {i <- ItemsOnSell if i.sellerId === seller.id} yield i.actual
            withTemplates(db.run(DBIO.sequence(Seq(updateQuery.update(false)) ++ items.map(buyItem2query(_, seller.id))).transactionally))
        }
      case _ =>
        /*val sellerUuid = randomId
        val insertQuery = Players += PlayersRow(sellerUuid, serverId.toId, sellerId, now, now, "Undefined",
          None, -1, -1, -1, None, isNoble = false, isHero = false)
        withTemplates(db.run(DBIO.sequence(Seq(insertQuery) ++ items.map(buyItem2query(_, sellerUuid))).transactionally))*/
    }
  }

  private def traderLogout(serverId: String, objectId: Int): Unit = {
    val traderId = for {p <- Players if p.serverId === serverId.toId && p.objectId === objectId} yield p.id
    db.run(traderId.result).map(_.headOption) onSuccess {
      case Some(id) =>
        val sellersUpdate = for {s <- ItemsOnSell if s.actual && (s.sellerId === id)} yield s.actual
        val customersUpdate = for {s <- ItemsOnBuy if s.actual && (s.customerId === id)} yield s.actual
        db.run(sellersUpdate.update(false) zip customersUpdate.update(false))
      case None =>
    }
  }

  private def sellItems2queries(isPackage: Boolean, items: Seq[TradeItem], sellerId: UUID) = {
    items map (sellItem2query(isPackage, _, sellerId))
  }

  private def sellItem2query(isPackage: Boolean, tradeItem: TradeItem, sellerId: UUID) = {
    val item = tradeItem.item
    val elementInfo = item.elementInfo

    ItemsOnSell += ItemsOnSellRow(randomId, now, sellerId,
      isPackage, item.itemId, item.objectId, item.count, item.enchantLevel, elementInfo.attackElementId,
      elementInfo.attackElementValue, elementInfo.defenseFire, elementInfo.defenseWater,
      elementInfo.defenceWind, elementInfo.defenceEarth, elementInfo.defenceHoly, elementInfo.defenceUnholy,
      tradeItem.ownersPrice, tradeItem.storePrice, actual = true)
  }

  private def buyItem2query(buyItem: BuyItem, customerId: UUID) = {
    val item = buyItem.item
    ItemsOnBuy += ItemsOnBuyRow(randomId, now, customerId,
      item.itemId, item.objectId, item.count, buyItem.ownersPrice, buyItem.storePrice, actual = true)
  }
}

object BrokerDatabaseWorker {

  case class SavePrivateStoreSell(serverId: String, privateStoreSellInfo: PrivateStoreSellInfo) extends DatabaseWorkerMessage

  case class SavePrivateStoreBuy(serverId: String, privateStoreBuyInfo: PrivateStoreBuyInfo) extends DatabaseWorkerMessage

  case class TraderLogout(serverId: String, objectId: Int)

}
