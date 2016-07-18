package controllers.base

import play.api.Logger
import play.api.http.{HttpErrorHandler, MimeTypes}
import play.api.mvc.{RequestHeader, Results}

import scala.concurrent._

/**
  * @author iRevThis
  */
class GlobalErrorHandler extends HttpErrorHandler with Results {
  def onServerError(request: RequestHeader, e: Throwable) = {
    Future.successful {
      Logger.error(s"Error on request ${request.path}", e)
      InternalServerError(e.getMessage).as(MimeTypes.JSON)
    }
  }

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful {
      //Logger.error(s"Client error on request ${request.path}. Status: $statusCode. Message: $message")
      Status(statusCode)(message).as(MimeTypes.JSON)
    }
  }
}
