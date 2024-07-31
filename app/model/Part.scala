package model

import play.api.libs.json.{Json, OFormat}

case class Part(
                 id: Option[Long] = None,
                 name: String = "",
                 quantity: Int = 0,
                 price: BigDecimal = BigDecimal(0)
               ) {
  def withId(id: Option[Long]): Part = copy(id = id)
  def withName(name: String): Part = copy(name = name)
  def withQuantity(quantity: Int): Part = copy(quantity = quantity)
  def withPrice(price: BigDecimal): Part = copy(price = price)
}

object Part {
  def builder: PartBuilder = new PartBuilder()

  class PartBuilder {
    private var id: Option[Long] = None
    private var name: String = ""
    private var quantity: Int = 0
    private var price: BigDecimal = BigDecimal(0)

    def withId(id: Option[Long]): PartBuilder = {
      this.id = id
      this
    }

    def withName(name: String): PartBuilder = {
      this.name = name
      this
    }

    def withQuantity(quantity: Int): PartBuilder = {
      this.quantity = quantity
      this
    }

    def withPrice(price: BigDecimal): PartBuilder = {
      this.price = price
      this
    }

    def build(): Part = Part(id, name, quantity, price)
  }
}
