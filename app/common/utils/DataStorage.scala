package common.utils

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

import common.enums.game.ItemType

import scala.io.Source
import scala.language.postfixOps

import scalaz._
import Scalaz._

/**
  * @author iRevThis
  */
object DataStorage {

  val skillsData = {
    val validLines = Source.fromFile(getFile("skill_data.txt"), StandardCharsets.UTF_8.displayName).getLines
      .map(_.split(" "))
      .filter(f => isNumeric(f(0)) && isNumeric(f(2)))

    validLines
      .map { f =>
        val displayId = f(1).parseInt getOrElse 0
        val skillName = f.drop(4).map(_.replace("\\0", "")).mkString(" ")
        SkillData(f(0).toInt, displayId, f(2).toInt, f(3).toInt, skillName)
      }
      .toSeq
  }

  val systemMsgs = {
    val validLines = Source.fromFile(getFile("systemmsg.txt"), StandardCharsets.UTF_8.displayName).getLines
      .map(_.split(" "))
      .filter(f => f != null && f.length > 1 && isNumeric(f(0)))

    validLines map { f => SystemMsg(f(0).toInt, f.drop(1).mkString(" ")) } toSeq
  }

  val npcsData = {
    val validLines = Source.fromFile(getFile("npc_data.txt"), StandardCharsets.UTF_8.displayName).getLines
      .map(_.split(" "))
      .filter(f => isNumeric(f(0)))

    validLines map { f => NpcData(f(0).toInt, f.drop(1).mkString(" ")) } toSeq
  }

  val itemsData = {
    val validLines = Source.fromFile(getFile("item_data.txt"), StandardCharsets.UTF_8.displayName).getLines
      .map(_.split(" "))
      .filter(f => isNumeric(f(0)))

    validLines map { f => ItemData(f(0).toInt, f(1), f(2), f.drop(3).mkString(" ")) } toSeq
  }

  private def getFile(str: String): File =
    Paths.get(new File("data").getAbsolutePath, str).toFile

  private def isNumeric(str: String): Boolean =
    str forall Character.isDigit

  sealed trait GameData {
    def id: Int
  }

  case class ItemData(id: Int, arg1: String, arg2: String, name: String) extends GameData {
    val stackable: Boolean = arg2.equalsIgnoreCase("stackable") || arg2.equalsIgnoreCase("asset")
    val itemType = ItemType.byName(arg1).getOrElse("Unknown")
  }

  case class NpcData(id: Int, name: String) extends GameData

  case class SkillData(id: Int, displayId: Int, isMagic: Int, isNegative: Int, name: String) extends GameData

  case class SystemMsg(id: Int, message: String) extends GameData

}
