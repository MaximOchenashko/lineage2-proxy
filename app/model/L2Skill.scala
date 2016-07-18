package model

/**
 * @author iRevThis
 */
class L2Skill(skillId: Int, level: Int, duration: Int, displayId: Int,
              isMagic: Int, isNegative: Int, name: String,
              passive: Boolean, disabled: Boolean, enchanted: Int) extends L2Effect(skillId, level, duration) {

}
