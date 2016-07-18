package model.prototypes

import model.L2Creature
import model.embedable._

/**
  * @author iRevThis
  */
trait L2CharBased extends L2Creature {
  def appearance: Appearance
  def clanInfo: ClanInfo
  def fishingLocation: FishingLocation
  def mountInfo: MountInfo
  def etcCharInfo: EtcCharInfo
  def charParams: CharParams
  def charAdditionInfo: CharAdditionInfo
  def classId: Int
  def mainClassId: Int
  def level: Int
  def cubics: Set[Int]
}
