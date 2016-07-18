package controllers.base

import java.io.File
import java.sql.Timestamp
import java.time.Instant

import controllers.base.ApiResponses.ListWrapper
import controllers.base.BaseController.AuthenticatedRequest
import play.api.http.{HeaderNames, MimeTypes, Status}
import play.api.libs.json._
import play.api.mvc.{Result, Results}

import scala.util.{Failure, Success, Try}

/**
 * @author iRevThis
 */
trait ApiResponses {

  implicit def timestampReads: Reads[Timestamp] = new Reads[Timestamp] {
    override def reads(json: JsValue): JsResult[Timestamp] = {
      Try(json.as[Long]) match {
        case Success(x) => JsSuccess(Timestamp.from(Instant.ofEpochMilli(x)))
        case Failure(e) => JsError(e.getMessage)
      }
    }
  }

  implicit def timestampWrites: Writes[Timestamp] = new Writes[Timestamp] {
    override def writes(o: Timestamp): JsValue = JsNumber(o.toInstant.toEpochMilli)
  }

  implicit def listWrapperReads[X](implicit fmt: Reads[X]): Reads[ListWrapper[X]] = new Reads[ListWrapper[X]] {
    override def reads(json: JsValue): JsResult[ListWrapper[X]] = json match {
      case obj: JsObject => try {
        val items = (json \ "items").as[JsArray].value.map(_.as(fmt))
        val offset = (json \ "offset").as[Long]
        val limit = (json \ "limit").as[Long]
        val total = (json \ "total").as[Long]
        JsSuccess(ListWrapper[X](items, offset, limit, total))
      } catch {
        case e: Throwable => JsError(e.getMessage)
      }
      case _ => JsError("JsObject expected")
    }
  }

  implicit def listWrapperWrites[X](implicit fmt: Writes[X]): Writes[ListWrapper[X]] = new Writes[ListWrapper[X]] {
    override def writes(o: ListWrapper[X]): JsValue = Json.obj(
      "items" -> o.items.map(Json.toJson(_)(fmt)),
      "offset" -> JsNumber(o.offset),
      "limit" -> JsNumber(o.limit),
      "total" -> JsNumber(o.total)
    )
  }

  def fileResult(data: Array[Byte], dispositionHeader: String) =
    Results.Ok(data).as(MimeTypes.BINARY).withHeaders(HeaderNames.CONTENT_DISPOSITION -> dispositionHeader)

  def fileResult(file: File, filename: String): Result =
    Results.Status(200).sendFile(file, inline = false, _ => filename)

  def ok[X](list: Seq[X], offset: Long, limit: Long, total: Long)(implicit request: AuthenticatedRequest[_], fmt: Writes[X]): Result = ok(ListWrapper[X](list, offset, limit, total))

  def ok[X](list: Seq[X])(implicit request: AuthenticatedRequest[_], fmt: Writes[X]): Result = ok(ListWrapper[X](list))

  def ok[X](any: X = null)(implicit request: AuthenticatedRequest[_], fmt: Writes[X]): Result = apiResult(Status.OK, any)

  def apiResult[X](code: Int, content: X = null)(implicit request: AuthenticatedRequest[_], fmt: Writes[X]): Result = {
    Option(content) match {
      case Some(x) => Results.Status(code)(Json.toJson(x)).as(MimeTypes.JSON).withHeaders(HeaderNames.AUTHORIZATION -> request.keys.authKey.get)
      case None => Results.Status(code).as(MimeTypes.JSON).withHeaders(HeaderNames.AUTHORIZATION -> request.keys.authKey.get)
    }
  }

}

object ApiResponses {
  case class ListWrapper[X](items: Seq[X], offset: Long, limit: Long, total: Long)

  object ListWrapper {
    def apply[X](items: Seq[X]): ListWrapper[X] = ListWrapper(items, 0L, items.size, items.size)
  }

}
