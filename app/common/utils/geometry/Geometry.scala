package common.utils.geometry

import play.api.libs.json.{JsValue, Json, Writes}

/**
 * @author iRevThis
 */
trait Point2D {
  def x: Int
  def y: Int
}

trait Point3D extends Point2D {
  def y: Int
}

case class Location(x: Int, y: Int, z: Int, h: Int = 0) extends Point3D {
  def distance(x: Int, y: Int) = {
    val dx = this.x - x
    val dy = this.y - y
    math.sqrt(dx * dx + dy * dy)
  }

  def distance(loc: Location): Double = distance(loc.x, loc.y)
}

object Location {
  implicit val locationWrites = new Writes[Location] {
    override def writes(loc: Location): JsValue = Json.obj(
      "x" -> loc.x,
      "y" -> loc.y,
      "z" -> loc.z,
      "h" -> loc.h
    )
  }
}