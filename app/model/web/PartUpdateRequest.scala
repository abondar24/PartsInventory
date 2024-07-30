package model.web

import model.Part
import play.api.libs.json.{Json, OFormat}

case class PartUpdateRequest(
                              name: Option[String],
                              quantity: Option[Int],
                              price: Option[BigDecimal],
                              partDetail: Option[PartDetailUpdateRequest]
                            )

object PartUpdateRequest {
  implicit val partUpdateFormat: OFormat[PartUpdateRequest] = Json.format[PartUpdateRequest]
}