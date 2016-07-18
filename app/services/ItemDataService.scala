package services

import java.io.InputStream
import java.nio.file.Files
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import common.enums.base.{FileDataBased, FileDataEnumHolder}
import common.enums.game._
import models.Tables.{ItemTemplates, ItemTemplatesRow, ItemsOnSell}
import play.api.Environment
import play.api.cache.CacheApi
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.json._
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration
import scala.util.Try

/**
  * @author iRevThis
  */
@Singleton
class ItemDataService @Inject()(val dbConfigProvider: DatabaseConfigProvider,
                                env: Environment, cache: CacheApi)
                               (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._
  import ItemDataService._

  def byId(itemId: Int): Option[ItemData] = {
    cache.get[ItemData](itemId.toString).orElse(load(itemId))
  }

/*  private def forceLoad() = {
    env.getExistingFile("conf/json/items").filter(_.isDirectory).map(_.listFiles()) foreach { files =>
      files foreach { f =>
        val queries = closeable(Files.newInputStream(f.toPath))(Json.parse(_).as[Seq[ItemDataRow]]).map(dto2query)
        val q = DBIO.sequence(queries.toList)
        db.run(q)
      }
    }
  }

  private def fillUnknown() = {
    val templatesIds = for {q <- ItemTemplates} yield q.itemId
    val itemIds = for {i <- ItemsOnSell} yield i.itemId
    val q = for {
      i <- ItemsOnSell if !i.itemId.in(templatesIds.distinct)
    } yield i

    db.run(itemIds.result) onSuccess {
      case ids if ids.nonEmpty =>
        val queries = ids.map(r => ItemTemplates += ItemTemplatesRow(UUID.randomUUID(), r, "Unknown"))
        db.run(DBIO.sequence(queries))
      case _ =>
    }
  }*/

  private def load(itemId: Int): Option[ItemData] = {
    env.resourceAsStream(s"json/items/${id2group(itemId)}.json")
      .map { input => closeable(input)(Json.parse).as[Seq[ItemData]] }
      .flatMap { x =>
        x.foreach(v => cache.set(v.id.toString, v, Duration(10, TimeUnit.MINUTES)))
        cache.get[ItemData](itemId.toString)
      }
  }

  private def id2group(id: Int) = {
    val str = id.toString
    val hundred = Try(str.reverse(2).asDigit).getOrElse(0)
    val thousands = Try(str.take(str.length - 3).toInt * 1000).getOrElse(0)
    val min = thousands + hundred * 100
    val max = thousands + hundred * 100 + 99
    s"${min}_$max"
  }

  private def closeable[X](iStream: InputStream)(f: (InputStream) => X): X =
    try f(iStream) finally iStream.close()

  private def dto2query(itemData: ItemDataRow) = {
    ItemTemplates += ItemTemplatesRow(UUID.randomUUID(), itemData.id, itemData.name, itemData.description,
      itemData.itemType, itemData.slotBitType, itemData.armorType, itemData.etcItemType, itemData.recipeId,
      itemData.weight, itemData.price, itemData.defaultPrice, itemData.materialType, itemData.crystalType,
      itemData.crystalCount, itemData.isTrade, itemData.isDrop, itemData.isDestruct, itemData.isPrivateStore,
      itemData.keepType, itemData.physicalDamage, itemData.weaponType, itemData.magicDamage, itemData.magicWeapon,
      itemData.elementalEnable, itemData.isOlympiadCanUse, itemData.canMove)
  }

}

object ItemDataService {

  private def fileDataEnumReads[X <: FileDataBased](x: FileDataEnumHolder[X]) = new Reads[X] {
    override def reads(json: JsValue): JsResult[X] =
      (for {
        code <- json.asOpt[Int]
        enum <- x.byCode(code)
      } yield JsSuccess(enum)) getOrElse JsError("Invalid code")
  }

  private implicit val itemDataReads: Reads[ItemData] = new Reads[ItemData] {
    override def reads(json: JsValue): JsResult[ItemData] = {
      val id = (json \ "id").as[Int]
      val name = (json \ "name").as[String]
      val description = (json \ "description").asOpt[String]
      val itemType = (json \ "itemType").asOpt[ItemType](fileDataEnumReads(ItemType))
      val slotBitType = (json \ "slotBitType").asOpt[SlotBitType](fileDataEnumReads(SlotBitType))
      val armorType = (json \ "armorType").asOpt[ArmorType](fileDataEnumReads(ArmorType))
      val etcItemType = (json \ "etcItemType").asOpt[EtcItemType](fileDataEnumReads(EtcItemType))
      val weight = (json \ "weight").asOpt[Int]
      val recipeId = (json \ "recipeId").asOpt[Int]
      val materialType = (json \ "materialType").asOpt[MaterialType](fileDataEnumReads(MaterialType))
      val keepType = (json \ "keepType").asOpt[Int]
      val crystalType = (json \ "crystalType").asOpt[CrystalType](fileDataEnumReads(CrystalType))
      val crystalCount = (json \ "crystalCount").asOpt[Int]
      val isTrade = (json \ "isTrade").asOpt[Boolean]
      val isDrop = (json \ "isDrop").asOpt[Boolean]
      val isDestruct = (json \ "isDestruct").asOpt[Boolean]
      val isPrivateStore = (json \ "isPrivateStore").asOpt[Boolean]
      val price = (json \ "price").asOpt[Long]
      val defaultPrice = (json \ "defaultPrice").asOpt[Long]
      val weaponType = (json \ "weaponType").asOpt[WeaponType](fileDataEnumReads(WeaponType))
      val physicalDamage = (json \ "physicalDamage").asOpt[Int]
      val magicDamage = (json \ "magicDamage").asOpt[Int]
      val isOlympiadUse = (json \ "isOlympiadCanUse").asOpt[Boolean]
      val canMove = (json \ "canMove").asOpt[Boolean]
      val html = (json \ "html").asOpt[String]
      val magicWeapon = (json \ "magicWeapon").asOpt[Boolean]
      val elementalEnable = (json \ "elementalEnable").asOpt[Boolean]
      JsSuccess(ItemData(id, name, description, itemType, slotBitType, armorType, etcItemType,
        weight, recipeId, keepType, crystalType, crystalCount, materialType, isTrade, isDrop, isDestruct, isPrivateStore,
        price, defaultPrice, weaponType, physicalDamage, magicDamage, isOlympiadUse, canMove, html, magicWeapon, elementalEnable))
    }
  }

