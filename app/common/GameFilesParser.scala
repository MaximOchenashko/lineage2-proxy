package common

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import common.GameFilesParser._
import common.enums.game._
import play.libs.Json

import scala.io.Source
import scala.util.Try

/**
  * Utility class just to generate json from game files. Not used in main programm
  *
  * @author iRevThis
  */
class GameFilesParser(skillDataPath: String, itemsNamePath: String, itemDataPath: String) {

  val skills = Source.fromFile(skillDataPath, "UTF-8").getLines()
    .filterNot(_.contains("#"))
    .filterNot(_.contains("//"))
    .filter(_.startsWith("skill_begin"))
    .map(_.split("\t", -1).drop(1).dropRight(1).map(_.replace(" ", "")))
    .map { v =>
      val refs = v.filter(_.split("=").length == 2).map { v => val arr = v.split("="); arr.head -> arr.last }.toMap
      SkillData(refs("skill_name"), refs("skill_id").toInt, refs("level").toInt)
    }

  def parseItemNames() = {
    val itemsNames = Source.fromFile(itemsNamePath, "UTF-8").getLines()
      .drop(1)
      .filterNot(_.startsWith("#"))
      .filter(_.trim.length > 0)
      .map(_.split("\t", -1))
      .map(v => ItemName(v(0).toInt, v(1).replace("&", "and"), v(2), v(3).replace(".\\0", "").replace("u,", "").replace("a,", "")))
      .toList

    Source.fromFile(itemDataPath, "UTF-8").getLines()
      .filterNot(_.contains("#"))
      .filterNot(_.contains("//"))
      .filter(_.contains("item_begin"))
      .map(_.split("\t", -1).drop(2).dropRight(1))
      .map { v =>
        val itemId = v(0).toInt
        val pch = v(1).drop(1).dropRight(1)
        val itemName = itemsNames.find(_.id == itemId)
        val name = itemName.map(_.name).getOrElse("")
        val itemData = ItemData(itemId, name, pch, itemName.map(_.addName), itemName.map(_.description))
        v.filter(_.split("=").length == 2).map { v => val arr = v.split("="); arr.head -> arr.last }.foldLeft(itemData)(mapValues)
      }.toSeq
  }

  def mapValues(itemData: ItemData, values: (String, String)): ItemData = {
    val value = values._2
    lazy val intValue = Try(value.toInt).toOption
    lazy val booleanValue = intValue.contains(1)
    partOne(value, intValue, itemData).orElse(partTwo(value, intValue, booleanValue, itemData)).applyOrElse(values._1, (v: String) => itemData)
  }

  def partOne(value: String, intValue: Option[Int], itemData: ItemData): PartialFunction[String, ItemData] = {
    case "item_type" => itemData.copy(itemType = ItemType.byFileValue(value).map(_.code))
    case "slot_bit_type" => itemData.copy(slotBitType = SlotBitType.byFileValue(value).map(_.code))
    case "armor_type" => itemData.copy(armorType = ArmorType.byFileValue(value).map(_.code))
    case "etcitem_type" => itemData.copy(etcItemType = EtcItemType.byFileValue(value).map(_.code))
    case "delay_share_group" => itemData.copy(delayShareGroup = intValue)
    case "item_multi_skill_list" => if (value.length < 3) itemData else itemData.copy(itemMultiSkillList = value.drop(1).dropRight(1).split(";").map(v => skills.find(_.pchName == v)).filter(_.isDefined).map(_.get))
    case "recipe_id" => itemData.copy(recipeId = intValue)
    case "blessed" => itemData.copy(blessed = intValue)
    case "weight" => itemData.copy(weight = intValue)
    case "default_action" => itemData.copy(defaultAction = DefaultAction.byFileValue(value).map(_.code))
    case "consume_type" => itemData.copy(consumeType = ConsumeType.byFileValue(value).map(_.code))
    case "initial_count" => itemData.copy(initialCount = intValue)
    case "soulshot_count" => itemData.copy(soulshotCount = intValue)
    case "spiritshot_count" => itemData.copy(spiritshotCount = intValue)
    /* case "reduced_soulshot" => value
     case "reduced_spiritshot" =>
     case "reduced_mp_consume" =>*/
    case "immediate_effect" => itemData.copy(immediateEffect = intValue)
    case "ex_immediate_effect" => itemData.copy(exImmediateEffect = intValue)
    case "drop_period" => itemData.copy(dropPeriod = intValue)
    case "duration" => itemData.copy(duration = intValue)
    case "use_skill_distime" => itemData.copy(useSkillDistime = intValue)
    case "period" => itemData.copy(period = intValue)
    case "equip_reuse_delay" => itemData.copy(equipReuseDelay = intValue)
    case "price" => itemData.copy(price = Try(value.toLong).toOption)
    case "default_price" => itemData.copy(defaultPrice = Try(value.toLong).toOption)
    case "item_skill" =>
      if (value.length < 3)
        itemData
      else
        itemData.copy(itemSkill = skills.find(_.pchName == value.drop(1).dropRight(1)))
    case "critical_attack_skill" => if (value.length < 3) itemData else itemData.copy(criticalAttackSkill = skills.find(_.pchName == value.drop(1).dropRight(1)))
    case "attack_skill" => if (value.length < 3) itemData else itemData.copy(attackSkill = skills.find(_.pchName == value.drop(1).dropRight(1)))
    case "magic_skill" => if (value.length < 3) itemData else itemData.copy(magicSkill = skills.find(_.pchName == value.drop(1).dropRight(1)))
    case "item_skill_enchanted_four" => if (value.length < 3) itemData else itemData.copy(itemSkillEnchantedFour = skills.find(_.pchName == value.drop(1).dropRight(1)))
  }

