package model.web

import play.api.libs.json.{Json, OFormat}

case class PartDetailUpdateRequest(
                                    detailId: Option[Long],
                                    detailDescription: Option[String]
                                  )
object PartDetailUpdateRequest {
  implicit val detailUpdateFormat: OFormat[PartDetailUpdateRequest] = Json.format[PartDetailUpdateRequest]
}