package model.web

import play.api.libs.json.{Json, OFormat}

case class PartDetailResponse (
                                description: String,
                              )

object PartDetailResponse {
  implicit val partDetailFormat: OFormat[PartDetailResponse] = Json.format[PartDetailResponse]
}