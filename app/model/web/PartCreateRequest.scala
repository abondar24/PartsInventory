package model.web

import model.Part
import play.api.libs.json.{Json, OFormat}

case class PartCreateRequest(
                              name: String,
                              quantity: Int,
                              price: BigDecimal,
                              details: Seq[PartDetailCreateRequest]
                            )

object PartCreateRequest {
  implicit val partFormat: OFormat[PartCreateRequest] = Json.format[PartCreateRequest]
}