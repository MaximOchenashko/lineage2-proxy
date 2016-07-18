package services

import java.nio.charset.StandardCharsets
import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.Configuration
import play.api.libs.json.{Json, Reads, Writes}
import redis.RedisClient
import services.RedisService.RedisObject

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author iRevThis
  */
@Singleton
class RedisService @Inject()(conf: Configuration)
                            (implicit system: ActorSystem, ec: ExecutionContext) {

  import scalaz._
  import Scalaz._
  import OptionT._

  private[this] lazy val (host, port, password, db, name) = (
    conf.getString("redis.host") | "localhost",
    conf.getInt("redis.port") | 6379,
    conf.getString("redis.password"),
    conf.getInt("redis.db"),
    conf.getString("redis.name") | "RedisClient"
    )

  private[this] lazy val client = new RedisClient(host, port, password, db, name)

  def get[X <: RedisObject](key: String)(implicit reads: Reads[X]): Future[Option[X]] =
    (for {
      buffer <- optionT(client get key)
    } yield Json.parse(buffer.decodeString(StandardCharsets.UTF_8.name)).as[X]).run

  def save[X <: RedisObject](obj: X, ttl: Long)(implicit writes: Writes[X]): Future[Boolean] =
    client.setex(obj.key, ttl, Json.toJson(obj).toString)

  def delete(key: String): Future[Long] =
    client del key

  def delete[X <: RedisObject](obj: X): Future[Long] =
    client del obj.key

  def exists(key: String): Future[Boolean] =
    client exists key

}

object RedisService {

  trait RedisObject {
    def key: String
  }

}
