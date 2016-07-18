package model

import common.utils.geometry.Location
import model.embedable.{AbnormalEffects, Stats}

import scala.collection.mutable

/**
 * @author iRevThis
 */
trait L2Creature extends L2Object {

  def name: String
  def title: String
  def stats: Stats
  def dead: Boolean
  def effects: mutable.Buffer[L2Effect]
  def abnormalEffects: AbnormalEffects
  def location: Location

  def updateEffect(eff: L2Effect): Unit = { }
  def updateEffects(effs: Seq[L2Effect]): Unit = { }

}