package model

import play.api.libs.json.{Json, OFormat}

case class PartDetail(
                       id: Option[Long] = None,
                       partId: Long,
                       description: String,
                     )

object PartDetail {
  implicit val partDetailFormat: OFormat[PartDetail] = Json.format[PartDetail]
}
