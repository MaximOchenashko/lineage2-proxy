package controllers.base

import play.api.http.{ContentTypeOf, HeaderNames, MimeTypes, Writeable}
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.mvc.{Request, Result, Results}

import scala.concurrent.Future
import scala.util.Try

/**
  * @author iRevThis
  */
trait WsResponses {

  implicit class WSClientEx(ws: WSClient) {
    def jsonReq(path: String) = ws.url(path).withHeaders(HeaderNames.CONTENT_TYPE -> MimeTypes.JSON)
  }

  implicit class WSResponseEx(response: WSResponse) {
    def toResult = {
      Results.Status(response.status)(response.body)
        .withHeaders(response.allHeaders.map(j => (j._1, j._2.head)).toSeq: _*)
        .as(MimeTypes.JSON)
    }

    def onSuccess(f: (WSResponse) => Result): Result = {
      response.status match {
        case 200 => f(response)
        case _ => response.toResult
      }
    }

    def jsonBodyOpt = Try(Option(response.json)).getOrElse(Option(response.body).map(r => Json.toJson(r)))
    def jsonBody = jsonBodyOpt.get
  }

  implicit class WSRequestEx(request: WSRequest) {
    def copyQueryStrings(keys: String*)(implicit req: Request[_]) = {
      val queryParams = keys.map(key => {
        req.getQueryString(key) match {
          case Some(x) => Some(key -> x)
          case None => None
        }
      }).filter(_.isDefined).map(_.get)
      request.withQueryString(queryParams: _*)
    }

    def tryGet = {
      Try(request.get()).recover {
        case t: Throwable => Future.failed(t)
      }.get
    }

    def tryPost[T](body: T)(implicit wrt: Writeable[T], ct: ContentTypeOf[T]) = {
      Try(request.post(body)).recover {
        case t: Throwable => Future.failed(t)
      }.get
    }

    def tryPut[T](body: T)(implicit wrt: Writeable[T], ct: ContentTypeOf[T]) = {
      Try(request.put(body)).recover {
        case t: Throwable => Future.failed(t)
      }.get
    }

    def tryDelete = {
      Try(request.delete).recover {
        case t: Throwable => Future.failed(t)
      }.get
    }
  }

  private[this] def presentQueryParams(keys: String*)(implicit req: Request[_]) = {
    keys.map(key => {
      req.getQueryString(key) match {
        case Some(x) => Some(key -> x)
        case None => None
      }
    }).filter(_.isDefined).map(_.get)
  }

}