  private implicit val itemDataRowReads: Reads[ItemDataRow] = new Reads[ItemDataRow] {
    override def reads(json: JsValue): JsResult[ItemDataRow] = {
      val id = (json \ "id").as[Int]
      val name = (json \ "name").as[String]
      val description = (json \ "description").asOpt[String]
      val itemType = (json \ "itemType").asOpt[Int]
      val slotBitType = (json \ "slotBitType").asOpt[Int]
      val armorType = (json \ "armorType").asOpt[Int]
      val etcItemType = (json \ "etcItemType").asOpt[Int]
      val weight = (json \ "weight").asOpt[Int]
      val recipeId = (json \ "recipeId").asOpt[Int]
      val materialType = (json \ "materialType").asOpt[Int]
      val keepType = (json \ "keepType").asOpt[Int]
      val crystalType = (json \ "crystalType").asOpt[Int]
      val crystalCount = (json \ "crystalCount").asOpt[Int]
      val isTrade = (json \ "isTrade").asOpt[Boolean]
      val isDrop = (json \ "isDrop").asOpt[Boolean]
      val isDestruct = (json \ "isDestruct").asOpt[Boolean]
      val isPrivateStore = (json \ "isPrivateStore").asOpt[Boolean]
      val price = (json \ "price").asOpt[Long]
      val defaultPrice = (json \ "defaultPrice").asOpt[Long]
      val weaponType = (json \ "weaponType").asOpt[Int]
      val physicalDamage = (json \ "physicalDamage").asOpt[Int]
      val magicDamage = (json \ "magicDamage").asOpt[Int]
      val isOlympiadUse = (json \ "isOlympiadCanUse").asOpt[Boolean]
      val canMove = (json \ "canMove").asOpt[Boolean]
      val html = (json \ "html").asOpt[String]
      val magicWeapon = (json \ "magicWeapon").asOpt[Boolean]
      val elementalEnable = (json \ "elementalEnable").asOpt[Boolean]
      JsSuccess(ItemDataRow(id, name, description, itemType, slotBitType, armorType, etcItemType,
        weight, recipeId, keepType, crystalType, crystalCount, materialType, isTrade, isDrop, isDestruct, isPrivateStore,
        price, defaultPrice, weaponType, physicalDamage, magicDamage, isOlympiadUse, canMove, html, magicWeapon, elementalEnable))
    }
  }

  case class ItemDataMin(id: Int, name: String, description: Option[String],
                         html: Option[String] = None,
                         magicWeapon: Option[Boolean] = None,
                         isMagic: Option[Boolean] = None)

  case class ItemData(id: Int, name: String, description: Option[String],
                      itemType: Option[ItemType] = None,
                      slotBitType: Option[SlotBitType] = None,
                      armorType: Option[ArmorType] = None,
                      etcItemType: Option[EtcItemType] = None,
                      weight: Option[Int] = None,
                      recipeId: Option[Int] = None,
                      keepType: Option[Int] = None,
                      crystalType: Option[CrystalType] = None,
                      crystalCount: Option[Int] = None,
                      materialType: Option[MaterialType] = None,
                      isTrade: Option[Boolean] = None,
                      isDrop: Option[Boolean] = None,
                      isDestruct: Option[Boolean] = None,
                      isPrivateStore: Option[Boolean] = None,
                      price: Option[Long] = None,
                      defaultPrice: Option[Long] = None,
                      weaponType: Option[WeaponType] = None,
                      physicalDamage: Option[Int] = None,
                      magicDamage: Option[Int] = None,
                      isOlympiadCanUse: Option[Boolean] = None,
                      canMove: Option[Boolean] = None,
                      html: Option[String] = None,
                      magicWeapon: Option[Boolean] = None,
                      elementalEnable: Option[Boolean] = None)

  case class ItemDataRow(id: Int, name: String, description: Option[String],
                         itemType: Option[Int] = None,
                         slotBitType: Option[Int] = None,
                         armorType: Option[Int] = None,
                         etcItemType: Option[Int] = None,
                         weight: Option[Int] = None,
                         recipeId: Option[Int] = None,
                         keepType: Option[Int] = None,
                         crystalType: Option[Int] = None,
                         crystalCount: Option[Int] = None,
                         materialType: Option[Int] = None,
                         isTrade: Option[Boolean] = None,
                         isDrop: Option[Boolean] = None,
                         isDestruct: Option[Boolean] = None,
                         isPrivateStore: Option[Boolean] = None,
                         price: Option[Long] = None,
                         defaultPrice: Option[Long] = None,
                         weaponType: Option[Int] = None,
                         physicalDamage: Option[Int] = None,
                         magicDamage: Option[Int] = None,
                         isOlympiadCanUse: Option[Boolean] = None,
                         canMove: Option[Boolean] = None,
                         html: Option[String] = None,
                         magicWeapon: Option[Boolean] = None,
                         elementalEnable: Option[Boolean] = None)

}
