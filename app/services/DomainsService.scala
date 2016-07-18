package services

import java.awt.{Polygon => JPolygon}
import javax.inject.{Inject, Singleton}

import play.api.Environment
import play.api.libs.json.{Format, Json}

/**
  * @author iRevThis
  */
@Singleton
class DomainsService @Inject()(env: Environment) {

  import DomainsService._

  lazy val domains = env.resourceAsStream("json/domains.json")
    .map { source => Json.parse(source).as[Seq[Domain]] }
    .get
    .map { r =>
      val polygons = r.polygon.map { v =>
        val points = v.coordinates.map(v => v.x -> v.y).toArray.unzip
        new JPolygon(points._1, points._2, points._1.length)
      }
      GeometryDomain(r.name, r.id, polygons)
    }

  def territory(x: Int, y: Int): Option[String] =
    domains collectFirst {
      case GeometryDomain(name, _, poly) if poly.exists(_.contains(x, y)) =>
        name
    }

}

object DomainsService {

  private implicit val locationFormat: Format[Location] = Json.format[Location]
  private implicit val polygonFormat: Format[Polygon] = Json.format[Polygon]
  private implicit val domainFormat: Format[Domain] = Json.format[Domain]

  case class GeometryDomain(name: String, id: Int, polygons: Seq[JPolygon])

  case class Domain(name: String, id: Int, polygon: Seq[Polygon])

  case class Polygon(coordinates: Seq[Location])

  case class Location(x: Int, y: Int, z: Int, offset: Int)

}