  def partTwo(value: String, intValue: Option[Int], booleanValue: Boolean, itemData: ItemData): PartialFunction[String, ItemData] = {
    case "capsuled_items" => itemData //todo map
    case "material_type" => itemData.copy(materialType = MaterialType.byFileValue(value).map(_.code))
    case "crystal_type" => itemData.copy(crystalType = CrystalType.byFileValue(value).map(_.code))
    case "crystal_count" => itemData.copy(crystalCount = intValue)
    case "is_trade" => itemData.copy(isTrade = Some(booleanValue))
    case "is_drop" => itemData.copy(isDrop = Some(booleanValue))
    case "is_destruct" => itemData.copy(isDestruct = Some(booleanValue))
    case "is_private_store" => itemData.copy(isPrivateStore = Some(booleanValue))
    case "keep_type" => itemData.copy(keepType = intValue)
    case "physical_damage" => itemData.copy(physicalDamage = intValue)
    case "random_damage" => itemData.copy(randomDamage = intValue)
    case "weapon_type" => itemData.copy(weaponType = WeaponType.byFileValue(value).map(_.code))
    case "critical" => itemData.copy(critical = intValue)
    case "hit_modify" => itemData.copy(hitModify = Some(value.toFloat))
    case "avoid_modify" => itemData.copy(avoidModify = intValue)
    case "dual_fhit_rate" => itemData.copy(dualFhitRate = intValue)
    case "shield_defense" => itemData.copy(shieldDefense = intValue)
    case "shield_defense_rate" => itemData.copy(shieldDefenseRate = intValue)
    case "attack_range" => itemData.copy(attackRange = intValue)
    case "damage_range" => itemData //todo map
    case "attack_speed" => itemData.copy(attackSpeed = intValue)
    case "reuse_delay" => itemData.copy(reuseDelay = intValue)
    case "mp_consume" => itemData //todo compute
    case "magical_damage" => itemData.copy(magicalDamage = intValue)
    case "durability" => itemData.copy(durability = intValue)
    case "damaged" => itemData.copy(damaged = intValue)
    case "physical_defense" => itemData.copy(physicalDamage = intValue)
    case "magical_defense" => itemData.copy(magicalDefense = intValue)
    case "mp_bonus" => itemData.copy(mpBonus = intValue)
    case "category" => itemData //todo
    case "enchanted" => if (value.length < 3) itemData else itemData.copy(enchanted = skills.find(_.pchName == value.drop(1).dropRight(1)))
    case "base_attribute_attack" => itemData.copy(baseAttributeAttack = attackAttribute(value))
    case "base_attribute_defend" => itemData.copy(baseAttributeDefence = defenceAttribute(value))
    case "html" => if (value.length < 3) itemData else itemData.copy(html = Some(value.drop(1).dropRight(1)))
    case "magic_weapon" => itemData.copy(magicWeapon = Some(booleanValue))
    case "enchant_enable" => itemData.copy(enchantEnable = intValue)
    case "elemental_enable" => itemData.copy(elementalEnable = Some(booleanValue))
    case "unequip_skill" => if (value.length < 3) itemData else itemData.copy(unequipSkill = skills.find(_.pchName == value.drop(1).dropRight(1)))
    case "for_npc" => itemData.copy(forNpc = Some(booleanValue))
    case "item_equip_option" => itemData //todo
    case "use_condition" => itemData //todo
    case "equip_condition" => itemData //todo
    case "is_olympiad_can_use" => itemData.copy(isOlympiadCanUse = Some(booleanValue))
    case "can_move" => itemData.copy(canMove = Some(booleanValue))
    case "is_premium" => itemData.copy(isPremium = Some(booleanValue))
  }

  def groupByHundreds(id: Int) = {
    val str = id.toString
    val hundred = Try(str.reverse(2).asDigit).getOrElse(0)
    val thousands = Try(str.take(str.length - 3).toInt * 1000).getOrElse(0)
    val min = thousands + hundred * 100
    val max = thousands + hundred * 100 + 99
    s"${min}_$max"
  }

  def attackAttribute(source: String) = {
    Try {
      val arr = source.drop(1).dropRight(1).split(";")
      ElementAttack(ElementalAttributes.byName(arr.head.capitalize).get.code, arr.last.toInt)
    }.toOption
  }

  def defenceAttribute(source: String) = {
    Try {
      val arr = source.drop(1).dropRight(1).split(";").map(_.toInt)
      DefenceElement(arr(0), arr(1), arr(2), arr(3), arr(4), arr(5))
    }.toOption
  }
}

