package dto.tcp.clientpackets

import model.L2Object

/**
 * @author iRevThis
 */
trait UpdateType[X <: L2Object] extends UpdateAction {

  def objectId: Int

  def update(obj: X): Option[X]

}
