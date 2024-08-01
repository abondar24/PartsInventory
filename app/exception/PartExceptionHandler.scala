package exception

import com.google.inject.{Inject, Singleton}
import play.api.http.HttpErrorHandler
import play.api.http.Status.{BAD_REQUEST, NOT_FOUND}
import play.api.libs.json.Json
import play.api.mvc.Results.{BadRequest, InternalServerError, NotFound, Status}
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PartExceptionHandler @Inject() extends HttpErrorHandler{

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    statusCode match {
      case NOT_FOUND => Future.successful(NotFound(Json.obj("error" -> message)))
      case BAD_REQUEST => Future.successful(BadRequest(Json.obj("error" -> message)))
      case _ => Future.successful(Status(statusCode)(Json.obj("error" -> message)))
    }
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    exception match {
      case e: PartNotFoundException => Future.successful(NotFound(Json.obj("error" -> e.getMessage)))
      case e: PartDetailNotFoundException => Future.successful(NotFound(Json.obj("error" -> e.getMessage)))
      case _ => Future.successful(InternalServerError(Json.obj("error" -> "An unexpected error occurred")))
    }
  }
}
