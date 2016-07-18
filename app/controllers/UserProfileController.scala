package controllers

import javax.inject.{Inject, Singleton}

import common.enums.UserRole
import controllers.UserProfileController.{AuthResponse, Credentials, SignUp, UserDetail}
import controllers.base.BaseController
import play.api.libs.json.Json
import play.api.mvc._
import play.mvc.Http.MimeTypes
import services.security.AuthService
import services.security.AuthService.AuthInfo

import scala.concurrent.{ExecutionContext, Future}

/**
 * @author iRevThis
 */
@Singleton
class UserProfileController @Inject()(val authService: AuthService)
                                     (implicit ec: ExecutionContext) extends BaseController  {

  implicit val stringResponseFormat = Json.format[AuthResponse]
  implicit val userDetailFormat = Json.format[UserDetail]
  implicit val signUpFormat = Json.format[SignUp]
  implicit val credentialsFormat = Json.format[Credentials]

  def userProfile = authorized() { implicit request =>
    val user = request.authInfo
    ok(UserDetail(user.login, user.apiToken))
  }

  def signUp = Action.async(parse.json[SignUp]) { request =>
    val dto = request.body
    authService.userExists(dto.email) flatMap {
      case false =>
        for {
          _ <- authService.createUser(dto.name, dto.email, dto.password, UserRole.User)
        } yield Ok
      case true => Future.successful(Conflict)
    }
  }

  def login = Action.async(parse.json[Credentials]) { request =>
    val credentials = request.body
    authService.authenticate(credentials.email, credentials.password) flatMap {
      case Some(x) =>
        val authInfo = AuthInfo(x.id, x.name, x.password, UserRole.byCode(x.role).get, x.apiToken)
        for {
          tokenKey <- authService.authorize(authInfo, credentials.rememberMe)
        } yield Ok(Json.toJson(AuthResponse(tokenKey, x.apiToken, x.name)))
          .withHeaders(AUTHORIZATION -> tokenKey)
          .as(MimeTypes.JSON)

      case None =>
        Future successful NotFound
    }
  }

}

object UserProfileController {
  case class SignUp(name: String, email: String, password: String)
  case class Credentials(email: String, password: String, rememberMe: Boolean)
  case class UserDetail(name: String, userToken: String)
  case class AuthResponse(tokenKey: String, xApiToken: String, userName: String)
}
