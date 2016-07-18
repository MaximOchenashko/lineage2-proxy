package services.security

import java.sql.Timestamp
import java.time.Instant
import java.util.UUID
import javax.inject.{Inject, Singleton}

import common.enums.UserRole
import models.Tables.{Users, UsersRow}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{Format, Json}
import services.RedisService
import services.RedisService.RedisObject
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author iRevThis
  */
@Singleton
class AuthService @Inject()(redisService: RedisService,
                            val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import AuthService._
  import driver.api._

  import scalaz._
  import OptionT._
  import Scalaz._

  def loadToken(key: String): Future[Option[AuthToken]] =
    redisService get key

  def authenticate(key: String): Future[Option[AuthInfo]] =
    (for {
      token <- optionT(loadToken(key))
    } yield token.authInfo).run

  def authorize(authInfo: AuthInfo, rememberMe: Boolean): Future[String] = {
    val token = AuthToken(UUID.randomUUID.toString, authInfo)
    val expirationTime = rememberMe ? DefaultRememberMeSeconds | DefaultExpireSeconds
    redisService.save(token, expirationTime) collect { case success if success => token.key }
  }

  def authenticate(email: String, password: String): Future[Option[UsersRow]] =
    db.run(UserByEmailAndPwdQuery(email, password).result.headOption)

  def logout(key: String): Unit =
    redisService delete key

  def userExists(email: String): Future[Boolean] =
    db.run(UserByEmailQuery(email).extract.exists.result)

  def createUser(name: String, email: String, password: String, userRole: UserRole): Future[Int] = {
    val newUser = UsersRow(UUID.randomUUID(), now, name, UUID.randomUUID().toString.toUpperCase,
      None, email, enabled = true, password, userRole.code)

    db.run(Users += newUser)
  }

  private def now = Timestamp.from(Instant.now)
}

object AuthService {
  //1 hour
  private val DefaultExpireSeconds = 60 * 60
  //two weeks
  private val DefaultRememberMeSeconds = 14 * 24 * 60 * 60

  import models.Tables.profile.api._

  private val UserByEmailQuery = Compiled { (email: Rep[String]) =>
    for {u <- Users if u.email.toLowerCase === email.toLowerCase} yield u
  }

  private val UserByEmailAndPwdQuery = Compiled { (email: Rep[String], pwd: Rep[String]) =>
    for {
      u <- Users if u.email.toLowerCase === email.toLowerCase && u.password === pwd
    } yield u
  }

  case class AuthInfo(uuid: UUID, login: String, password: String, role: UserRole, apiToken: String)

  case class AuthToken(key: String, authInfo: AuthInfo) extends RedisObject

  implicit val userRoleFormat: Format[UserRole] = Format(UserRole.jsonReads, UserRole.jsonWrites)
  implicit val authInfoFormat: Format[AuthInfo] = Json.format[AuthInfo]
  implicit val tokenFormat: Format[AuthToken] = Json.format[AuthToken]

}
