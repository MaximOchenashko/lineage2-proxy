package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait ItemType extends FileDataBased

object ItemType extends FileDataEnumHolder[ItemType] {
  override def values: Seq[ItemType] = Seq(Weapon, None, Etcitem, Arrow, Armor, Asset, Potion, Accessary, Questitem, ScrlEnchantWp, ScrlEnchantAm, Scroll, Recipe, Material, PetCollar, CastleGuard, Lotto, RaceTicket, Dye, Seed, Crop, Maturecrop, Harvest, Seed2, TicketOfLord, Lure, BlessScrlEnchantWp, BlessScrlEnchantAm, Coupon, Elixir, ScrlEnchantAttr, Bolt, ScrlIncEnchantPropWp, ScrlIncEnchantPropAm, AncientCrystalEnchantWp, AncientCrystalEnchantAm, RuneSelect, Rune)


  case object Weapon extends ItemType {
    override def name: String = "Weapon"

    override def fileValue: String = "weapon"

    override def code: Int = 0
  }

  case object None extends ItemType {
    override def name: String = "None"

    override def fileValue: String = "none"

    override def code: Int = 1
  }

  case object Etcitem extends ItemType {
    override def name: String = "Etcitem"

    override def fileValue: String = "etcitem"

    override def code: Int = 2
  }

  case object Arrow extends ItemType {
    override def name: String = "Arrow"

    override def fileValue: String = "arrow"

    override def code: Int = 3
  }

  case object Armor extends ItemType {
    override def name: String = "Armor"

    override def fileValue: String = "armor"

    override def code: Int = 4
  }

  case object Asset extends ItemType {
    override def name: String = "Asset"

    override def fileValue: String = "asset"

    override def code: Int = 5
  }

  case object Potion extends ItemType {
    override def name: String = "Potion"

    override def fileValue: String = "potion"

    override def code: Int = 6
  }

  case object Accessary extends ItemType {
    override def name: String = "Accessary"

    override def fileValue: String = "accessary"

    override def code: Int = 7
  }

  case object Questitem extends ItemType {
    override def name: String = "Questitem"

    override def fileValue: String = "questitem"

    override def code: Int = 8
  }

  case object ScrlEnchantWp extends ItemType {
    override def name: String = "ScrlEnchantWp"

    override def fileValue: String = "scrl_enchant_wp"

    override def code: Int = 9
  }

  case object ScrlEnchantAm extends ItemType {
    override def name: String = "ScrlEnchantAm"

    override def fileValue: String = "scrl_enchant_am"

    override def code: Int = 10
  }

  case object Scroll extends ItemType {
    override def name: String = "Scroll"

    override def fileValue: String = "scroll"

    override def code: Int = 11
  }

  case object Recipe extends ItemType {
    override def name: String = "Recipe"

    override def fileValue: String = "recipe"

    override def code: Int = 12
  }

  case object Material extends ItemType {
    override def name: String = "Material"

    override def fileValue: String = "material"

    override def code: Int = 13
  }

  case object PetCollar extends ItemType {
    override def name: String = "PetCollar"

    override def fileValue: String = "pet_collar"

    override def code: Int = 14
  }

  case object CastleGuard extends ItemType {
    override def name: String = "CastleGuard"

    override def fileValue: String = "castle_guard"

    override def code: Int = 15
  }

  case object Lotto extends ItemType {
    override def name: String = "Lotto"

    override def fileValue: String = "lotto"

    override def code: Int = 16
  }

  case object RaceTicket extends ItemType {
    override def name: String = "RaceTicket"

    override def fileValue: String = "race_ticket"

    override def code: Int = 17
  }

  case object Dye extends ItemType {
    override def name: String = "Dye"

    override def fileValue: String = "dye"

    override def code: Int = 18
  }

  case object Seed extends ItemType {
    override def name: String = "Seed"

    override def fileValue: String = "seed"

    override def code: Int = 19
  }

  case object Crop extends ItemType {
    override def name: String = "Crop"

    override def fileValue: String = "crop"

    override def code: Int = 20
  }

  case object Maturecrop extends ItemType {
    override def name: String = "Maturecrop"

    override def fileValue: String = "maturecrop"

    override def code: Int = 21
  }

  case object Harvest extends ItemType {
    override def name: String = "Harvest"

    override def fileValue: String = "harvest"

    override def code: Int = 22
  }

  case object Seed2 extends ItemType {
    override def name: String = "Seed2"

    override def fileValue: String = "seed2"

    override def code: Int = 23
  }

  case object TicketOfLord extends ItemType {
    override def name: String = "TicketOfLord"

    override def fileValue: String = "ticket_of_lord"

    override def code: Int = 24
  }

  case object Lure extends ItemType {
    override def name: String = "Lure"

    override def fileValue: String = "lure"

    override def code: Int = 25
  }

  case object BlessScrlEnchantWp extends ItemType {
    override def name: String = "BlessScrlEnchantWp"

    override def fileValue: String = "bless_scrl_enchant_wp"

    override def code: Int = 26
  }

  case object BlessScrlEnchantAm extends ItemType {
    override def name: String = "BlessScrlEnchantAm"

    override def fileValue: String = "bless_scrl_enchant_am"

    override def code: Int = 27
  }

  case object Coupon extends ItemType {
    override def name: String = "Coupon"

    override def fileValue: String = "coupon"

    override def code: Int = 28
  }

  case object Elixir extends ItemType {
    override def name: String = "Elixir"

    override def fileValue: String = "elixir"

    override def code: Int = 29
  }

  case object ScrlEnchantAttr extends ItemType {
    override def name: String = "ScrlEnchantAttr"

    override def fileValue: String = "scrl_enchant_attr"

    override def code: Int = 30
  }

  case object Bolt extends ItemType {
    override def name: String = "Bolt"

    override def fileValue: String = "bolt"

    override def code: Int = 31
  }

  case object ScrlIncEnchantPropWp extends ItemType {
    override def name: String = "ScrlIncEnchantPropWp"

    override def fileValue: String = "scrl_inc_enchant_prop_wp"

    override def code: Int = 32
  }

  case object ScrlIncEnchantPropAm extends ItemType {
    override def name: String = "ScrlIncEnchantPropAm"

    override def fileValue: String = "scrl_inc_enchant_prop_am"

    override def code: Int = 33
  }

  case object AncientCrystalEnchantWp extends ItemType {
    override def name: String = "AncientCrystalEnchantWp"

    override def fileValue: String = "ancient_crystal_enchant_wp"

    override def code: Int = 34
  }

  case object AncientCrystalEnchantAm extends ItemType {
    override def name: String = "AncientCrystalEnchantAm"

    override def fileValue: String = "ancient_crystal_enchant_am"

    override def code: Int = 35
  }

  case object RuneSelect extends ItemType {
    override def name: String = "RuneSelect"

    override def fileValue: String = "rune_select"

    override def code: Int = 36
  }

  case object Rune extends ItemType {
    override def name: String = "Rune"

    override def fileValue: String = "rune"

    override def code: Int = 37
  }

}
   