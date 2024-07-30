package model.web

import play.api.libs.json.{Json, OFormat}

case class PartDetailCreateRequest(
                                  description: String
                                  )

object PartDetailCreateRequest {
  implicit val detailFormat: OFormat[PartDetailCreateRequest] = Json.format[PartDetailCreateRequest]
}