package common.enums.base

/**
  * @author iRevThis
  */
trait FileDataBased extends EnumLike {
  def fileValue: String
}

trait FileDataEnumHolder[T <: FileDataBased] extends EnumHolder[T] {
  def byFileValue(value: String) = values.find(_.fileValue == value)
}
