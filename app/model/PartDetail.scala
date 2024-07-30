package model

import play.api.libs.json.{Json, OFormat}

case class PartDetail(
                       id: Option[Long] = None,
                       partId: Option[Long] = None,
                       description: String,
                     )

object PartDetail {
  
  def withPartId(partDetail: PartDetail, newPartId: Option[Long]): PartDetail = {
    partDetail.copy(partId = newPartId)
  }
}
