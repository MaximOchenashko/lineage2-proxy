package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  import slick.collection.heterogeneous._
  import slick.collection.heterogeneous.syntax._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(BrokerNotifications.schema, DroppedItems.schema, ItemsOnBuy.schema, ItemsOnSell.schema, ItemTemplates.schema, NpcsSpawns.schema, Players.schema, PlayEvolutions.schema, Servers.schema, Subclasses.schema, Users.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table BrokerNotifications
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param updateDate Database column update_date SqlType(timestamp)
   *  @param userId Database column user_id SqlType(uuid)
   *  @param enabled Database column enabled SqlType(bool)
   *  @param itemId Database column item_id SqlType(int4)
   *  @param price Database column price SqlType(int8), Default(None)
   *  @param count Database column count SqlType(int8), Default(None)
   *  @param enchant Database column enchant SqlType(int4), Default(None)
   *  @param attributeType Database column attribute_type SqlType(int4), Default(None)
   *  @param attributeValue Database column attribute_value SqlType(int4), Default(None) */
  case class BrokerNotificationsRow(id: java.util.UUID, createDate: java.sql.Timestamp, updateDate: java.sql.Timestamp, userId: java.util.UUID, enabled: Boolean, itemId: Int, price: Option[Long] = None, count: Option[Long] = None, enchant: Option[Int] = None, attributeType: Option[Int] = None, attributeValue: Option[Int] = None)
  /** GetResult implicit for fetching BrokerNotificationsRow objects using plain SQL queries */
  implicit def GetResultBrokerNotificationsRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[Boolean], e3: GR[Int], e4: GR[Option[Long]], e5: GR[Option[Int]]): GR[BrokerNotificationsRow] = GR{
    prs => import prs._
    BrokerNotificationsRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[java.util.UUID], <<[Boolean], <<[Int], <<?[Long], <<?[Long], <<?[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table broker_notifications. Objects of this class serve as prototypes for rows in queries. */
  class BrokerNotifications(_tableTag: Tag) extends Table[BrokerNotificationsRow](_tableTag, Some("scripts-dev"), "broker_notifications") {
    def * = (id, createDate, updateDate, userId, enabled, itemId, price, count, enchant, attributeType, attributeValue) <> (BrokerNotificationsRow.tupled, BrokerNotificationsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(updateDate), Rep.Some(userId), Rep.Some(enabled), Rep.Some(itemId), price, count, enchant, attributeType, attributeValue).shaped.<>({r=>import r._; _1.map(_=> BrokerNotificationsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column update_date SqlType(timestamp) */
    val updateDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("update_date")
    /** Database column user_id SqlType(uuid) */
    val userId: Rep[java.util.UUID] = column[java.util.UUID]("user_id")
    /** Database column enabled SqlType(bool) */
    val enabled: Rep[Boolean] = column[Boolean]("enabled")
    /** Database column item_id SqlType(int4) */
    val itemId: Rep[Int] = column[Int]("item_id")
    /** Database column price SqlType(int8), Default(None) */
    val price: Rep[Option[Long]] = column[Option[Long]]("price", O.Default(None))
    /** Database column count SqlType(int8), Default(None) */
    val count: Rep[Option[Long]] = column[Option[Long]]("count", O.Default(None))
    /** Database column enchant SqlType(int4), Default(None) */
    val enchant: Rep[Option[Int]] = column[Option[Int]]("enchant", O.Default(None))
    /** Database column attribute_type SqlType(int4), Default(None) */
    val attributeType: Rep[Option[Int]] = column[Option[Int]]("attribute_type", O.Default(None))
    /** Database column attribute_value SqlType(int4), Default(None) */
    val attributeValue: Rep[Option[Int]] = column[Option[Int]]("attribute_value", O.Default(None))

    /** Foreign key referencing Users (database name broker_notifications_user_id_fkey) */
    lazy val usersFk = foreignKey("broker_notifications_user_id_fkey", userId, Users)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table BrokerNotifications */
  lazy val BrokerNotifications = new TableQuery(tag => new BrokerNotifications(tag))

  /** Entity class storing rows of table DroppedItems
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param tokenId Database column token_id SqlType(uuid)
   *  @param dropperId Database column dropper_id SqlType(int4)
   *  @param itemObjectId Database column item_object_id SqlType(int4)
   *  @param itemId Database column item_id SqlType(int4)
   *  @param itemX Database column item_x SqlType(int4)
   *  @param itemY Database column item_y SqlType(int4)
   *  @param itemZ Database column item_z SqlType(int4)
   *  @param stackable Database column stackable SqlType(bool)
   *  @param count Database column count SqlType(int8) */
  case class DroppedItemsRow(id: java.util.UUID, createDate: java.sql.Timestamp, tokenId: java.util.UUID, dropperId: Int, itemObjectId: Int, itemId: Int, itemX: Int, itemY: Int, itemZ: Int, stackable: Boolean, count: Long)
  /** GetResult implicit for fetching DroppedItemsRow objects using plain SQL queries */
  implicit def GetResultDroppedItemsRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[Int], e3: GR[Boolean], e4: GR[Long]): GR[DroppedItemsRow] = GR{
    prs => import prs._
    DroppedItemsRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[java.util.UUID], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Boolean], <<[Long]))
  }
  /** Table description of table dropped_items. Objects of this class serve as prototypes for rows in queries. */
  class DroppedItems(_tableTag: Tag) extends Table[DroppedItemsRow](_tableTag, Some("scripts-dev"), "dropped_items") {
    def * = (id, createDate, tokenId, dropperId, itemObjectId, itemId, itemX, itemY, itemZ, stackable, count) <> (DroppedItemsRow.tupled, DroppedItemsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(tokenId), Rep.Some(dropperId), Rep.Some(itemObjectId), Rep.Some(itemId), Rep.Some(itemX), Rep.Some(itemY), Rep.Some(itemZ), Rep.Some(stackable), Rep.Some(count)).shaped.<>({r=>import r._; _1.map(_=> DroppedItemsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column token_id SqlType(uuid) */
    val tokenId: Rep[java.util.UUID] = column[java.util.UUID]("token_id")
    /** Database column dropper_id SqlType(int4) */
    val dropperId: Rep[Int] = column[Int]("dropper_id")
    /** Database column item_object_id SqlType(int4) */
    val itemObjectId: Rep[Int] = column[Int]("item_object_id")
    /** Database column item_id SqlType(int4) */
    val itemId: Rep[Int] = column[Int]("item_id")
    /** Database column item_x SqlType(int4) */
    val itemX: Rep[Int] = column[Int]("item_x")
    /** Database column item_y SqlType(int4) */
    val itemY: Rep[Int] = column[Int]("item_y")
    /** Database column item_z SqlType(int4) */
    val itemZ: Rep[Int] = column[Int]("item_z")
    /** Database column stackable SqlType(bool) */
    val stackable: Rep[Boolean] = column[Boolean]("stackable")
    /** Database column count SqlType(int8) */
    val count: Rep[Long] = column[Long]("count")

    /** Uniqueness Index over (dropperId,tokenId,itemObjectId) (database name dropped_items_unique_key) */
    val index1 = index("dropped_items_unique_key", (dropperId, tokenId, itemObjectId), unique=true)
  }
  /** Collection-like TableQuery object for table DroppedItems */
  lazy val DroppedItems = new TableQuery(tag => new DroppedItems(tag))

  /** Entity class storing rows of table ItemsOnBuy
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param customerId Database column customer_id SqlType(uuid)
   *  @param itemId Database column item_id SqlType(int4)
   *  @param itemObjectId Database column item_object_id SqlType(int4)
   *  @param count Database column count SqlType(int8)
   *  @param customerPrice Database column customer_price SqlType(int8)
   *  @param storePrice Database column store_price SqlType(int8)
   *  @param actual Database column actual SqlType(bool) */
  case class ItemsOnBuyRow(id: java.util.UUID, createDate: java.sql.Timestamp, customerId: java.util.UUID, itemId: Int, itemObjectId: Int, count: Long, customerPrice: Long, storePrice: Long, actual: Boolean)
  /** GetResult implicit for fetching ItemsOnBuyRow objects using plain SQL queries */
  implicit def GetResultItemsOnBuyRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[Int], e3: GR[Long], e4: GR[Boolean]): GR[ItemsOnBuyRow] = GR{
    prs => import prs._
    ItemsOnBuyRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[java.util.UUID], <<[Int], <<[Int], <<[Long], <<[Long], <<[Long], <<[Boolean]))
  }
  /** Table description of table items_on_buy. Objects of this class serve as prototypes for rows in queries. */
  class ItemsOnBuy(_tableTag: Tag) extends Table[ItemsOnBuyRow](_tableTag, Some("scripts-dev"), "items_on_buy") {
    def * = (id, createDate, customerId, itemId, itemObjectId, count, customerPrice, storePrice, actual) <> (ItemsOnBuyRow.tupled, ItemsOnBuyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(customerId), Rep.Some(itemId), Rep.Some(itemObjectId), Rep.Some(count), Rep.Some(customerPrice), Rep.Some(storePrice), Rep.Some(actual)).shaped.<>({r=>import r._; _1.map(_=> ItemsOnBuyRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column customer_id SqlType(uuid) */
    val customerId: Rep[java.util.UUID] = column[java.util.UUID]("customer_id")
    /** Database column item_id SqlType(int4) */
    val itemId: Rep[Int] = column[Int]("item_id")
    /** Database column item_object_id SqlType(int4) */
    val itemObjectId: Rep[Int] = column[Int]("item_object_id")
    /** Database column count SqlType(int8) */
    val count: Rep[Long] = column[Long]("count")
    /** Database column customer_price SqlType(int8) */
    val customerPrice: Rep[Long] = column[Long]("customer_price")
    /** Database column store_price SqlType(int8) */
    val storePrice: Rep[Long] = column[Long]("store_price")
    /** Database column actual SqlType(bool) */
    val actual: Rep[Boolean] = column[Boolean]("actual")

    /** Foreign key referencing ItemTemplates (database name items_on_buy_item_id_fkey) */
    lazy val itemTemplatesFk = foreignKey("items_on_buy_item_id_fkey", itemId, ItemTemplates)(r => r.itemId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Players (database name items_on_buy_customer_id_fkey) */
    lazy val playersFk = foreignKey("items_on_buy_customer_id_fkey", customerId, Players)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table ItemsOnBuy */
  lazy val ItemsOnBuy = new TableQuery(tag => new ItemsOnBuy(tag))

  /** Entity class storing rows of table ItemsOnSell
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param sellerId Database column seller_id SqlType(uuid)
   *  @param isPackage Database column is_package SqlType(bool)
   *  @param itemId Database column item_id SqlType(int4)
   *  @param itemObjectId Database column item_object_id SqlType(int4)
   *  @param count Database column count SqlType(int8)
   *  @param enchant Database column enchant SqlType(int4)
   *  @param attackelementid Database column attackelementid SqlType(int4)
   *  @param attackelementvalue Database column attackelementvalue SqlType(int4)
   *  @param defensefire Database column defensefire SqlType(int4)
   *  @param defensewater Database column defensewater SqlType(int4)
   *  @param defencewind Database column defencewind SqlType(int4)
   *  @param defenceearth Database column defenceearth SqlType(int4)
   *  @param defenceholy Database column defenceholy SqlType(int4)
   *  @param defenceunholy Database column defenceunholy SqlType(int4)
   *  @param ownersPrice Database column owners_price SqlType(int8)
   *  @param storePrice Database column store_price SqlType(int8)
   *  @param actual Database column actual SqlType(bool) */
  case class ItemsOnSellRow(id: java.util.UUID, createDate: java.sql.Timestamp, sellerId: java.util.UUID, isPackage: Boolean, itemId: Int, itemObjectId: Int, count: Long, enchant: Int, attackelementid: Int, attackelementvalue: Int, defensefire: Int, defensewater: Int, defencewind: Int, defenceearth: Int, defenceholy: Int, defenceunholy: Int, ownersPrice: Long, storePrice: Long, actual: Boolean)
  /** GetResult implicit for fetching ItemsOnSellRow objects using plain SQL queries */
  implicit def GetResultItemsOnSellRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[Boolean], e3: GR[Int], e4: GR[Long]): GR[ItemsOnSellRow] = GR{
    prs => import prs._
    ItemsOnSellRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[java.util.UUID], <<[Boolean], <<[Int], <<[Int], <<[Long], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Long], <<[Long], <<[Boolean]))
  }
  /** Table description of table items_on_sell. Objects of this class serve as prototypes for rows in queries. */
  class ItemsOnSell(_tableTag: Tag) extends Table[ItemsOnSellRow](_tableTag, Some("scripts-dev"), "items_on_sell") {
    def * = (id, createDate, sellerId, isPackage, itemId, itemObjectId, count, enchant, attackelementid, attackelementvalue, defensefire, defensewater, defencewind, defenceearth, defenceholy, defenceunholy, ownersPrice, storePrice, actual) <> (ItemsOnSellRow.tupled, ItemsOnSellRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(sellerId), Rep.Some(isPackage), Rep.Some(itemId), Rep.Some(itemObjectId), Rep.Some(count), Rep.Some(enchant), Rep.Some(attackelementid), Rep.Some(attackelementvalue), Rep.Some(defensefire), Rep.Some(defensewater), Rep.Some(defencewind), Rep.Some(defenceearth), Rep.Some(defenceholy), Rep.Some(defenceunholy), Rep.Some(ownersPrice), Rep.Some(storePrice), Rep.Some(actual)).shaped.<>({r=>import r._; _1.map(_=> ItemsOnSellRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get, _19.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column seller_id SqlType(uuid) */
    val sellerId: Rep[java.util.UUID] = column[java.util.UUID]("seller_id")
    /** Database column is_package SqlType(bool) */
    val isPackage: Rep[Boolean] = column[Boolean]("is_package")
    /** Database column item_id SqlType(int4) */
    val itemId: Rep[Int] = column[Int]("item_id")
    /** Database column item_object_id SqlType(int4) */
    val itemObjectId: Rep[Int] = column[Int]("item_object_id")
    /** Database column count SqlType(int8) */
    val count: Rep[Long] = column[Long]("count")
    /** Database column enchant SqlType(int4) */
    val enchant: Rep[Int] = column[Int]("enchant")
    /** Database column attackelementid SqlType(int4) */
    val attackelementid: Rep[Int] = column[Int]("attackelementid")
    /** Database column attackelementvalue SqlType(int4) */
    val attackelementvalue: Rep[Int] = column[Int]("attackelementvalue")
    /** Database column defensefire SqlType(int4) */
    val defensefire: Rep[Int] = column[Int]("defensefire")
    /** Database column defensewater SqlType(int4) */
    val defensewater: Rep[Int] = column[Int]("defensewater")
    /** Database column defencewind SqlType(int4) */
    val defencewind: Rep[Int] = column[Int]("defencewind")
    /** Database column defenceearth SqlType(int4) */
    val defenceearth: Rep[Int] = column[Int]("defenceearth")
    /** Database column defenceholy SqlType(int4) */
    val defenceholy: Rep[Int] = column[Int]("defenceholy")
    /** Database column defenceunholy SqlType(int4) */
    val defenceunholy: Rep[Int] = column[Int]("defenceunholy")
    /** Database column owners_price SqlType(int8) */
    val ownersPrice: Rep[Long] = column[Long]("owners_price")
    /** Database column store_price SqlType(int8) */
    val storePrice: Rep[Long] = column[Long]("store_price")
    /** Database column actual SqlType(bool) */
    val actual: Rep[Boolean] = column[Boolean]("actual")

    /** Foreign key referencing Players (database name items_on_sell_seller_id_fkey) */
    lazy val playersFk = foreignKey("items_on_sell_seller_id_fkey", sellerId, Players)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table ItemsOnSell */
  lazy val ItemsOnSell = new TableQuery(tag => new ItemsOnSell(tag))

  /** Row type of table ItemTemplates */
  type ItemTemplatesRow = HCons[java.util.UUID,HCons[Int,HCons[String,HCons[Option[String],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Long],HCons[Option[Long],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Boolean],HCons[Option[Boolean],HCons[Option[Boolean],HCons[Option[Boolean],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Boolean],HCons[Option[Boolean],HCons[Option[Boolean],HCons[Option[Boolean],HNil]]]]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for ItemTemplatesRow providing default values if available in the database schema. */
  def ItemTemplatesRow(id: java.util.UUID, itemId: Int, name: String, description: Option[String] = None, itemType: Option[Int] = None, slotBitType: Option[Int] = None, armorType: Option[Int] = None, etcitemType: Option[Int] = None, recipeId: Option[Int] = None, weight: Option[Int] = None, price: Option[Long] = None, defaultPrice: Option[Long] = None, materialType: Option[Int] = None, crystalType: Option[Int] = None, crystalCount: Option[Int] = None, isTrade: Option[Boolean] = None, isDrop: Option[Boolean] = None, isDestruct: Option[Boolean] = None, isPrivateStore: Option[Boolean] = None, keepType: Option[Int] = None, physicalDamage: Option[Int] = None, weaponType: Option[Int] = None, magicalDamage: Option[Int] = None, magicWeapon: Option[Boolean] = None, elementalEnable: Option[Boolean] = None, isOlympiadCanUse: Option[Boolean] = None, canMove: Option[Boolean] = None): ItemTemplatesRow = {
    id :: itemId :: name :: description :: itemType :: slotBitType :: armorType :: etcitemType :: recipeId :: weight :: price :: defaultPrice :: materialType :: crystalType :: crystalCount :: isTrade :: isDrop :: isDestruct :: isPrivateStore :: keepType :: physicalDamage :: weaponType :: magicalDamage :: magicWeapon :: elementalEnable :: isOlympiadCanUse :: canMove :: HNil
  }
  /** GetResult implicit for fetching ItemTemplatesRow objects using plain SQL queries */
  implicit def GetResultItemTemplatesRow(implicit e0: GR[java.util.UUID], e1: GR[Int], e2: GR[String], e3: GR[Option[String]], e4: GR[Option[Int]], e5: GR[Option[Long]], e6: GR[Option[Boolean]]): GR[ItemTemplatesRow] = GR{
    prs => import prs._
    <<[java.util.UUID] :: <<[Int] :: <<[String] :: <<?[String] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Long] :: <<?[Long] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Boolean] :: <<?[Boolean] :: <<?[Boolean] :: <<?[Boolean] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Boolean] :: <<?[Boolean] :: <<?[Boolean] :: <<?[Boolean] :: HNil
  }
  /** Table description of table item_templates. Objects of this class serve as prototypes for rows in queries. */
  class ItemTemplates(_tableTag: Tag) extends Table[ItemTemplatesRow](_tableTag, Some("scripts-dev"), "item_templates") {
    def * = id :: itemId :: name :: description :: itemType :: slotBitType :: armorType :: etcitemType :: recipeId :: weight :: price :: defaultPrice :: materialType :: crystalType :: crystalCount :: isTrade :: isDrop :: isDestruct :: isPrivateStore :: keepType :: physicalDamage :: weaponType :: magicalDamage :: magicWeapon :: elementalEnable :: isOlympiadCanUse :: canMove :: HNil

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column item_id SqlType(int4) */
    val itemId: Rep[Int] = column[Int]("item_id")
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column description SqlType(varchar), Length(500,true), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Length(500,varying=true), O.Default(None))
    /** Database column item_type SqlType(int4), Default(None) */
    val itemType: Rep[Option[Int]] = column[Option[Int]]("item_type", O.Default(None))
    /** Database column slot_bit_type SqlType(int4), Default(None) */
    val slotBitType: Rep[Option[Int]] = column[Option[Int]]("slot_bit_type", O.Default(None))
    /** Database column armor_type SqlType(int4), Default(None) */
    val armorType: Rep[Option[Int]] = column[Option[Int]]("armor_type", O.Default(None))
    /** Database column etcitem_type SqlType(int4), Default(None) */
    val etcitemType: Rep[Option[Int]] = column[Option[Int]]("etcitem_type", O.Default(None))
    /** Database column recipe_id SqlType(int4), Default(None) */
    val recipeId: Rep[Option[Int]] = column[Option[Int]]("recipe_id", O.Default(None))
    /** Database column weight SqlType(int4), Default(None) */
    val weight: Rep[Option[Int]] = column[Option[Int]]("weight", O.Default(None))
    /** Database column price SqlType(int8), Default(None) */
    val price: Rep[Option[Long]] = column[Option[Long]]("price", O.Default(None))
    /** Database column default_price SqlType(int8), Default(None) */
    val defaultPrice: Rep[Option[Long]] = column[Option[Long]]("default_price", O.Default(None))
    /** Database column material_type SqlType(int4), Default(None) */
    val materialType: Rep[Option[Int]] = column[Option[Int]]("material_type", O.Default(None))
    /** Database column crystal_type SqlType(int4), Default(None) */
    val crystalType: Rep[Option[Int]] = column[Option[Int]]("crystal_type", O.Default(None))
    /** Database column crystal_count SqlType(int4), Default(None) */
    val crystalCount: Rep[Option[Int]] = column[Option[Int]]("crystal_count", O.Default(None))
    /** Database column is_trade SqlType(bool), Default(None) */
    val isTrade: Rep[Option[Boolean]] = column[Option[Boolean]]("is_trade", O.Default(None))
    /** Database column is_drop SqlType(bool), Default(None) */
    val isDrop: Rep[Option[Boolean]] = column[Option[Boolean]]("is_drop", O.Default(None))
    /** Database column is_destruct SqlType(bool), Default(None) */
    val isDestruct: Rep[Option[Boolean]] = column[Option[Boolean]]("is_destruct", O.Default(None))
    /** Database column is_private_store SqlType(bool), Default(None) */
    val isPrivateStore: Rep[Option[Boolean]] = column[Option[Boolean]]("is_private_store", O.Default(None))
    /** Database column keep_type SqlType(int4), Default(None) */
    val keepType: Rep[Option[Int]] = column[Option[Int]]("keep_type", O.Default(None))
    /** Database column physical_damage SqlType(int4), Default(None) */
    val physicalDamage: Rep[Option[Int]] = column[Option[Int]]("physical_damage", O.Default(None))
    /** Database column weapon_type SqlType(int4), Default(None) */
    val weaponType: Rep[Option[Int]] = column[Option[Int]]("weapon_type", O.Default(None))
    /** Database column magical_damage SqlType(int4), Default(None) */
    val magicalDamage: Rep[Option[Int]] = column[Option[Int]]("magical_damage", O.Default(None))
    /** Database column magic_weapon SqlType(bool), Default(None) */
    val magicWeapon: Rep[Option[Boolean]] = column[Option[Boolean]]("magic_weapon", O.Default(None))
    /** Database column elemental_enable SqlType(bool), Default(None) */
    val elementalEnable: Rep[Option[Boolean]] = column[Option[Boolean]]("elemental_enable", O.Default(None))
    /** Database column is_olympiad_can_use SqlType(bool), Default(None) */
    val isOlympiadCanUse: Rep[Option[Boolean]] = column[Option[Boolean]]("is_olympiad_can_use", O.Default(None))
    /** Database column can_move SqlType(bool), Default(None) */
    val canMove: Rep[Option[Boolean]] = column[Option[Boolean]]("can_move", O.Default(None))

    /** Uniqueness Index over (itemId) (database name item_templates_item_id_unique) */
    val index1 = index("item_templates_item_id_unique", itemId :: HNil, unique=true)
  }
  /** Collection-like TableQuery object for table ItemTemplates */
  lazy val ItemTemplates = new TableQuery(tag => new ItemTemplates(tag))

  /** Entity class storing rows of table NpcsSpawns
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param updateDate Database column update_date SqlType(timestamp)
   *  @param tokenId Database column token_id SqlType(uuid)
   *  @param watcherName Database column watcher_name SqlType(varchar), Length(255,true)
   *  @param watcherX Database column watcher_x SqlType(int4)
   *  @param watcherY Database column watcher_y SqlType(int4)
   *  @param watcherZ Database column watcher_z SqlType(int4)
   *  @param npcObjectId Database column npc_object_id SqlType(int4)
   *  @param npcId Database column npc_id SqlType(int4)
   *  @param npcLevel Database column npc_level SqlType(int4)
   *  @param npcName Database column npc_name SqlType(varchar), Length(255,true)
   *  @param npcIsDead Database column npc_is_dead SqlType(bool)
   *  @param npcX Database column npc_x SqlType(int4)
   *  @param npcY Database column npc_y SqlType(int4)
   *  @param npcZ Database column npc_z SqlType(int4) */
  case class NpcsSpawnsRow(id: java.util.UUID, createDate: java.sql.Timestamp, updateDate: java.sql.Timestamp, tokenId: java.util.UUID, watcherName: String, watcherX: Int, watcherY: Int, watcherZ: Int, npcObjectId: Int, npcId: Int, npcLevel: Int, npcName: String, npcIsDead: Boolean, npcX: Int, npcY: Int, npcZ: Int)
  /** GetResult implicit for fetching NpcsSpawnsRow objects using plain SQL queries */
  implicit def GetResultNpcsSpawnsRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[String], e3: GR[Int], e4: GR[Boolean]): GR[NpcsSpawnsRow] = GR{
    prs => import prs._
    NpcsSpawnsRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[java.util.UUID], <<[String], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[String], <<[Boolean], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table npcs_spawns. Objects of this class serve as prototypes for rows in queries. */
  class NpcsSpawns(_tableTag: Tag) extends Table[NpcsSpawnsRow](_tableTag, Some("scripts-dev"), "npcs_spawns") {
    def * = (id, createDate, updateDate, tokenId, watcherName, watcherX, watcherY, watcherZ, npcObjectId, npcId, npcLevel, npcName, npcIsDead, npcX, npcY, npcZ) <> (NpcsSpawnsRow.tupled, NpcsSpawnsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(updateDate), Rep.Some(tokenId), Rep.Some(watcherName), Rep.Some(watcherX), Rep.Some(watcherY), Rep.Some(watcherZ), Rep.Some(npcObjectId), Rep.Some(npcId), Rep.Some(npcLevel), Rep.Some(npcName), Rep.Some(npcIsDead), Rep.Some(npcX), Rep.Some(npcY), Rep.Some(npcZ)).shaped.<>({r=>import r._; _1.map(_=> NpcsSpawnsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column update_date SqlType(timestamp) */
    val updateDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("update_date")
    /** Database column token_id SqlType(uuid) */
    val tokenId: Rep[java.util.UUID] = column[java.util.UUID]("token_id")
    /** Database column watcher_name SqlType(varchar), Length(255,true) */
    val watcherName: Rep[String] = column[String]("watcher_name", O.Length(255,varying=true))
    /** Database column watcher_x SqlType(int4) */
    val watcherX: Rep[Int] = column[Int]("watcher_x")
    /** Database column watcher_y SqlType(int4) */
    val watcherY: Rep[Int] = column[Int]("watcher_y")
    /** Database column watcher_z SqlType(int4) */
    val watcherZ: Rep[Int] = column[Int]("watcher_z")
    /** Database column npc_object_id SqlType(int4) */
    val npcObjectId: Rep[Int] = column[Int]("npc_object_id")
    /** Database column npc_id SqlType(int4) */
    val npcId: Rep[Int] = column[Int]("npc_id")
    /** Database column npc_level SqlType(int4) */
    val npcLevel: Rep[Int] = column[Int]("npc_level")
    /** Database column npc_name SqlType(varchar), Length(255,true) */
    val npcName: Rep[String] = column[String]("npc_name", O.Length(255,varying=true))
    /** Database column npc_is_dead SqlType(bool) */
    val npcIsDead: Rep[Boolean] = column[Boolean]("npc_is_dead")
    /** Database column npc_x SqlType(int4) */
    val npcX: Rep[Int] = column[Int]("npc_x")
    /** Database column npc_y SqlType(int4) */
    val npcY: Rep[Int] = column[Int]("npc_y")
    /** Database column npc_z SqlType(int4) */
    val npcZ: Rep[Int] = column[Int]("npc_z")

    /** Uniqueness Index over (npcObjectId,tokenId) (database name npcs_spawns_obj_id_token_unique_key) */
    val index1 = index("npcs_spawns_obj_id_token_unique_key", (npcObjectId, tokenId), unique=true)
  }
  /** Collection-like TableQuery object for table NpcsSpawns */
  lazy val NpcsSpawns = new TableQuery(tag => new NpcsSpawns(tag))

  /** Entity class storing rows of table Players
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param serverId Database column server_id SqlType(uuid)
   *  @param objectId Database column object_id SqlType(int4)
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param lastSeen Database column last_seen SqlType(timestamp)
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param title Database column title SqlType(varchar), Length(255,true), Default(None)
   *  @param race Database column race SqlType(int4)
   *  @param sex Database column sex SqlType(int4)
   *  @param baseClass Database column base_class SqlType(int4)
   *  @param clanId Database column clan_id SqlType(int4), Default(None)
   *  @param isNoble Database column is_noble SqlType(bool)
   *  @param isHero Database column is_hero SqlType(bool)
   *  @param paperDolls Database column paper_dolls SqlType(varchar), Length(500,true)
   *  @param x Database column x SqlType(int4)
   *  @param y Database column y SqlType(int4)
   *  @param z Database column z SqlType(int4) */
  case class PlayersRow(id: java.util.UUID, serverId: java.util.UUID, objectId: Int, createDate: java.sql.Timestamp, lastSeen: java.sql.Timestamp, name: String, title: Option[String] = None, race: Int, sex: Int, baseClass: Int, clanId: Option[Int] = None, isNoble: Boolean, isHero: Boolean, paperDolls: String, x: Int, y: Int, z: Int)
  /** GetResult implicit for fetching PlayersRow objects using plain SQL queries */
  implicit def GetResultPlayersRow(implicit e0: GR[java.util.UUID], e1: GR[Int], e2: GR[java.sql.Timestamp], e3: GR[String], e4: GR[Option[String]], e5: GR[Option[Int]], e6: GR[Boolean]): GR[PlayersRow] = GR{
    prs => import prs._
    PlayersRow.tupled((<<[java.util.UUID], <<[java.util.UUID], <<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[String], <<?[String], <<[Int], <<[Int], <<[Int], <<?[Int], <<[Boolean], <<[Boolean], <<[String], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table players. Objects of this class serve as prototypes for rows in queries. */
  class Players(_tableTag: Tag) extends Table[PlayersRow](_tableTag, Some("scripts-dev"), "players") {
    def * = (id, serverId, objectId, createDate, lastSeen, name, title, race, sex, baseClass, clanId, isNoble, isHero, paperDolls, x, y, z) <> (PlayersRow.tupled, PlayersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(serverId), Rep.Some(objectId), Rep.Some(createDate), Rep.Some(lastSeen), Rep.Some(name), title, Rep.Some(race), Rep.Some(sex), Rep.Some(baseClass), clanId, Rep.Some(isNoble), Rep.Some(isHero), Rep.Some(paperDolls), Rep.Some(x), Rep.Some(y), Rep.Some(z)).shaped.<>({r=>import r._; _1.map(_=> PlayersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get, _10.get, _11, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column server_id SqlType(uuid) */
    val serverId: Rep[java.util.UUID] = column[java.util.UUID]("server_id")
    /** Database column object_id SqlType(int4) */
    val objectId: Rep[Int] = column[Int]("object_id")
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column last_seen SqlType(timestamp) */
    val lastSeen: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_seen")
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column title SqlType(varchar), Length(255,true), Default(None) */
    val title: Rep[Option[String]] = column[Option[String]]("title", O.Length(255,varying=true), O.Default(None))
    /** Database column race SqlType(int4) */
    val race: Rep[Int] = column[Int]("race")
    /** Database column sex SqlType(int4) */
    val sex: Rep[Int] = column[Int]("sex")
    /** Database column base_class SqlType(int4) */
    val baseClass: Rep[Int] = column[Int]("base_class")
    /** Database column clan_id SqlType(int4), Default(None) */
    val clanId: Rep[Option[Int]] = column[Option[Int]]("clan_id", O.Default(None))
    /** Database column is_noble SqlType(bool) */
    val isNoble: Rep[Boolean] = column[Boolean]("is_noble")
    /** Database column is_hero SqlType(bool) */
    val isHero: Rep[Boolean] = column[Boolean]("is_hero")
    /** Database column paper_dolls SqlType(varchar), Length(500,true) */
    val paperDolls: Rep[String] = column[String]("paper_dolls", O.Length(500,varying=true))
    /** Database column x SqlType(int4) */
    val x: Rep[Int] = column[Int]("x")
    /** Database column y SqlType(int4) */
    val y: Rep[Int] = column[Int]("y")
    /** Database column z SqlType(int4) */
    val z: Rep[Int] = column[Int]("z")

    /** Foreign key referencing Servers (database name players_server_id_fkey) */
    lazy val serversFk = foreignKey("players_server_id_fkey", serverId, Servers)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (name,serverId) (database name players_sever_id_name_unique_key) */
    val index1 = index("players_sever_id_name_unique_key", (name, serverId), unique=true)
    /** Uniqueness Index over (name,serverId) (database name players_sever_id_object_id_unique_key) */
    val index2 = index("players_sever_id_object_id_unique_key", (name, serverId), unique=true)
  }
  /** Collection-like TableQuery object for table Players */
  lazy val Players = new TableQuery(tag => new Players(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param hash Database column hash SqlType(varchar), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(timestamp)
   *  @param applyScript Database column apply_script SqlType(text), Default(None)
   *  @param revertScript Database column revert_script SqlType(text), Default(None)
   *  @param state Database column state SqlType(varchar), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem SqlType(text), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, Some("scripts-dev"), "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(varchar), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(timestamp) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(text), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Default(None))
    /** Database column revert_script SqlType(text), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O.Default(None))
    /** Database column state SqlType(varchar), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem SqlType(text), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table Servers
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param identifier Database column identifier SqlType(varchar), Length(255,true) */
  case class ServersRow(id: java.util.UUID, createDate: java.sql.Timestamp, name: String, identifier: String)
  /** GetResult implicit for fetching ServersRow objects using plain SQL queries */
  implicit def GetResultServersRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[String]): GR[ServersRow] = GR{
    prs => import prs._
    ServersRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[String], <<[String]))
  }
  /** Table description of table servers. Objects of this class serve as prototypes for rows in queries. */
  class Servers(_tableTag: Tag) extends Table[ServersRow](_tableTag, Some("scripts-dev"), "servers") {
    def * = (id, createDate, name, identifier) <> (ServersRow.tupled, ServersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(name), Rep.Some(identifier)).shaped.<>({r=>import r._; _1.map(_=> ServersRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column identifier SqlType(varchar), Length(255,true) */
    val identifier: Rep[String] = column[String]("identifier", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Servers */
  lazy val Servers = new TableQuery(tag => new Servers(tag))

  /** Entity class storing rows of table Subclasses
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param lastSeen Database column last_seen SqlType(timestamp)
   *  @param playerId Database column player_id SqlType(uuid)
   *  @param classId Database column class_id SqlType(int4) */
  case class SubclassesRow(id: java.util.UUID, createDate: java.sql.Timestamp, lastSeen: java.sql.Timestamp, playerId: java.util.UUID, classId: Int)
  /** GetResult implicit for fetching SubclassesRow objects using plain SQL queries */
  implicit def GetResultSubclassesRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[Int]): GR[SubclassesRow] = GR{
    prs => import prs._
    SubclassesRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[java.util.UUID], <<[Int]))
  }
  /** Table description of table subclasses. Objects of this class serve as prototypes for rows in queries. */
  class Subclasses(_tableTag: Tag) extends Table[SubclassesRow](_tableTag, Some("scripts-dev"), "subclasses") {
    def * = (id, createDate, lastSeen, playerId, classId) <> (SubclassesRow.tupled, SubclassesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(lastSeen), Rep.Some(playerId), Rep.Some(classId)).shaped.<>({r=>import r._; _1.map(_=> SubclassesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column last_seen SqlType(timestamp) */
    val lastSeen: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("last_seen")
    /** Database column player_id SqlType(uuid) */
    val playerId: Rep[java.util.UUID] = column[java.util.UUID]("player_id")
    /** Database column class_id SqlType(int4) */
    val classId: Rep[Int] = column[Int]("class_id")

    /** Foreign key referencing Players (database name subclasses_player_id_fkey) */
    lazy val playersFk = foreignKey("subclasses_player_id_fkey", playerId, Players)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Subclasses */
  lazy val Subclasses = new TableQuery(tag => new Subclasses(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(uuid), PrimaryKey
   *  @param createDate Database column create_date SqlType(timestamp)
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param apiToken Database column api_token SqlType(varchar), Length(255,true)
   *  @param telegram Database column telegram SqlType(int4), Default(None)
   *  @param email Database column email SqlType(varchar), Length(255,true)
   *  @param enabled Database column enabled SqlType(bool)
   *  @param password Database column password SqlType(varchar), Length(255,true)
   *  @param role Database column role SqlType(int4) */
  case class UsersRow(id: java.util.UUID, createDate: java.sql.Timestamp, name: String, apiToken: String, telegram: Option[Int] = None, email: String, enabled: Boolean, password: String, role: Int)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[java.util.UUID], e1: GR[java.sql.Timestamp], e2: GR[String], e3: GR[Option[Int]], e4: GR[Boolean], e5: GR[Int]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[java.util.UUID], <<[java.sql.Timestamp], <<[String], <<[String], <<?[Int], <<[String], <<[Boolean], <<[String], <<[Int]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, Some("scripts-dev"), "users") {
    def * = (id, createDate, name, apiToken, telegram, email, enabled, password, role) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createDate), Rep.Some(name), Rep.Some(apiToken), telegram, Rep.Some(email), Rep.Some(enabled), Rep.Some(password), Rep.Some(role)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(uuid), PrimaryKey */
    val id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    /** Database column create_date SqlType(timestamp) */
    val createDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("create_date")
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column api_token SqlType(varchar), Length(255,true) */
    val apiToken: Rep[String] = column[String]("api_token", O.Length(255,varying=true))
    /** Database column telegram SqlType(int4), Default(None) */
    val telegram: Rep[Option[Int]] = column[Option[Int]]("telegram", O.Default(None))
    /** Database column email SqlType(varchar), Length(255,true) */
    val email: Rep[String] = column[String]("email", O.Length(255,varying=true))
    /** Database column enabled SqlType(bool) */
    val enabled: Rep[Boolean] = column[Boolean]("enabled")
    /** Database column password SqlType(varchar), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column role SqlType(int4) */
    val role: Rep[Int] = column[Int]("role")

    /** Uniqueness Index over (email) (database name uk_users_email) */
    val index1 = index("uk_users_email", email, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))

  implicit class QueryExtension[T, E](q: Query[T, E, Seq]) extends ODataSupported {
    def page(offset: Long, limit: Long): Query[T, E, Seq] = q.drop(offset).take(limit)
    def page(implicit request: play.api.mvc.Request[_]): Query[T, E, Seq] = q.page(offset, limit)
  }

  
  trait ODataSupported {
    protected def offset(implicit request: play.api.mvc.Request[_]) = decodedQueryString("offset").map(_.toLong).getOrElse(0L)
    protected def limit(implicit request: play.api.mvc.Request[_]) = decodedQueryString("limit").map(_.toLong).getOrElse(50L)
    protected def filter(implicit request: play.api.mvc.Request[_]) = decodedQueryString("$filter")
    protected def orderBy(implicit request: play.api.mvc.Request[_]) = decodedQueryString("$orderby")
    protected def decodedQueryString(key: String)(implicit request: play.api.mvc.Request[_]) = request.getQueryString(key).map(java.net.URLDecoder.decode(_, "UTF-8"))
    protected def decodedQueryStrings(key: String)(implicit request: play.api.mvc.Request[_]) = request.queryString.getOrElse(key, Seq.empty).map(java.net.URLDecoder.decode(_, "UTF-8"))
  }

  implicit class BrokerNotificationsExtension(query: Query[BrokerNotifications, BrokerNotifications#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val countGtRegex = "count gt ([\\d]{1,})".r
          val countLtRegex = "count lt ([\\d]{1,})".r
          val countEqRegex = "count eq ([\\d]{1,})".r
          val itemIdGtRegex = "itemId gt ([\\d]{1,})".r
          val itemIdLtRegex = "itemId lt ([\\d]{1,})".r
          val itemIdEqRegex = "itemId eq ([\\d]{1,})".r
          val attributeTypeGtRegex = "attributeType gt ([\\d]{1,})".r
          val attributeTypeLtRegex = "attributeType lt ([\\d]{1,})".r
          val attributeTypeEqRegex = "attributeType eq ([\\d]{1,})".r
          val priceGtRegex = "price gt ([\\d]{1,})".r
          val priceLtRegex = "price lt ([\\d]{1,})".r
          val priceEqRegex = "price eq ([\\d]{1,})".r
          val attributeValueGtRegex = "attributeValue gt ([\\d]{1,})".r
          val attributeValueLtRegex = "attributeValue lt ([\\d]{1,})".r
          val attributeValueEqRegex = "attributeValue eq ([\\d]{1,})".r
          val enchantGtRegex = "enchant gt ([\\d]{1,})".r
          val enchantLtRegex = "enchant lt ([\\d]{1,})".r
          val enchantEqRegex = "enchant eq ([\\d]{1,})".r
          filter match {
            case countGtRegex(x) => query.filter(_.count.filter(_ >= x.toLong).isDefined)
            case countLtRegex(x) => query.filter(_.count.filter(_ <= x.toLong).isDefined)
            case countEqRegex(x) => query.filter(_.count.filter(_ === x.toLong).isDefined)
            case itemIdGtRegex(x) => query.filter(_.itemId >= x.toInt)
            case itemIdLtRegex(x) => query.filter(_.itemId <= x.toInt)
            case itemIdEqRegex(x) => query.filter(_.itemId === x.toInt)
            case attributeTypeGtRegex(x) => query.filter(_.attributeType.filter(_ >= x.toInt).isDefined)
            case attributeTypeLtRegex(x) => query.filter(_.attributeType.filter(_ <= x.toInt).isDefined)
            case attributeTypeEqRegex(x) => query.filter(_.attributeType.filter(_ === x.toInt).isDefined)
            case priceGtRegex(x) => query.filter(_.price.filter(_ >= x.toLong).isDefined)
            case priceLtRegex(x) => query.filter(_.price.filter(_ <= x.toLong).isDefined)
            case priceEqRegex(x) => query.filter(_.price.filter(_ === x.toLong).isDefined)
            case attributeValueGtRegex(x) => query.filter(_.attributeValue.filter(_ >= x.toInt).isDefined)
            case attributeValueLtRegex(x) => query.filter(_.attributeValue.filter(_ <= x.toInt).isDefined)
            case attributeValueEqRegex(x) => query.filter(_.attributeValue.filter(_ === x.toInt).isDefined)
            case enchantGtRegex(x) => query.filter(_.enchant.filter(_ >= x.toInt).isDefined)
            case enchantLtRegex(x) => query.filter(_.enchant.filter(_ <= x.toInt).isDefined)
            case enchantEqRegex(x) => query.filter(_.enchant.filter(_ === x.toInt).isDefined)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[BrokerNotifications,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val countRegex = "count (asc|desc)".r
          val itemIdRegex = "itemId (asc|desc)".r
          val attributeTypeRegex = "attributeType (asc|desc)".r
          val priceRegex = "price (asc|desc)".r
          val enabledRegex = "enabled (asc|desc)".r
          val updateDateRegex = "updateDate (asc|desc)".r
          val userIdRegex = "userId (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val attributeValueRegex = "attributeValue (asc|desc)".r
          val enchantRegex = "enchant (asc|desc)".r
          orderBy match {
            case countRegex(x) => Some(q => if (x == "asc") q.count.asc else q.count.desc)
            case itemIdRegex(x) => Some(q => if (x == "asc") q.itemId.asc else q.itemId.desc)
            case attributeTypeRegex(x) => Some(q => if (x == "asc") q.attributeType.asc else q.attributeType.desc)
            case priceRegex(x) => Some(q => if (x == "asc") q.price.asc else q.price.desc)
            case enabledRegex(x) => Some(q => if (x == "asc") q.enabled.asc else q.enabled.desc)
            case updateDateRegex(x) => Some(q => if (x == "asc") q.updateDate.asc else q.updateDate.desc)
            case userIdRegex(x) => Some(q => if (x == "asc") q.userId.asc else q.userId.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case attributeValueRegex(x) => Some(q => if (x == "asc") q.attributeValue.asc else q.attributeValue.desc)
            case enchantRegex(x) => Some(q => if (x == "asc") q.enchant.asc else q.enchant.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class DroppedItemsExtension(query: Query[DroppedItems, DroppedItems#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val countGtRegex = "count gt ([\\d]{1,})".r
          val countLtRegex = "count lt ([\\d]{1,})".r
          val countEqRegex = "count eq ([\\d]{1,})".r
          val itemIdGtRegex = "itemId gt ([\\d]{1,})".r
          val itemIdLtRegex = "itemId lt ([\\d]{1,})".r
          val itemIdEqRegex = "itemId eq ([\\d]{1,})".r
          val itemYGtRegex = "itemY gt ([\\d]{1,})".r
          val itemYLtRegex = "itemY lt ([\\d]{1,})".r
          val itemYEqRegex = "itemY eq ([\\d]{1,})".r
          val itemXGtRegex = "itemX gt ([\\d]{1,})".r
          val itemXLtRegex = "itemX lt ([\\d]{1,})".r
          val itemXEqRegex = "itemX eq ([\\d]{1,})".r
          val dropperIdGtRegex = "dropperId gt ([\\d]{1,})".r
          val dropperIdLtRegex = "dropperId lt ([\\d]{1,})".r
          val dropperIdEqRegex = "dropperId eq ([\\d]{1,})".r
          val itemObjectIdGtRegex = "itemObjectId gt ([\\d]{1,})".r
          val itemObjectIdLtRegex = "itemObjectId lt ([\\d]{1,})".r
          val itemObjectIdEqRegex = "itemObjectId eq ([\\d]{1,})".r
          val itemZGtRegex = "itemZ gt ([\\d]{1,})".r
          val itemZLtRegex = "itemZ lt ([\\d]{1,})".r
          val itemZEqRegex = "itemZ eq ([\\d]{1,})".r
          filter match {
            case countGtRegex(x) => query.filter(_.count >= x.toLong)
            case countLtRegex(x) => query.filter(_.count <= x.toLong)
            case countEqRegex(x) => query.filter(_.count === x.toLong)
            case itemIdGtRegex(x) => query.filter(_.itemId >= x.toInt)
            case itemIdLtRegex(x) => query.filter(_.itemId <= x.toInt)
            case itemIdEqRegex(x) => query.filter(_.itemId === x.toInt)
            case itemYGtRegex(x) => query.filter(_.itemY >= x.toInt)
            case itemYLtRegex(x) => query.filter(_.itemY <= x.toInt)
            case itemYEqRegex(x) => query.filter(_.itemY === x.toInt)
            case itemXGtRegex(x) => query.filter(_.itemX >= x.toInt)
            case itemXLtRegex(x) => query.filter(_.itemX <= x.toInt)
            case itemXEqRegex(x) => query.filter(_.itemX === x.toInt)
            case dropperIdGtRegex(x) => query.filter(_.dropperId >= x.toInt)
            case dropperIdLtRegex(x) => query.filter(_.dropperId <= x.toInt)
            case dropperIdEqRegex(x) => query.filter(_.dropperId === x.toInt)
            case itemObjectIdGtRegex(x) => query.filter(_.itemObjectId >= x.toInt)
            case itemObjectIdLtRegex(x) => query.filter(_.itemObjectId <= x.toInt)
            case itemObjectIdEqRegex(x) => query.filter(_.itemObjectId === x.toInt)
            case itemZGtRegex(x) => query.filter(_.itemZ >= x.toInt)
            case itemZLtRegex(x) => query.filter(_.itemZ <= x.toInt)
            case itemZEqRegex(x) => query.filter(_.itemZ === x.toInt)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[DroppedItems,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val countRegex = "count (asc|desc)".r
          val itemIdRegex = "itemId (asc|desc)".r
          val itemYRegex = "itemY (asc|desc)".r
          val stackableRegex = "stackable (asc|desc)".r
          val itemXRegex = "itemX (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val tokenIdRegex = "tokenId (asc|desc)".r
          val dropperIdRegex = "dropperId (asc|desc)".r
          val itemObjectIdRegex = "itemObjectId (asc|desc)".r
          val itemZRegex = "itemZ (asc|desc)".r
          orderBy match {
            case countRegex(x) => Some(q => if (x == "asc") q.count.asc else q.count.desc)
            case itemIdRegex(x) => Some(q => if (x == "asc") q.itemId.asc else q.itemId.desc)
            case itemYRegex(x) => Some(q => if (x == "asc") q.itemY.asc else q.itemY.desc)
            case stackableRegex(x) => Some(q => if (x == "asc") q.stackable.asc else q.stackable.desc)
            case itemXRegex(x) => Some(q => if (x == "asc") q.itemX.asc else q.itemX.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case tokenIdRegex(x) => Some(q => if (x == "asc") q.tokenId.asc else q.tokenId.desc)
            case dropperIdRegex(x) => Some(q => if (x == "asc") q.dropperId.asc else q.dropperId.desc)
            case itemObjectIdRegex(x) => Some(q => if (x == "asc") q.itemObjectId.asc else q.itemObjectId.desc)
            case itemZRegex(x) => Some(q => if (x == "asc") q.itemZ.asc else q.itemZ.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class ItemsOnBuyExtension(query: Query[ItemsOnBuy, ItemsOnBuy#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val countGtRegex = "count gt ([\\d]{1,})".r
          val countLtRegex = "count lt ([\\d]{1,})".r
          val countEqRegex = "count eq ([\\d]{1,})".r
          val itemIdGtRegex = "itemId gt ([\\d]{1,})".r
          val itemIdLtRegex = "itemId lt ([\\d]{1,})".r
          val itemIdEqRegex = "itemId eq ([\\d]{1,})".r
          val storePriceGtRegex = "storePrice gt ([\\d]{1,})".r
          val storePriceLtRegex = "storePrice lt ([\\d]{1,})".r
          val storePriceEqRegex = "storePrice eq ([\\d]{1,})".r
          val customerPriceGtRegex = "customerPrice gt ([\\d]{1,})".r
          val customerPriceLtRegex = "customerPrice lt ([\\d]{1,})".r
          val customerPriceEqRegex = "customerPrice eq ([\\d]{1,})".r
          val itemObjectIdGtRegex = "itemObjectId gt ([\\d]{1,})".r
          val itemObjectIdLtRegex = "itemObjectId lt ([\\d]{1,})".r
          val itemObjectIdEqRegex = "itemObjectId eq ([\\d]{1,})".r
          filter match {
            case countGtRegex(x) => query.filter(_.count >= x.toLong)
            case countLtRegex(x) => query.filter(_.count <= x.toLong)
            case countEqRegex(x) => query.filter(_.count === x.toLong)
            case itemIdGtRegex(x) => query.filter(_.itemId >= x.toInt)
            case itemIdLtRegex(x) => query.filter(_.itemId <= x.toInt)
            case itemIdEqRegex(x) => query.filter(_.itemId === x.toInt)
            case storePriceGtRegex(x) => query.filter(_.storePrice >= x.toLong)
            case storePriceLtRegex(x) => query.filter(_.storePrice <= x.toLong)
            case storePriceEqRegex(x) => query.filter(_.storePrice === x.toLong)
            case customerPriceGtRegex(x) => query.filter(_.customerPrice >= x.toLong)
            case customerPriceLtRegex(x) => query.filter(_.customerPrice <= x.toLong)
            case customerPriceEqRegex(x) => query.filter(_.customerPrice === x.toLong)
            case itemObjectIdGtRegex(x) => query.filter(_.itemObjectId >= x.toInt)
            case itemObjectIdLtRegex(x) => query.filter(_.itemObjectId <= x.toInt)
            case itemObjectIdEqRegex(x) => query.filter(_.itemObjectId === x.toInt)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[ItemsOnBuy,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val countRegex = "count (asc|desc)".r
          val itemIdRegex = "itemId (asc|desc)".r
          val customerIdRegex = "customerId (asc|desc)".r
          val storePriceRegex = "storePrice (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val customerPriceRegex = "customerPrice (asc|desc)".r
          val itemObjectIdRegex = "itemObjectId (asc|desc)".r
          val actualRegex = "actual (asc|desc)".r
          orderBy match {
            case countRegex(x) => Some(q => if (x == "asc") q.count.asc else q.count.desc)
            case itemIdRegex(x) => Some(q => if (x == "asc") q.itemId.asc else q.itemId.desc)
            case customerIdRegex(x) => Some(q => if (x == "asc") q.customerId.asc else q.customerId.desc)
            case storePriceRegex(x) => Some(q => if (x == "asc") q.storePrice.asc else q.storePrice.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case customerPriceRegex(x) => Some(q => if (x == "asc") q.customerPrice.asc else q.customerPrice.desc)
            case itemObjectIdRegex(x) => Some(q => if (x == "asc") q.itemObjectId.asc else q.itemObjectId.desc)
            case actualRegex(x) => Some(q => if (x == "asc") q.actual.asc else q.actual.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class ItemsOnSellExtension(query: Query[ItemsOnSell, ItemsOnSell#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val countGtRegex = "count gt ([\\d]{1,})".r
          val countLtRegex = "count lt ([\\d]{1,})".r
          val countEqRegex = "count eq ([\\d]{1,})".r
          val itemIdGtRegex = "itemId gt ([\\d]{1,})".r
          val itemIdLtRegex = "itemId lt ([\\d]{1,})".r
          val itemIdEqRegex = "itemId eq ([\\d]{1,})".r
          val storePriceGtRegex = "storePrice gt ([\\d]{1,})".r
          val storePriceLtRegex = "storePrice lt ([\\d]{1,})".r
          val storePriceEqRegex = "storePrice eq ([\\d]{1,})".r
          val defencewindGtRegex = "defencewind gt ([\\d]{1,})".r
          val defencewindLtRegex = "defencewind lt ([\\d]{1,})".r
          val defencewindEqRegex = "defencewind eq ([\\d]{1,})".r
          val attackelementvalueGtRegex = "attackelementvalue gt ([\\d]{1,})".r
          val attackelementvalueLtRegex = "attackelementvalue lt ([\\d]{1,})".r
          val attackelementvalueEqRegex = "attackelementvalue eq ([\\d]{1,})".r
          val defenceholyGtRegex = "defenceholy gt ([\\d]{1,})".r
          val defenceholyLtRegex = "defenceholy lt ([\\d]{1,})".r
          val defenceholyEqRegex = "defenceholy eq ([\\d]{1,})".r
          val defenceearthGtRegex = "defenceearth gt ([\\d]{1,})".r
          val defenceearthLtRegex = "defenceearth lt ([\\d]{1,})".r
          val defenceearthEqRegex = "defenceearth eq ([\\d]{1,})".r
          val defenceunholyGtRegex = "defenceunholy gt ([\\d]{1,})".r
          val defenceunholyLtRegex = "defenceunholy lt ([\\d]{1,})".r
          val defenceunholyEqRegex = "defenceunholy eq ([\\d]{1,})".r
          val attackelementidGtRegex = "attackelementid gt ([\\d]{1,})".r
          val attackelementidLtRegex = "attackelementid lt ([\\d]{1,})".r
          val attackelementidEqRegex = "attackelementid eq ([\\d]{1,})".r
          val itemObjectIdGtRegex = "itemObjectId gt ([\\d]{1,})".r
          val itemObjectIdLtRegex = "itemObjectId lt ([\\d]{1,})".r
          val itemObjectIdEqRegex = "itemObjectId eq ([\\d]{1,})".r
          val defensewaterGtRegex = "defensewater gt ([\\d]{1,})".r
          val defensewaterLtRegex = "defensewater lt ([\\d]{1,})".r
          val defensewaterEqRegex = "defensewater eq ([\\d]{1,})".r
          val enchantGtRegex = "enchant gt ([\\d]{1,})".r
          val enchantLtRegex = "enchant lt ([\\d]{1,})".r
          val enchantEqRegex = "enchant eq ([\\d]{1,})".r
          val defensefireGtRegex = "defensefire gt ([\\d]{1,})".r
          val defensefireLtRegex = "defensefire lt ([\\d]{1,})".r
          val defensefireEqRegex = "defensefire eq ([\\d]{1,})".r
          val ownersPriceGtRegex = "ownersPrice gt ([\\d]{1,})".r
          val ownersPriceLtRegex = "ownersPrice lt ([\\d]{1,})".r
          val ownersPriceEqRegex = "ownersPrice eq ([\\d]{1,})".r
          filter match {
            case countGtRegex(x) => query.filter(_.count >= x.toLong)
            case countLtRegex(x) => query.filter(_.count <= x.toLong)
            case countEqRegex(x) => query.filter(_.count === x.toLong)
            case itemIdGtRegex(x) => query.filter(_.itemId >= x.toInt)
            case itemIdLtRegex(x) => query.filter(_.itemId <= x.toInt)
            case itemIdEqRegex(x) => query.filter(_.itemId === x.toInt)
            case storePriceGtRegex(x) => query.filter(_.storePrice >= x.toLong)
            case storePriceLtRegex(x) => query.filter(_.storePrice <= x.toLong)
            case storePriceEqRegex(x) => query.filter(_.storePrice === x.toLong)
            case defencewindGtRegex(x) => query.filter(_.defencewind >= x.toInt)
            case defencewindLtRegex(x) => query.filter(_.defencewind <= x.toInt)
            case defencewindEqRegex(x) => query.filter(_.defencewind === x.toInt)
            case attackelementvalueGtRegex(x) => query.filter(_.attackelementvalue >= x.toInt)
            case attackelementvalueLtRegex(x) => query.filter(_.attackelementvalue <= x.toInt)
            case attackelementvalueEqRegex(x) => query.filter(_.attackelementvalue === x.toInt)
            case defenceholyGtRegex(x) => query.filter(_.defenceholy >= x.toInt)
            case defenceholyLtRegex(x) => query.filter(_.defenceholy <= x.toInt)
            case defenceholyEqRegex(x) => query.filter(_.defenceholy === x.toInt)
            case defenceearthGtRegex(x) => query.filter(_.defenceearth >= x.toInt)
            case defenceearthLtRegex(x) => query.filter(_.defenceearth <= x.toInt)
            case defenceearthEqRegex(x) => query.filter(_.defenceearth === x.toInt)
            case defenceunholyGtRegex(x) => query.filter(_.defenceunholy >= x.toInt)
            case defenceunholyLtRegex(x) => query.filter(_.defenceunholy <= x.toInt)
            case defenceunholyEqRegex(x) => query.filter(_.defenceunholy === x.toInt)
            case attackelementidGtRegex(x) => query.filter(_.attackelementid >= x.toInt)
            case attackelementidLtRegex(x) => query.filter(_.attackelementid <= x.toInt)
            case attackelementidEqRegex(x) => query.filter(_.attackelementid === x.toInt)
            case itemObjectIdGtRegex(x) => query.filter(_.itemObjectId >= x.toInt)
            case itemObjectIdLtRegex(x) => query.filter(_.itemObjectId <= x.toInt)
            case itemObjectIdEqRegex(x) => query.filter(_.itemObjectId === x.toInt)
            case defensewaterGtRegex(x) => query.filter(_.defensewater >= x.toInt)
            case defensewaterLtRegex(x) => query.filter(_.defensewater <= x.toInt)
            case defensewaterEqRegex(x) => query.filter(_.defensewater === x.toInt)
            case enchantGtRegex(x) => query.filter(_.enchant >= x.toInt)
            case enchantLtRegex(x) => query.filter(_.enchant <= x.toInt)
            case enchantEqRegex(x) => query.filter(_.enchant === x.toInt)
            case defensefireGtRegex(x) => query.filter(_.defensefire >= x.toInt)
            case defensefireLtRegex(x) => query.filter(_.defensefire <= x.toInt)
            case defensefireEqRegex(x) => query.filter(_.defensefire === x.toInt)
            case ownersPriceGtRegex(x) => query.filter(_.ownersPrice >= x.toLong)
            case ownersPriceLtRegex(x) => query.filter(_.ownersPrice <= x.toLong)
            case ownersPriceEqRegex(x) => query.filter(_.ownersPrice === x.toLong)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[ItemsOnSell,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val countRegex = "count (asc|desc)".r
          val itemIdRegex = "itemId (asc|desc)".r
          val storePriceRegex = "storePrice (asc|desc)".r
          val defencewindRegex = "defencewind (asc|desc)".r
          val attackelementvalueRegex = "attackelementvalue (asc|desc)".r
          val defenceholyRegex = "defenceholy (asc|desc)".r
          val isPackageRegex = "isPackage (asc|desc)".r
          val defenceearthRegex = "defenceearth (asc|desc)".r
          val defenceunholyRegex = "defenceunholy (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val sellerIdRegex = "sellerId (asc|desc)".r
          val attackelementidRegex = "attackelementid (asc|desc)".r
          val itemObjectIdRegex = "itemObjectId (asc|desc)".r
          val defensewaterRegex = "defensewater (asc|desc)".r
          val actualRegex = "actual (asc|desc)".r
          val enchantRegex = "enchant (asc|desc)".r
          val defensefireRegex = "defensefire (asc|desc)".r
          val ownersPriceRegex = "ownersPrice (asc|desc)".r
          orderBy match {
            case countRegex(x) => Some(q => if (x == "asc") q.count.asc else q.count.desc)
            case itemIdRegex(x) => Some(q => if (x == "asc") q.itemId.asc else q.itemId.desc)
            case storePriceRegex(x) => Some(q => if (x == "asc") q.storePrice.asc else q.storePrice.desc)
            case defencewindRegex(x) => Some(q => if (x == "asc") q.defencewind.asc else q.defencewind.desc)
            case attackelementvalueRegex(x) => Some(q => if (x == "asc") q.attackelementvalue.asc else q.attackelementvalue.desc)
            case defenceholyRegex(x) => Some(q => if (x == "asc") q.defenceholy.asc else q.defenceholy.desc)
            case isPackageRegex(x) => Some(q => if (x == "asc") q.isPackage.asc else q.isPackage.desc)
            case defenceearthRegex(x) => Some(q => if (x == "asc") q.defenceearth.asc else q.defenceearth.desc)
            case defenceunholyRegex(x) => Some(q => if (x == "asc") q.defenceunholy.asc else q.defenceunholy.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case sellerIdRegex(x) => Some(q => if (x == "asc") q.sellerId.asc else q.sellerId.desc)
            case attackelementidRegex(x) => Some(q => if (x == "asc") q.attackelementid.asc else q.attackelementid.desc)
            case itemObjectIdRegex(x) => Some(q => if (x == "asc") q.itemObjectId.asc else q.itemObjectId.desc)
            case defensewaterRegex(x) => Some(q => if (x == "asc") q.defensewater.asc else q.defensewater.desc)
            case actualRegex(x) => Some(q => if (x == "asc") q.actual.asc else q.actual.desc)
            case enchantRegex(x) => Some(q => if (x == "asc") q.enchant.asc else q.enchant.desc)
            case defensefireRegex(x) => Some(q => if (x == "asc") q.defensefire.asc else q.defensefire.desc)
            case ownersPriceRegex(x) => Some(q => if (x == "asc") q.ownersPrice.asc else q.ownersPrice.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class ItemTemplatesExtension(query: Query[ItemTemplates, ItemTemplates#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val keepTypeGtRegex = "keepType gt ([\\d]{1,})".r
          val keepTypeLtRegex = "keepType lt ([\\d]{1,})".r
          val keepTypeEqRegex = "keepType eq ([\\d]{1,})".r
          val crystalTypeGtRegex = "crystalType gt ([\\d]{1,})".r
          val crystalTypeLtRegex = "crystalType lt ([\\d]{1,})".r
          val crystalTypeEqRegex = "crystalType eq ([\\d]{1,})".r
          val weightGtRegex = "weight gt ([\\d]{1,})".r
          val weightLtRegex = "weight lt ([\\d]{1,})".r
          val weightEqRegex = "weight eq ([\\d]{1,})".r
          val nameRegex = "contains\\(name,'([\\w\\d].*)'\\)".r
          val itemIdGtRegex = "itemId gt ([\\d]{1,})".r
          val itemIdLtRegex = "itemId lt ([\\d]{1,})".r
          val itemIdEqRegex = "itemId eq ([\\d]{1,})".r
          val physicalDamageGtRegex = "physicalDamage gt ([\\d]{1,})".r
          val physicalDamageLtRegex = "physicalDamage lt ([\\d]{1,})".r
          val physicalDamageEqRegex = "physicalDamage eq ([\\d]{1,})".r
          val descriptionRegex = "contains\\(description,'([\\w\\d].*)'\\)".r
          val armorTypeGtRegex = "armorType gt ([\\d]{1,})".r
          val armorTypeLtRegex = "armorType lt ([\\d]{1,})".r
          val armorTypeEqRegex = "armorType eq ([\\d]{1,})".r
          val crystalCountGtRegex = "crystalCount gt ([\\d]{1,})".r
          val crystalCountLtRegex = "crystalCount lt ([\\d]{1,})".r
          val crystalCountEqRegex = "crystalCount eq ([\\d]{1,})".r
          val priceGtRegex = "price gt ([\\d]{1,})".r
          val priceLtRegex = "price lt ([\\d]{1,})".r
          val priceEqRegex = "price eq ([\\d]{1,})".r
          val etcitemTypeGtRegex = "etcitemType gt ([\\d]{1,})".r
          val etcitemTypeLtRegex = "etcitemType lt ([\\d]{1,})".r
          val etcitemTypeEqRegex = "etcitemType eq ([\\d]{1,})".r
          val defaultPriceGtRegex = "defaultPrice gt ([\\d]{1,})".r
          val defaultPriceLtRegex = "defaultPrice lt ([\\d]{1,})".r
          val defaultPriceEqRegex = "defaultPrice eq ([\\d]{1,})".r
          val weaponTypeGtRegex = "weaponType gt ([\\d]{1,})".r
          val weaponTypeLtRegex = "weaponType lt ([\\d]{1,})".r
          val weaponTypeEqRegex = "weaponType eq ([\\d]{1,})".r
          val materialTypeGtRegex = "materialType gt ([\\d]{1,})".r
          val materialTypeLtRegex = "materialType lt ([\\d]{1,})".r
          val materialTypeEqRegex = "materialType eq ([\\d]{1,})".r
          val magicalDamageGtRegex = "magicalDamage gt ([\\d]{1,})".r
          val magicalDamageLtRegex = "magicalDamage lt ([\\d]{1,})".r
          val magicalDamageEqRegex = "magicalDamage eq ([\\d]{1,})".r
          val slotBitTypeGtRegex = "slotBitType gt ([\\d]{1,})".r
          val slotBitTypeLtRegex = "slotBitType lt ([\\d]{1,})".r
          val slotBitTypeEqRegex = "slotBitType eq ([\\d]{1,})".r
          val itemTypeGtRegex = "itemType gt ([\\d]{1,})".r
          val itemTypeLtRegex = "itemType lt ([\\d]{1,})".r
          val itemTypeEqRegex = "itemType eq ([\\d]{1,})".r
          val recipeIdGtRegex = "recipeId gt ([\\d]{1,})".r
          val recipeIdLtRegex = "recipeId lt ([\\d]{1,})".r
          val recipeIdEqRegex = "recipeId eq ([\\d]{1,})".r
          filter match {
            case keepTypeGtRegex(x) => query.filter(_.keepType.filter(_ >= x.toInt).isDefined)
            case keepTypeLtRegex(x) => query.filter(_.keepType.filter(_ <= x.toInt).isDefined)
            case keepTypeEqRegex(x) => query.filter(_.keepType.filter(_ === x.toInt).isDefined)
            case crystalTypeGtRegex(x) => query.filter(_.crystalType.filter(_ >= x.toInt).isDefined)
            case crystalTypeLtRegex(x) => query.filter(_.crystalType.filter(_ <= x.toInt).isDefined)
            case crystalTypeEqRegex(x) => query.filter(_.crystalType.filter(_ === x.toInt).isDefined)
            case weightGtRegex(x) => query.filter(_.weight.filter(_ >= x.toInt).isDefined)
            case weightLtRegex(x) => query.filter(_.weight.filter(_ <= x.toInt).isDefined)
            case weightEqRegex(x) => query.filter(_.weight.filter(_ === x.toInt).isDefined)
            case nameRegex(x) => query.filter(_.name.toLowerCase like s"%${x.toLowerCase}%")
            case itemIdGtRegex(x) => query.filter(_.itemId >= x.toInt)
            case itemIdLtRegex(x) => query.filter(_.itemId <= x.toInt)
            case itemIdEqRegex(x) => query.filter(_.itemId === x.toInt)
            case physicalDamageGtRegex(x) => query.filter(_.physicalDamage.filter(_ >= x.toInt).isDefined)
            case physicalDamageLtRegex(x) => query.filter(_.physicalDamage.filter(_ <= x.toInt).isDefined)
            case physicalDamageEqRegex(x) => query.filter(_.physicalDamage.filter(_ === x.toInt).isDefined)
            case descriptionRegex(x) => query.filter(_.description.filter(_.toLowerCase like s"%${x.toLowerCase}%").isDefined)
            case armorTypeGtRegex(x) => query.filter(_.armorType.filter(_ >= x.toInt).isDefined)
            case armorTypeLtRegex(x) => query.filter(_.armorType.filter(_ <= x.toInt).isDefined)
            case armorTypeEqRegex(x) => query.filter(_.armorType.filter(_ === x.toInt).isDefined)
            case crystalCountGtRegex(x) => query.filter(_.crystalCount.filter(_ >= x.toInt).isDefined)
            case crystalCountLtRegex(x) => query.filter(_.crystalCount.filter(_ <= x.toInt).isDefined)
            case crystalCountEqRegex(x) => query.filter(_.crystalCount.filter(_ === x.toInt).isDefined)
            case priceGtRegex(x) => query.filter(_.price.filter(_ >= x.toLong).isDefined)
            case priceLtRegex(x) => query.filter(_.price.filter(_ <= x.toLong).isDefined)
            case priceEqRegex(x) => query.filter(_.price.filter(_ === x.toLong).isDefined)
            case etcitemTypeGtRegex(x) => query.filter(_.etcitemType.filter(_ >= x.toInt).isDefined)
            case etcitemTypeLtRegex(x) => query.filter(_.etcitemType.filter(_ <= x.toInt).isDefined)
            case etcitemTypeEqRegex(x) => query.filter(_.etcitemType.filter(_ === x.toInt).isDefined)
            case defaultPriceGtRegex(x) => query.filter(_.defaultPrice.filter(_ >= x.toLong).isDefined)
            case defaultPriceLtRegex(x) => query.filter(_.defaultPrice.filter(_ <= x.toLong).isDefined)
            case defaultPriceEqRegex(x) => query.filter(_.defaultPrice.filter(_ === x.toLong).isDefined)
            case weaponTypeGtRegex(x) => query.filter(_.weaponType.filter(_ >= x.toInt).isDefined)
            case weaponTypeLtRegex(x) => query.filter(_.weaponType.filter(_ <= x.toInt).isDefined)
            case weaponTypeEqRegex(x) => query.filter(_.weaponType.filter(_ === x.toInt).isDefined)
            case materialTypeGtRegex(x) => query.filter(_.materialType.filter(_ >= x.toInt).isDefined)
            case materialTypeLtRegex(x) => query.filter(_.materialType.filter(_ <= x.toInt).isDefined)
            case materialTypeEqRegex(x) => query.filter(_.materialType.filter(_ === x.toInt).isDefined)
            case magicalDamageGtRegex(x) => query.filter(_.magicalDamage.filter(_ >= x.toInt).isDefined)
            case magicalDamageLtRegex(x) => query.filter(_.magicalDamage.filter(_ <= x.toInt).isDefined)
            case magicalDamageEqRegex(x) => query.filter(_.magicalDamage.filter(_ === x.toInt).isDefined)
            case slotBitTypeGtRegex(x) => query.filter(_.slotBitType.filter(_ >= x.toInt).isDefined)
            case slotBitTypeLtRegex(x) => query.filter(_.slotBitType.filter(_ <= x.toInt).isDefined)
            case slotBitTypeEqRegex(x) => query.filter(_.slotBitType.filter(_ === x.toInt).isDefined)
            case itemTypeGtRegex(x) => query.filter(_.itemType.filter(_ >= x.toInt).isDefined)
            case itemTypeLtRegex(x) => query.filter(_.itemType.filter(_ <= x.toInt).isDefined)
            case itemTypeEqRegex(x) => query.filter(_.itemType.filter(_ === x.toInt).isDefined)
            case recipeIdGtRegex(x) => query.filter(_.recipeId.filter(_ >= x.toInt).isDefined)
            case recipeIdLtRegex(x) => query.filter(_.recipeId.filter(_ <= x.toInt).isDefined)
            case recipeIdEqRegex(x) => query.filter(_.recipeId.filter(_ === x.toInt).isDefined)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[ItemTemplates,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val keepTypeRegex = "keepType (asc|desc)".r
          val crystalTypeRegex = "crystalType (asc|desc)".r
          val weightRegex = "weight (asc|desc)".r
          val nameRegex = "name (asc|desc)".r
          val itemIdRegex = "itemId (asc|desc)".r
          val physicalDamageRegex = "physicalDamage (asc|desc)".r
          val isPrivateStoreRegex = "isPrivateStore (asc|desc)".r
          val descriptionRegex = "description (asc|desc)".r
          val armorTypeRegex = "armorType (asc|desc)".r
          val crystalCountRegex = "crystalCount (asc|desc)".r
          val priceRegex = "price (asc|desc)".r
          val magicWeaponRegex = "magicWeapon (asc|desc)".r
          val etcitemTypeRegex = "etcitemType (asc|desc)".r
          val isTradeRegex = "isTrade (asc|desc)".r
          val elementalEnableRegex = "elementalEnable (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val defaultPriceRegex = "defaultPrice (asc|desc)".r
          val weaponTypeRegex = "weaponType (asc|desc)".r
          val materialTypeRegex = "materialType (asc|desc)".r
          val magicalDamageRegex = "magicalDamage (asc|desc)".r
          val isOlympiadCanUseRegex = "isOlympiadCanUse (asc|desc)".r
          val canMoveRegex = "canMove (asc|desc)".r
          val slotBitTypeRegex = "slotBitType (asc|desc)".r
          val itemTypeRegex = "itemType (asc|desc)".r
          val isDestructRegex = "isDestruct (asc|desc)".r
          val isDropRegex = "isDrop (asc|desc)".r
          val recipeIdRegex = "recipeId (asc|desc)".r
          orderBy match {
            case keepTypeRegex(x) => Some(q => if (x == "asc") q.keepType.asc else q.keepType.desc)
            case crystalTypeRegex(x) => Some(q => if (x == "asc") q.crystalType.asc else q.crystalType.desc)
            case weightRegex(x) => Some(q => if (x == "asc") q.weight.asc else q.weight.desc)
            case nameRegex(x) => Some(q => if (x == "asc") q.name.asc else q.name.desc)
            case itemIdRegex(x) => Some(q => if (x == "asc") q.itemId.asc else q.itemId.desc)
            case physicalDamageRegex(x) => Some(q => if (x == "asc") q.physicalDamage.asc else q.physicalDamage.desc)
            case isPrivateStoreRegex(x) => Some(q => if (x == "asc") q.isPrivateStore.asc else q.isPrivateStore.desc)
            case descriptionRegex(x) => Some(q => if (x == "asc") q.description.asc else q.description.desc)
            case armorTypeRegex(x) => Some(q => if (x == "asc") q.armorType.asc else q.armorType.desc)
            case crystalCountRegex(x) => Some(q => if (x == "asc") q.crystalCount.asc else q.crystalCount.desc)
            case priceRegex(x) => Some(q => if (x == "asc") q.price.asc else q.price.desc)
            case magicWeaponRegex(x) => Some(q => if (x == "asc") q.magicWeapon.asc else q.magicWeapon.desc)
            case etcitemTypeRegex(x) => Some(q => if (x == "asc") q.etcitemType.asc else q.etcitemType.desc)
            case isTradeRegex(x) => Some(q => if (x == "asc") q.isTrade.asc else q.isTrade.desc)
            case elementalEnableRegex(x) => Some(q => if (x == "asc") q.elementalEnable.asc else q.elementalEnable.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case defaultPriceRegex(x) => Some(q => if (x == "asc") q.defaultPrice.asc else q.defaultPrice.desc)
            case weaponTypeRegex(x) => Some(q => if (x == "asc") q.weaponType.asc else q.weaponType.desc)
            case materialTypeRegex(x) => Some(q => if (x == "asc") q.materialType.asc else q.materialType.desc)
            case magicalDamageRegex(x) => Some(q => if (x == "asc") q.magicalDamage.asc else q.magicalDamage.desc)
            case isOlympiadCanUseRegex(x) => Some(q => if (x == "asc") q.isOlympiadCanUse.asc else q.isOlympiadCanUse.desc)
            case canMoveRegex(x) => Some(q => if (x == "asc") q.canMove.asc else q.canMove.desc)
            case slotBitTypeRegex(x) => Some(q => if (x == "asc") q.slotBitType.asc else q.slotBitType.desc)
            case itemTypeRegex(x) => Some(q => if (x == "asc") q.itemType.asc else q.itemType.desc)
            case isDestructRegex(x) => Some(q => if (x == "asc") q.isDestruct.asc else q.isDestruct.desc)
            case isDropRegex(x) => Some(q => if (x == "asc") q.isDrop.asc else q.isDrop.desc)
            case recipeIdRegex(x) => Some(q => if (x == "asc") q.recipeId.asc else q.recipeId.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class NpcsSpawnsExtension(query: Query[NpcsSpawns, NpcsSpawns#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val watcherNameRegex = "contains\\(watcherName,'([\\w\\d].*)'\\)".r
          val npcObjectIdGtRegex = "npcObjectId gt ([\\d]{1,})".r
          val npcObjectIdLtRegex = "npcObjectId lt ([\\d]{1,})".r
          val npcObjectIdEqRegex = "npcObjectId eq ([\\d]{1,})".r
          val npcZGtRegex = "npcZ gt ([\\d]{1,})".r
          val npcZLtRegex = "npcZ lt ([\\d]{1,})".r
          val npcZEqRegex = "npcZ eq ([\\d]{1,})".r
          val npcLevelGtRegex = "npcLevel gt ([\\d]{1,})".r
          val npcLevelLtRegex = "npcLevel lt ([\\d]{1,})".r
          val npcLevelEqRegex = "npcLevel eq ([\\d]{1,})".r
          val npcYGtRegex = "npcY gt ([\\d]{1,})".r
          val npcYLtRegex = "npcY lt ([\\d]{1,})".r
          val npcYEqRegex = "npcY eq ([\\d]{1,})".r
          val npcXGtRegex = "npcX gt ([\\d]{1,})".r
          val npcXLtRegex = "npcX lt ([\\d]{1,})".r
          val npcXEqRegex = "npcX eq ([\\d]{1,})".r
          val npcNameRegex = "contains\\(npcName,'([\\w\\d].*)'\\)".r
          val npcIdGtRegex = "npcId gt ([\\d]{1,})".r
          val npcIdLtRegex = "npcId lt ([\\d]{1,})".r
          val npcIdEqRegex = "npcId eq ([\\d]{1,})".r
          val watcherZGtRegex = "watcherZ gt ([\\d]{1,})".r
          val watcherZLtRegex = "watcherZ lt ([\\d]{1,})".r
          val watcherZEqRegex = "watcherZ eq ([\\d]{1,})".r
          val watcherXGtRegex = "watcherX gt ([\\d]{1,})".r
          val watcherXLtRegex = "watcherX lt ([\\d]{1,})".r
          val watcherXEqRegex = "watcherX eq ([\\d]{1,})".r
          val watcherYGtRegex = "watcherY gt ([\\d]{1,})".r
          val watcherYLtRegex = "watcherY lt ([\\d]{1,})".r
          val watcherYEqRegex = "watcherY eq ([\\d]{1,})".r
          filter match {
            case watcherNameRegex(x) => query.filter(_.watcherName.toLowerCase like s"%${x.toLowerCase}%")
            case npcObjectIdGtRegex(x) => query.filter(_.npcObjectId >= x.toInt)
            case npcObjectIdLtRegex(x) => query.filter(_.npcObjectId <= x.toInt)
            case npcObjectIdEqRegex(x) => query.filter(_.npcObjectId === x.toInt)
            case npcZGtRegex(x) => query.filter(_.npcZ >= x.toInt)
            case npcZLtRegex(x) => query.filter(_.npcZ <= x.toInt)
            case npcZEqRegex(x) => query.filter(_.npcZ === x.toInt)
            case npcLevelGtRegex(x) => query.filter(_.npcLevel >= x.toInt)
            case npcLevelLtRegex(x) => query.filter(_.npcLevel <= x.toInt)
            case npcLevelEqRegex(x) => query.filter(_.npcLevel === x.toInt)
            case npcYGtRegex(x) => query.filter(_.npcY >= x.toInt)
            case npcYLtRegex(x) => query.filter(_.npcY <= x.toInt)
            case npcYEqRegex(x) => query.filter(_.npcY === x.toInt)
            case npcXGtRegex(x) => query.filter(_.npcX >= x.toInt)
            case npcXLtRegex(x) => query.filter(_.npcX <= x.toInt)
            case npcXEqRegex(x) => query.filter(_.npcX === x.toInt)
            case npcNameRegex(x) => query.filter(_.npcName.toLowerCase like s"%${x.toLowerCase}%")
            case npcIdGtRegex(x) => query.filter(_.npcId >= x.toInt)
            case npcIdLtRegex(x) => query.filter(_.npcId <= x.toInt)
            case npcIdEqRegex(x) => query.filter(_.npcId === x.toInt)
            case watcherZGtRegex(x) => query.filter(_.watcherZ >= x.toInt)
            case watcherZLtRegex(x) => query.filter(_.watcherZ <= x.toInt)
            case watcherZEqRegex(x) => query.filter(_.watcherZ === x.toInt)
            case watcherXGtRegex(x) => query.filter(_.watcherX >= x.toInt)
            case watcherXLtRegex(x) => query.filter(_.watcherX <= x.toInt)
            case watcherXEqRegex(x) => query.filter(_.watcherX === x.toInt)
            case watcherYGtRegex(x) => query.filter(_.watcherY >= x.toInt)
            case watcherYLtRegex(x) => query.filter(_.watcherY <= x.toInt)
            case watcherYEqRegex(x) => query.filter(_.watcherY === x.toInt)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[NpcsSpawns,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val watcherNameRegex = "watcherName (asc|desc)".r
          val npcObjectIdRegex = "npcObjectId (asc|desc)".r
          val npcZRegex = "npcZ (asc|desc)".r
          val npcLevelRegex = "npcLevel (asc|desc)".r
          val updateDateRegex = "updateDate (asc|desc)".r
          val npcYRegex = "npcY (asc|desc)".r
          val npcXRegex = "npcX (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val npcNameRegex = "npcName (asc|desc)".r
          val tokenIdRegex = "tokenId (asc|desc)".r
          val npcIdRegex = "npcId (asc|desc)".r
          val watcherZRegex = "watcherZ (asc|desc)".r
          val npcIsDeadRegex = "npcIsDead (asc|desc)".r
          val watcherXRegex = "watcherX (asc|desc)".r
          val watcherYRegex = "watcherY (asc|desc)".r
          orderBy match {
            case watcherNameRegex(x) => Some(q => if (x == "asc") q.watcherName.asc else q.watcherName.desc)
            case npcObjectIdRegex(x) => Some(q => if (x == "asc") q.npcObjectId.asc else q.npcObjectId.desc)
            case npcZRegex(x) => Some(q => if (x == "asc") q.npcZ.asc else q.npcZ.desc)
            case npcLevelRegex(x) => Some(q => if (x == "asc") q.npcLevel.asc else q.npcLevel.desc)
            case updateDateRegex(x) => Some(q => if (x == "asc") q.updateDate.asc else q.updateDate.desc)
            case npcYRegex(x) => Some(q => if (x == "asc") q.npcY.asc else q.npcY.desc)
            case npcXRegex(x) => Some(q => if (x == "asc") q.npcX.asc else q.npcX.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case npcNameRegex(x) => Some(q => if (x == "asc") q.npcName.asc else q.npcName.desc)
            case tokenIdRegex(x) => Some(q => if (x == "asc") q.tokenId.asc else q.tokenId.desc)
            case npcIdRegex(x) => Some(q => if (x == "asc") q.npcId.asc else q.npcId.desc)
            case watcherZRegex(x) => Some(q => if (x == "asc") q.watcherZ.asc else q.watcherZ.desc)
            case npcIsDeadRegex(x) => Some(q => if (x == "asc") q.npcIsDead.asc else q.npcIsDead.desc)
            case watcherXRegex(x) => Some(q => if (x == "asc") q.watcherX.asc else q.watcherX.desc)
            case watcherYRegex(x) => Some(q => if (x == "asc") q.watcherY.asc else q.watcherY.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class PlayersExtension(query: Query[Players, Players#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val xGtRegex = "x gt ([\\d]{1,})".r
          val xLtRegex = "x lt ([\\d]{1,})".r
          val xEqRegex = "x eq ([\\d]{1,})".r
          val nameRegex = "contains\\(name,'([\\w\\d].*)'\\)".r
          val paperDollsRegex = "contains\\(paperDolls,'([\\w\\d].*)'\\)".r
          val yGtRegex = "y gt ([\\d]{1,})".r
          val yLtRegex = "y lt ([\\d]{1,})".r
          val yEqRegex = "y eq ([\\d]{1,})".r
          val sexGtRegex = "sex gt ([\\d]{1,})".r
          val sexLtRegex = "sex lt ([\\d]{1,})".r
          val sexEqRegex = "sex eq ([\\d]{1,})".r
          val raceGtRegex = "race gt ([\\d]{1,})".r
          val raceLtRegex = "race lt ([\\d]{1,})".r
          val raceEqRegex = "race eq ([\\d]{1,})".r
          val clanIdGtRegex = "clanId gt ([\\d]{1,})".r
          val clanIdLtRegex = "clanId lt ([\\d]{1,})".r
          val clanIdEqRegex = "clanId eq ([\\d]{1,})".r
          val baseClassGtRegex = "baseClass gt ([\\d]{1,})".r
          val baseClassLtRegex = "baseClass lt ([\\d]{1,})".r
          val baseClassEqRegex = "baseClass eq ([\\d]{1,})".r
          val titleRegex = "contains\\(title,'([\\w\\d].*)'\\)".r
          val objectIdGtRegex = "objectId gt ([\\d]{1,})".r
          val objectIdLtRegex = "objectId lt ([\\d]{1,})".r
          val objectIdEqRegex = "objectId eq ([\\d]{1,})".r
          val zGtRegex = "z gt ([\\d]{1,})".r
          val zLtRegex = "z lt ([\\d]{1,})".r
          val zEqRegex = "z eq ([\\d]{1,})".r
          filter match {
            case xGtRegex(x) => query.filter(_.x >= x.toInt)
            case xLtRegex(x) => query.filter(_.x <= x.toInt)
            case xEqRegex(x) => query.filter(_.x === x.toInt)
            case nameRegex(x) => query.filter(_.name.toLowerCase like s"%${x.toLowerCase}%")
            case paperDollsRegex(x) => query.filter(_.paperDolls.toLowerCase like s"%${x.toLowerCase}%")
            case yGtRegex(x) => query.filter(_.y >= x.toInt)
            case yLtRegex(x) => query.filter(_.y <= x.toInt)
            case yEqRegex(x) => query.filter(_.y === x.toInt)
            case sexGtRegex(x) => query.filter(_.sex >= x.toInt)
            case sexLtRegex(x) => query.filter(_.sex <= x.toInt)
            case sexEqRegex(x) => query.filter(_.sex === x.toInt)
            case raceGtRegex(x) => query.filter(_.race >= x.toInt)
            case raceLtRegex(x) => query.filter(_.race <= x.toInt)
            case raceEqRegex(x) => query.filter(_.race === x.toInt)
            case clanIdGtRegex(x) => query.filter(_.clanId.filter(_ >= x.toInt).isDefined)
            case clanIdLtRegex(x) => query.filter(_.clanId.filter(_ <= x.toInt).isDefined)
            case clanIdEqRegex(x) => query.filter(_.clanId.filter(_ === x.toInt).isDefined)
            case baseClassGtRegex(x) => query.filter(_.baseClass >= x.toInt)
            case baseClassLtRegex(x) => query.filter(_.baseClass <= x.toInt)
            case baseClassEqRegex(x) => query.filter(_.baseClass === x.toInt)
            case titleRegex(x) => query.filter(_.title.filter(_.toLowerCase like s"%${x.toLowerCase}%").isDefined)
            case objectIdGtRegex(x) => query.filter(_.objectId >= x.toInt)
            case objectIdLtRegex(x) => query.filter(_.objectId <= x.toInt)
            case objectIdEqRegex(x) => query.filter(_.objectId === x.toInt)
            case zGtRegex(x) => query.filter(_.z >= x.toInt)
            case zLtRegex(x) => query.filter(_.z <= x.toInt)
            case zEqRegex(x) => query.filter(_.z === x.toInt)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[Players,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val xRegex = "x (asc|desc)".r
          val nameRegex = "name (asc|desc)".r
          val paperDollsRegex = "paperDolls (asc|desc)".r
          val yRegex = "y (asc|desc)".r
          val isNobleRegex = "isNoble (asc|desc)".r
          val isHeroRegex = "isHero (asc|desc)".r
          val sexRegex = "sex (asc|desc)".r
          val serverIdRegex = "serverId (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val raceRegex = "race (asc|desc)".r
          val clanIdRegex = "clanId (asc|desc)".r
          val baseClassRegex = "baseClass (asc|desc)".r
          val titleRegex = "title (asc|desc)".r
          val lastSeenRegex = "lastSeen (asc|desc)".r
          val objectIdRegex = "objectId (asc|desc)".r
          val zRegex = "z (asc|desc)".r
          orderBy match {
            case xRegex(x) => Some(q => if (x == "asc") q.x.asc else q.x.desc)
            case nameRegex(x) => Some(q => if (x == "asc") q.name.asc else q.name.desc)
            case paperDollsRegex(x) => Some(q => if (x == "asc") q.paperDolls.asc else q.paperDolls.desc)
            case yRegex(x) => Some(q => if (x == "asc") q.y.asc else q.y.desc)
            case isNobleRegex(x) => Some(q => if (x == "asc") q.isNoble.asc else q.isNoble.desc)
            case isHeroRegex(x) => Some(q => if (x == "asc") q.isHero.asc else q.isHero.desc)
            case sexRegex(x) => Some(q => if (x == "asc") q.sex.asc else q.sex.desc)
            case serverIdRegex(x) => Some(q => if (x == "asc") q.serverId.asc else q.serverId.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case raceRegex(x) => Some(q => if (x == "asc") q.race.asc else q.race.desc)
            case clanIdRegex(x) => Some(q => if (x == "asc") q.clanId.asc else q.clanId.desc)
            case baseClassRegex(x) => Some(q => if (x == "asc") q.baseClass.asc else q.baseClass.desc)
            case titleRegex(x) => Some(q => if (x == "asc") q.title.asc else q.title.desc)
            case lastSeenRegex(x) => Some(q => if (x == "asc") q.lastSeen.asc else q.lastSeen.desc)
            case objectIdRegex(x) => Some(q => if (x == "asc") q.objectId.asc else q.objectId.desc)
            case zRegex(x) => Some(q => if (x == "asc") q.z.asc else q.z.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class PlayEvolutionsExtension(query: Query[PlayEvolutions, PlayEvolutions#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val revertScriptRegex = "contains\\(revertScript,'([\\w\\d].*)'\\)".r
          val stateRegex = "contains\\(state,'([\\w\\d].*)'\\)".r
          val lastProblemRegex = "contains\\(lastProblem,'([\\w\\d].*)'\\)".r
          val hashRegex = "contains\\(hash,'([\\w\\d].*)'\\)".r
          val idGtRegex = "id gt ([\\d]{1,})".r
          val idLtRegex = "id lt ([\\d]{1,})".r
          val idEqRegex = "id eq ([\\d]{1,})".r
          val applyScriptRegex = "contains\\(applyScript,'([\\w\\d].*)'\\)".r
          filter match {
            case revertScriptRegex(x) => query.filter(_.revertScript.filter(_.toLowerCase like s"%${x.toLowerCase}%").isDefined)
            case stateRegex(x) => query.filter(_.state.filter(_.toLowerCase like s"%${x.toLowerCase}%").isDefined)
            case lastProblemRegex(x) => query.filter(_.lastProblem.filter(_.toLowerCase like s"%${x.toLowerCase}%").isDefined)
            case hashRegex(x) => query.filter(_.hash.toLowerCase like s"%${x.toLowerCase}%")
            case idGtRegex(x) => query.filter(_.id >= x.toInt)
            case idLtRegex(x) => query.filter(_.id <= x.toInt)
            case idEqRegex(x) => query.filter(_.id === x.toInt)
            case applyScriptRegex(x) => query.filter(_.applyScript.filter(_.toLowerCase like s"%${x.toLowerCase}%").isDefined)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[PlayEvolutions,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val revertScriptRegex = "revertScript (asc|desc)".r
          val stateRegex = "state (asc|desc)".r
          val appliedAtRegex = "appliedAt (asc|desc)".r
          val lastProblemRegex = "lastProblem (asc|desc)".r
          val hashRegex = "hash (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val applyScriptRegex = "applyScript (asc|desc)".r
          orderBy match {
            case revertScriptRegex(x) => Some(q => if (x == "asc") q.revertScript.asc else q.revertScript.desc)
            case stateRegex(x) => Some(q => if (x == "asc") q.state.asc else q.state.desc)
            case appliedAtRegex(x) => Some(q => if (x == "asc") q.appliedAt.asc else q.appliedAt.desc)
            case lastProblemRegex(x) => Some(q => if (x == "asc") q.lastProblem.asc else q.lastProblem.desc)
            case hashRegex(x) => Some(q => if (x == "asc") q.hash.asc else q.hash.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case applyScriptRegex(x) => Some(q => if (x == "asc") q.applyScript.asc else q.applyScript.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class ServersExtension(query: Query[Servers, Servers#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val nameRegex = "contains\\(name,'([\\w\\d].*)'\\)".r
          val identifierRegex = "contains\\(identifier,'([\\w\\d].*)'\\)".r
          filter match {
            case nameRegex(x) => query.filter(_.name.toLowerCase like s"%${x.toLowerCase}%")
            case identifierRegex(x) => query.filter(_.identifier.toLowerCase like s"%${x.toLowerCase}%")
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[Servers,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val idRegex = "id (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val nameRegex = "name (asc|desc)".r
          val identifierRegex = "identifier (asc|desc)".r
          orderBy match {
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case nameRegex(x) => Some(q => if (x == "asc") q.name.asc else q.name.desc)
            case identifierRegex(x) => Some(q => if (x == "asc") q.identifier.asc else q.identifier.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class SubclassesExtension(query: Query[Subclasses, Subclasses#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val classIdGtRegex = "classId gt ([\\d]{1,})".r
          val classIdLtRegex = "classId lt ([\\d]{1,})".r
          val classIdEqRegex = "classId eq ([\\d]{1,})".r
          filter match {
            case classIdGtRegex(x) => query.filter(_.classId >= x.toInt)
            case classIdLtRegex(x) => query.filter(_.classId <= x.toInt)
            case classIdEqRegex(x) => query.filter(_.classId === x.toInt)
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[Subclasses,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val classIdRegex = "classId (asc|desc)".r
          val playerIdRegex = "playerId (asc|desc)".r
          val lastSeenRegex = "lastSeen (asc|desc)".r
          orderBy match {
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case classIdRegex(x) => Some(q => if (x == "asc") q.classId.asc else q.classId.desc)
            case playerIdRegex(x) => Some(q => if (x == "asc") q.playerId.asc else q.playerId.desc)
            case lastSeenRegex(x) => Some(q => if (x == "asc") q.lastSeen.asc else q.lastSeen.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }

  implicit class UsersExtension(query: Query[Users, Users#TableElementType, Seq]) extends ODataSupported {
    def queryFilter(filterOpt: Option[String]) = {
      filterOpt match {
        case None => query
        case Some(filter) =>
          val nameRegex = "contains\\(name,'([\\w\\d].*)'\\)".r
          val emailRegex = "contains\\(email,'([\\w\\d].*)'\\)".r
          val roleGtRegex = "role gt ([\\d]{1,})".r
          val roleLtRegex = "role lt ([\\d]{1,})".r
          val roleEqRegex = "role eq ([\\d]{1,})".r
          val apiTokenRegex = "contains\\(apiToken,'([\\w\\d].*)'\\)".r
          val telegramGtRegex = "telegram gt ([\\d]{1,})".r
          val telegramLtRegex = "telegram lt ([\\d]{1,})".r
          val telegramEqRegex = "telegram eq ([\\d]{1,})".r
          val passwordRegex = "contains\\(password,'([\\w\\d].*)'\\)".r
          filter match {
            case nameRegex(x) => query.filter(_.name.toLowerCase like s"%${x.toLowerCase}%")
            case emailRegex(x) => query.filter(_.email.toLowerCase like s"%${x.toLowerCase}%")
            case roleGtRegex(x) => query.filter(_.role >= x.toInt)
            case roleLtRegex(x) => query.filter(_.role <= x.toInt)
            case roleEqRegex(x) => query.filter(_.role === x.toInt)
            case apiTokenRegex(x) => query.filter(_.apiToken.toLowerCase like s"%${x.toLowerCase}%")
            case telegramGtRegex(x) => query.filter(_.telegram.filter(_ >= x.toInt).isDefined)
            case telegramLtRegex(x) => query.filter(_.telegram.filter(_ <= x.toInt).isDefined)
            case telegramEqRegex(x) => query.filter(_.telegram.filter(_ === x.toInt).isDefined)
            case passwordRegex(x) => query.filter(_.password.toLowerCase like s"%${x.toLowerCase}%")
            case _ => query
          }
      }
    }
        
    def queryOrderBy(orderByOpt: Option[String]): Option[Function[Users,  slick.lifted.ColumnOrdered[_]]] = {
      orderByOpt flatMap { orderBy =>
        val nameRegex = "name (asc|desc)".r
          val emailRegex = "email (asc|desc)".r
          val roleRegex = "role (asc|desc)".r
          val enabledRegex = "enabled (asc|desc)".r
          val createDateRegex = "createDate (asc|desc)".r
          val idRegex = "id (asc|desc)".r
          val apiTokenRegex = "apiToken (asc|desc)".r
          val telegramRegex = "telegram (asc|desc)".r
          val passwordRegex = "password (asc|desc)".r
          orderBy match {
            case nameRegex(x) => Some(q => if (x == "asc") q.name.asc else q.name.desc)
            case emailRegex(x) => Some(q => if (x == "asc") q.email.asc else q.email.desc)
            case roleRegex(x) => Some(q => if (x == "asc") q.role.asc else q.role.desc)
            case enabledRegex(x) => Some(q => if (x == "asc") q.enabled.asc else q.enabled.desc)
            case createDateRegex(x) => Some(q => if (x == "asc") q.createDate.asc else q.createDate.desc)
            case idRegex(x) => Some(q => if (x == "asc") q.id.asc else q.id.desc)
            case apiTokenRegex(x) => Some(q => if (x == "asc") q.apiToken.asc else q.apiToken.desc)
            case telegramRegex(x) => Some(q => if (x == "asc") q.telegram.asc else q.telegram.desc)
            case passwordRegex(x) => Some(q => if (x == "asc") q.password.asc else q.password.desc)
            case _ => None
        }
      }
    }
        
    def applyOData(implicit request: play.api.mvc.Request[_]) = {
       val filters = decodedQueryStrings("$filter")
       val q = if (filters.isEmpty) query else filters.foldLeft(query)((q, f) => q.queryFilter(Some(f)))
       queryOrderBy(orderBy).map(v => q.sortBy(v(_))).getOrElse(q)
    }
    def applyQueryParams(implicit request: play.api.mvc.Request[_]) = query.applyOData.page(offset, limit)
  }
}
