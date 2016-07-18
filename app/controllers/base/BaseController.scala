package controllers.base

import java.net.URLDecoder
import java.sql.Timestamp
import java.time.Instant

import common.enums.UserRole
import controllers.base.BaseController._
import play.api.http.HeaderNames
import play.api.libs.Files.TemporaryFile
import play.api.libs.json._
import play.api.mvc._
import play.mvc.Http
import services.security.AuthService
import services.security.AuthService.AuthInfo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * @author iRevThis
 */
trait BaseController extends Controller with ApiResponses {

  import scalaz._
  import Scalaz._

  def authService: AuthService

  implicit def responseWrapperReads[X](implicit fmt: Reads[X]) = new Reads[ResponseWrapper[X]] {
    override def reads(json: JsValue): JsResult[ResponseWrapper[X]] = {
      val content = (json \ "content").as[X]
      JsSuccess(ResponseWrapper(content))
    }
  }

  implicit def responseWrapperWrites[X](implicit fmt: Writes[X]): Writes[ResponseWrapper[X]] = new Writes[ResponseWrapper[X]] {
    def writes(ts: ResponseWrapper[X]) = JsObject(Seq(
      "content" -> Json.toJson(ts)
    ))
  }

  protected def authorized(allowedRoles: Seq[UserRole] = UserRole.values) = Authenticated andThen authCheck(allowedRoles)

  private object Authenticated extends ActionBuilder[AuthenticatedRequest] with ActionTransformer[Request, AuthenticatedRequest] {

    override protected def transform[A](request: Request[A]): Future[AuthenticatedRequest[A]] =
      request.headers.get(HeaderNames.AUTHORIZATION) match {
        case Some(key) =>
          val xApiToken = request.headers.get("X-Api-Token")
          for {
            userInfo <- authService.authenticate(key)
          } yield AuthenticatedRequest(userInfo.orNull, ApiKeys(key.some, xApiToken), request)
        case None =>
          Future successful AuthenticatedRequest(null, ApiKeys(None, None), request)
      }

  }

  private def authCheck(allowedRoles: Seq[UserRole]): ActionFilter[AuthenticatedRequest] =
    new ActionFilter[AuthenticatedRequest] {

      import play.api.mvc.Result

      override protected def filter[A](request: AuthenticatedRequest[A]) = Future.successful {
        Option(request.authInfo)
          .filter(a => allowedRoles.contains(a.role))
          .fold[Option[Result]](Some(Unauthorized))(x => Option.empty[Result])
      }
    }

  protected def files(implicit request: Request[MultipartFormData[TemporaryFile]]) = request.body.files
  protected def tokenKey(implicit request: Request[_]) = request.headers.get(Http.HeaderNames.AUTHORIZATION)
  protected def offset(implicit request: Request[_]) = decodedQueryString("offset").map(_.toLong).getOrElse(0L)
  protected def limit(implicit request: Request[_]) = decodedQueryString("limit").map(_.toLong).getOrElse(30L)
  protected def filter(implicit request: Request[_]) = decodedQueryString("$filter")
  protected def orderBy(implicit request: Request[_]) = decodedQueryString("$orderby")
  protected def decodedQueryString(key: String)(implicit request: Request[_]) = request.getQueryString(key).map(URLDecoder.decode(_, "UTF-8"))
  protected def decodedQueryStrings(key: String)(implicit request: Request[_]) = request.queryString.get(key).map(_.map(URLDecoder.decode(_, "UTF-8")))
  protected def now = Timestamp.from(Instant.now)

}

object BaseController {

  case class AuthenticatedRequest[A](authInfo: AuthInfo, keys: ApiKeys, request: Request[A]) extends WrappedRequest[A](request)

  case class ResponseWrapper[X](content: X)
  case class ListResponse[X](items: Seq[X], offset: Long, limit: Long, total: Long)
  case class ApiKeys(authKey: Option[String], xApiToken: Option[String])

}
