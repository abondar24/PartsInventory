package model.web

import play.api.libs.json.{Json, OFormat}

case class PartResponse(
                         name: String,
                         quantity: Int,
                         price: BigDecimal
                       )
object PartResponse {
  implicit val partResponseFormat: OFormat[PartResponse] = Json.format[PartResponse]
}