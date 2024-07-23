package model

import play.api.libs.json.{Json, OFormat}

case class PartWithDetails (
                             name: String,
                             quantity: Int,
                             price: BigDecimal,
                             details: Seq[String]
                           )

object PartWithDetails {
  implicit val partWithDetailsFormat: OFormat[PartWithDetails] = Json.format[PartWithDetails]
}