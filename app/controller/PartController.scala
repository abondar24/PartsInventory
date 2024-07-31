package controller

import com.google.inject.{Inject, Singleton}
import exception.PartNotFoundException
import model.web.{PartCreateRequest, PartCreateResponse, PartDetailResponse, PartResponse, PartUpdateRequest}
import model.{Part, PartDetail}
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request, Result}
import service.PartService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PartController @Inject()(components: ControllerComponents, partService: PartService)(implicit ec: ExecutionContext)
  extends AbstractController(components) {


  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[PartCreateRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(errors)))),
      partCreateRequest => {
        val part = Part(
          id = None,
          name = partCreateRequest.name,
          quantity = partCreateRequest.quantity,
          price = partCreateRequest.price
        )

        val partDetails = partCreateRequest.details.map { detailRequest =>
          PartDetail(
            id = None,
            partId = None,
            description = detailRequest.description
          )
        }

        Future {
          partService.create(part, partDetails)
          val partId = part.id.getOrElse(-1L)
          val detailIds = partDetails.map(_.id.getOrElse(-1L)) // Here, IDs need to be gathered if generated

          val response = PartCreateResponse(partId, detailIds)
          Created(Json.toJson(response))
        }(ec)
      }
    )
  }


  def findById(id: Long): Action[AnyContent] = Action.async {
    Future {
      val part = partService.findById(id)
      val response = PartResponse(part.name, part.quantity, part.price)
      Ok(Json.toJson(response))
    }
  }

  def findByName(name: String): Action[AnyContent] = Action.async {
    Future {
      val part = partService.findByName(name)
      val response = PartResponse(part.name, part.quantity, part.price)
      Ok(Json.toJson(response))
    }
  }

  def findAll(): Action[AnyContent] = Action.async { request =>
    processPaginationParams(request) { (offset,limit)=>
      Future {
        val parts = partService.findAll(offset, limit)
        if (parts.isEmpty) {
          NoContent
        } else {
          val partResponse = parts.map(part => PartResponse(part.name, part.quantity, part.price))
          Ok(Json.toJson(partResponse))
        }
      }
    }
  }

  def findPartDetails(id: Long): Action[AnyContent] = Action.async {request=>
    processPaginationParams(request) { (offset,limit)=>
      Future {
        val partDetails = partService.findPartDetails(id, offset, limit)
        if (partDetails.isEmpty) {
          NoContent
        } else {
          val detailsResponse = partDetails.map(detail => PartDetailResponse(detail.description))
          Ok(Json.toJson(detailsResponse))
        }
      }
    }
  }


  def update(id: Long): Action[JsValue] = Action.async(parse.json) {request=>
    request.body.validate[PartUpdateRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(errors)))),
      updateRequest => {
        val partUpdate = {

          if (updateRequest.name.isDefined || updateRequest.quantity.isDefined || updateRequest.price.isDefined) {
            Some(Part(
              id = Some(id),
              name =  updateRequest.name.get,
              quantity = updateRequest.quantity.get,
              price = updateRequest.price.get
            ))
          } else {
            None
          }
        }

        val partDetailUpdate  = {
          if (updateRequest.partDetail.isDefined){
            if (updateRequest.partDetail.get.detailDescription.isDefined){
              Some(PartDetail(
                id = None,
                partId =  Some(id),
                description = updateRequest.partDetail.get.detailDescription.get
              ))
            } else {
              None
            }
          } else {
            None
          }
        }

        Future {
          partService.update(partUpdate, partDetailUpdate)
          Ok(Json.obj("status" -> "Updated successfully"))
        }

      }

    )
  }


  def delete(id: Long): Action[AnyContent] = Action.async {
    Future {
      partService.delete(id)
      Ok(Json.obj("status" -> "Deleted successfully"))
    }
  }


  private def processPaginationParams(request: Request[AnyContent])(handler: (Int, Int) => Future[Result]): Future[Result] = {
    val offsetOpt = request.getQueryString("offset").flatMap(s => scala.util.Try(s.toInt).toOption)
    val limitOpt = request.getQueryString("limit").flatMap(s => scala.util.Try(s.toInt).toOption)
    
    (offsetOpt, limitOpt) match {
      case (Some(offset), Some(limit)) if offset >= 0 && limit >= 0 =>
        handler(offset, limit)
      case _ =>
        Future.successful(BadRequest(Json.obj("error" -> "Invalid or missing query parameters")))
    }
  }
}
