package model.web

import play.api.libs.json.{Json, OFormat}

case class PartCreateResponse(
                             partId: Long,
                             partDetails: Seq[Long]
                             )

object PartCreateResponse {
  implicit val partFormat: OFormat[PartCreateResponse] = Json.format[PartCreateResponse]
}