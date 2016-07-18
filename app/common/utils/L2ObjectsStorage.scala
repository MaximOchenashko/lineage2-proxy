package common.utils

import model.L2Object

import scala.collection.mutable

/**
  * @author iRevThis
  */
class L2ObjectsStorage[X <: L2Object] {

  val objects = mutable.HashMap.empty[Int, X]

  def add(obj: X) = objects.update(obj.objectId, obj)

  def get(objId: Int) = objects.get(objId)

  def delete(objId: Int) = objects.remove(objId)

  def count = objects.size

  def seq(p: X => Boolean) = objects.values.filter(p).toSeq

  def exists(p: X => Boolean) = objects.values.exists(p)

}