object GameFilesParser {

  def main(args: Array[String]) {
    import java.io._
    val mapper = new ObjectMapper()
      .setSerializationInclusion(Include.NON_NULL)

    Json.setObjectMapper(mapper)
    val fg = new GameFilesParser("", "", "")
    val groups = fg.parseItemNames().groupBy(v => fg.groupByHundreds(v.id))

    for {
      (groupName, values) <- fg.parseItemNames().groupBy(v => fg.groupByHundreds(v.id))
    } yield {
      val pw = new PrintWriter(new File(s"d:\\json\\${groupName}.json"), "UTF-8")
      try pw.write(Json.toJson(values).toString)
      finally pw.close()
    }
    groups.foreach { g =>
      val pw = new PrintWriter(new File(s"d:\\json\\${g._1}.json"), "UTF-8")
      pw.write(Json.toJson(g._2).toString)
      pw.close()
    }
  }

  case class ItemName(id: Int, name: String, addName: String, description: String)

  case class ItemData(id: Int, name: String, pch: String, addName: Option[String], description: Option[String],
                      itemType: Option[Int] = None,
                      slotBitType: Option[Int] = None,
                      armorType: Option[Int] = None,
                      etcItemType: Option[Int] = None,
                      delayShareGroup: Option[Int] = None,
                      itemMultiSkillList: Seq[SkillData] = Seq.empty,
                      recipeId: Option[Int] = None,
                      blessed: Option[Int] = None,
                      weight: Option[Int] = None,
                      defaultAction: Option[Int] = None,
                      consumeType: Option[Int] = None,
                      initialCount: Option[Int] = None,
                      soulshotCount: Option[Int] = None,
                      spiritshotCount: Option[Int] = None,
                      reducedSoulshot: Option[String] = None,
                      reducedSpiritshot: Option[String] = None,
                      reducedMpConsume: Option[String] = None,
                      immediateEffect: Option[Int] = None,
                      exImmediateEffect: Option[Int] = None,
                      dropPeriod: Option[Int] = None,
                      duration: Option[Int] = None,
                      useSkillDistime: Option[Int] = None,
                      period: Option[Int] = None,
                      equipReuseDelay: Option[Int] = None,
                      price: Option[Long] = None,
                      defaultPrice: Option[Long] = None,
                      itemSkill: Option[SkillData] = None,
                      criticalAttackSkill: Option[SkillData] = None,
                      attackSkill: Option[SkillData] = None,
                      magicSkill: Option[SkillData] = None,
                      magicSkillChance: Option[Int] = None,
                      itemSkillEnchantedFour: Option[SkillData] = None,
                      capsuledItems: Seq[Int] = Seq.empty,
                      materialType: Option[Int] = None,
                      crystalType: Option[Int] = None,
                      crystalCount: Option[Int] = None,
                      isTrade: Option[Boolean] = None,
                      isDrop: Option[Boolean] = None,
                      isDestruct: Option[Boolean] = None,
                      isPrivateStore: Option[Boolean] = None,
                      keepType: Option[Int] = None,
                      physicalDamage: Option[Int] = None,
                      randomDamage: Option[Int] = None,
                      weaponType: Option[Int] = None,
                      critical: Option[Int] = None,
                      hitModify: Option[Float] = None,
                      avoidModify: Option[Int] = None,
                      dualFhitRate: Option[Int] = None,
                      shieldDefense: Option[Int] = None,
                      shieldDefenseRate: Option[Int] = None,
                      attackRange: Option[Int] = None,
                      damageRangeRange: Option[DamageRange] = None,
                      attackSpeed: Option[Int] = None,
                      reuseDelay: Option[Int] = None,
                      mpConsume: Option[Int] = None,
                      mpConsumeRepeat: Option[Int] = None,
                      magicalDamage: Option[Int] = None,
                      durability: Option[Int] = None,
                      damaged: Option[Int] = None,
                      physicalDefense: Option[Int] = None,
                      magicalDefense: Option[Int] = None,
                      mpBonus: Option[Int] = None,
                      category: Option[Int] = None,
                      enchanted: Option[SkillData] = None,
                      baseAttributeAttack: Option[ElementAttack] = None,
                      baseAttributeDefence: Option[DefenceElement] = None,
                      html: Option[String] = None,
                      magicWeapon: Option[Boolean] = None,
                      enchantEnable: Option[Int] = None,
                      elementalEnable: Option[Boolean] = None,
                      unequipSkill: Option[SkillData] = None,
                      forNpc: Option[Boolean] = None,
                      itemEquipOption: Option[Int] = None,
                      useCondition: Option[Int] = None,
                      equipCondition: Option[Int] = None,
                      isOlympiadCanUse: Option[Boolean] = None,
                      canMove: Option[Boolean] = None,
                      isPremium: Option[Boolean] = None)

  case class ElementAttack(element: Int, value: Int)

  case class DefenceElement(fire: Int, water: Int, wind: Int, earth: Int, holy: Int, dark: Int)

  case class SkillData(pchName: String, skillId: Int, skillLevel: Int)

  case class DamageRange(range: Int, baseAngle: Int, radius: Int, angle: Int)

}

