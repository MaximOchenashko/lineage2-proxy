package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait EtcItemType extends FileDataBased

object EtcItemType extends FileDataEnumHolder[EtcItemType] {
  override def values: Seq[EtcItemType] = Seq(None, Arrow, Potion, ScrlEnchantWp, ScrlEnchantAm, Scroll, Recipe, Material, PetCollar, CastleGuard, Lotto, RaceTicket, Dye, Seed, Crop, Maturecrop, Harvest, Seed2, TicketOfLord, Lure, BlessScrlEnchantWp, BlessScrlEnchantAm, Coupon, Elixir, ScrlEnchantAttr, Bolt, ScrlIncEnchantPropWp, ScrlIncEnchantPropAm, AncientCrystalEnchantWp, AncientCrystalEnchantAm, RuneSelect, Rune)

  case object None extends EtcItemType {
    override def name: String = "None"

    override def fileValue: String = "none"

    override def code: Int = 0
  }

  case object Arrow extends EtcItemType {
    override def name: String = "Arrow"

    override def fileValue: String = "arrow"

    override def code: Int = 1
  }

  case object Potion extends EtcItemType {
    override def name: String = "Potion"

    override def fileValue: String = "potion"

    override def code: Int = 2
  }

  case object ScrlEnchantWp extends EtcItemType {
    override def name: String = "ScrlEnchantWp"

    override def fileValue: String = "scrl_enchant_wp"

    override def code: Int = 3
  }

  case object ScrlEnchantAm extends EtcItemType {
    override def name: String = "ScrlEnchantAm"

    override def fileValue: String = "scrl_enchant_am"

    override def code: Int = 4
  }

  case object Scroll extends EtcItemType {
    override def name: String = "Scroll"

    override def fileValue: String = "scroll"

    override def code: Int = 5
  }

  case object Recipe extends EtcItemType {
    override def name: String = "Recipe"

    override def fileValue: String = "recipe"

    override def code: Int = 6
  }

  case object Material extends EtcItemType {
    override def name: String = "Material"

    override def fileValue: String = "material"

    override def code: Int = 7
  }

  case object PetCollar extends EtcItemType {
    override def name: String = "PetCollar"

    override def fileValue: String = "pet_collar"

    override def code: Int = 8
  }

  case object CastleGuard extends EtcItemType {
    override def name: String = "CastleGuard"

    override def fileValue: String = "castle_guard"

    override def code: Int = 9
  }

  case object Lotto extends EtcItemType {
    override def name: String = "Lotto"

    override def fileValue: String = "lotto"

    override def code: Int = 10
  }

  case object RaceTicket extends EtcItemType {
    override def name: String = "RaceTicket"

    override def fileValue: String = "race_ticket"

    override def code: Int = 11
  }

  case object Dye extends EtcItemType {
    override def name: String = "Dye"

    override def fileValue: String = "dye"

    override def code: Int = 12
  }

  case object Seed extends EtcItemType {
    override def name: String = "Seed"

    override def fileValue: String = "seed"

    override def code: Int = 13
  }

  case object Crop extends EtcItemType {
    override def name: String = "Crop"

    override def fileValue: String = "crop"

    override def code: Int = 14
  }

  case object Maturecrop extends EtcItemType {
    override def name: String = "Maturecrop"

    override def fileValue: String = "maturecrop"

    override def code: Int = 15
  }

  case object Harvest extends EtcItemType {
    override def name: String = "Harvest"

    override def fileValue: String = "harvest"

    override def code: Int = 16
  }

  case object Seed2 extends EtcItemType {
    override def name: String = "Seed2"

    override def fileValue: String = "seed2"

    override def code: Int = 17
  }

  case object TicketOfLord extends EtcItemType {
    override def name: String = "TicketOfLord"

    override def fileValue: String = "ticket_of_lord"

    override def code: Int = 18
  }

  case object Lure extends EtcItemType {
    override def name: String = "Lure"

    override def fileValue: String = "lure"

    override def code: Int = 19
  }

  case object BlessScrlEnchantWp extends EtcItemType {
    override def name: String = "BlessScrlEnchantWp"

    override def fileValue: String = "bless_scrl_enchant_wp"

    override def code: Int = 20
  }

  case object BlessScrlEnchantAm extends EtcItemType {
    override def name: String = "BlessScrlEnchantAm"

    override def fileValue: String = "bless_scrl_enchant_am"

    override def code: Int = 21
  }

  case object Coupon extends EtcItemType {
    override def name: String = "Coupon"

    override def fileValue: String = "coupon"

    override def code: Int = 22
  }

  case object Elixir extends EtcItemType {
    override def name: String = "Elixir"

    override def fileValue: String = "elixir"

    override def code: Int = 23
  }

  case object ScrlEnchantAttr extends EtcItemType {
    override def name: String = "ScrlEnchantAttr"

    override def fileValue: String = "scrl_enchant_attr"

    override def code: Int = 24
  }

  case object Bolt extends EtcItemType {
    override def name: String = "Bolt"

    override def fileValue: String = "bolt"

    override def code: Int = 25
  }

  case object ScrlIncEnchantPropWp extends EtcItemType {
    override def name: String = "ScrlIncEnchantPropWp"

    override def fileValue: String = "scrl_inc_enchant_prop_wp"

    override def code: Int = 26
  }

  case object ScrlIncEnchantPropAm extends EtcItemType {
    override def name: String = "ScrlIncEnchantPropAm"

    override def fileValue: String = "scrl_inc_enchant_prop_am"

    override def code: Int = 27
  }

  case object AncientCrystalEnchantWp extends EtcItemType {
    override def name: String = "AncientCrystalEnchantWp"

    override def fileValue: String = "ancient_crystal_enchant_wp"

    override def code: Int = 28
  }

  case object AncientCrystalEnchantAm extends EtcItemType {
    override def name: String = "AncientCrystalEnchantAm"

    override def fileValue: String = "ancient_crystal_enchant_am"

    override def code: Int = 29
  }

  case object RuneSelect extends EtcItemType {
    override def name: String = "RuneSelect"

    override def fileValue: String = "rune_select"

    override def code: Int = 30
  }

  case object Rune extends EtcItemType {
    override def name: String = "Rune"

    override def fileValue: String = "rune"

    override def code: Int = 31
  }

}
   