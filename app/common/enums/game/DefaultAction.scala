package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait DefaultAction extends FileDataBased

object DefaultAction extends FileDataEnumHolder[DefaultAction] {
  override def values: Seq[DefaultAction] = Seq(Equip, Peel, None, SkillReduce, Soulshot, Recipe, SkillMaintain, Spiritshot, Dice, Calc, Seed, Harvest, Capsule, XmasOpen, ShowHtml, ShowSsqStatus, Fishingshot, SummonSoulshot, SummonSpiritshot, CallSkill, ShowAdventurerGuideBook, KeepExp, CreateMpcc, NickColor, HideName, StartQuest)

  case object Equip extends DefaultAction {
    override def name: String = "Equip"

    override def fileValue: String = "action_equip"

    override def code: Int = 0
  }

  case object Peel extends DefaultAction {
    override def name: String = "Peel"

    override def fileValue: String = "action_peel"

    override def code: Int = 1
  }

  case object None extends DefaultAction {
    override def name: String = "None"

    override def fileValue: String = "action_none"

    override def code: Int = 2
  }

  case object SkillReduce extends DefaultAction {
    override def name: String = "SkillReduce"

    override def fileValue: String = "action_skill_reduce"

    override def code: Int = 3
  }

  case object Soulshot extends DefaultAction {
    override def name: String = "Soulshot"

    override def fileValue: String = "action_soulshot"

    override def code: Int = 4
  }

  case object Recipe extends DefaultAction {
    override def name: String = "Recipe"

    override def fileValue: String = "action_recipe"

    override def code: Int = 5
  }

  case object SkillMaintain extends DefaultAction {
    override def name: String = "SkillMaintain"

    override def fileValue: String = "action_skill_maintain"

    override def code: Int = 6
  }

  case object Spiritshot extends DefaultAction {
    override def name: String = "Spiritshot"

    override def fileValue: String = "action_spiritshot"

    override def code: Int = 7
  }

  case object Dice extends DefaultAction {
    override def name: String = "Dice"

    override def fileValue: String = "action_dice"

    override def code: Int = 8
  }

  case object Calc extends DefaultAction {
    override def name: String = "Calc"

    override def fileValue: String = "action_calc"

    override def code: Int = 9
  }

  case object Seed extends DefaultAction {
    override def name: String = "Seed"

    override def fileValue: String = "action_seed"

    override def code: Int = 10
  }

  case object Harvest extends DefaultAction {
    override def name: String = "Harvest"

    override def fileValue: String = "action_harvest"

    override def code: Int = 11
  }

  case object Capsule extends DefaultAction {
    override def name: String = "Capsule"

    override def fileValue: String = "action_capsule"

    override def code: Int = 12
  }

  case object XmasOpen extends DefaultAction {
    override def name: String = "XmasOpen"

    override def fileValue: String = "action_xmas_open"

    override def code: Int = 13
  }

  case object ShowHtml extends DefaultAction {
    override def name: String = "ShowHtml"

    override def fileValue: String = "action_show_html"

    override def code: Int = 14
  }

  case object ShowSsqStatus extends DefaultAction {
    override def name: String = "ShowSsqStatus"

    override def fileValue: String = "action_show_ssq_status"

    override def code: Int = 15
  }

  case object Fishingshot extends DefaultAction {
    override def name: String = "Fishingshot"

    override def fileValue: String = "action_fishingshot"

    override def code: Int = 16
  }

  case object SummonSoulshot extends DefaultAction {
    override def name: String = "SummonSoulshot"

    override def fileValue: String = "action_summon_soulshot"

    override def code: Int = 17
  }

  case object SummonSpiritshot extends DefaultAction {
    override def name: String = "SummonSpiritshot"

    override def fileValue: String = "action_summon_spiritshot"

    override def code: Int = 18
  }

  case object CallSkill extends DefaultAction {
    override def name: String = "CallSkill"

    override def fileValue: String = "action_call_skill"

    override def code: Int = 19
  }

  case object ShowAdventurerGuideBook extends DefaultAction {
    override def name: String = "ShowAdventurerGuideBook"

    override def fileValue: String = "action_show_adventurer_guide_book"

    override def code: Int = 20
  }

  case object KeepExp extends DefaultAction {
    override def name: String = "KeepExp"

    override def fileValue: String = "action_keep_exp"

    override def code: Int = 21
  }

  case object CreateMpcc extends DefaultAction {
    override def name: String = "CreateMpcc"

    override def fileValue: String = "action_create_mpcc"

    override def code: Int = 22
  }

  case object NickColor extends DefaultAction {
    override def name: String = "NickColor"

    override def fileValue: String = "action_nick_color"

    override def code: Int = 23
  }

  case object HideName extends DefaultAction {
    override def name: String = "HideName"

    override def fileValue: String = "action_hide_name"

    override def code: Int = 24
  }

  case object StartQuest extends DefaultAction {
    override def name: String = "StartQuest"

    override def fileValue: String = "action_start_quest"

    override def code: Int = 25
  }

}
   