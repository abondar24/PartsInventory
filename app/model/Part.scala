package model

import play.api.libs.json.{Json, OFormat}

case class Part(
               id: Option[Long] = None,
               name: String,
               quantity: Int,
               price: BigDecimal
               )

