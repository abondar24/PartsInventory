package model


case class PartDetail(
                       id: Option[Long] = None,
                       partId: Option[Long] = None,
                       description: String = ""
                     ) {
  def withId(id: Option[Long]): PartDetail = copy(id = id)
  def withPartId(partId: Option[Long]): PartDetail = copy(partId = partId)
  def withDescription(description: String): PartDetail = copy(description = description)
}

object PartDetail {
  def builder: PartDetailBuilder = new PartDetailBuilder()

  class PartDetailBuilder {
    private var id: Option[Long] = None
    private var partId: Option[Long] = None
    private var description: String = ""

    def withId(id: Option[Long]): PartDetailBuilder = {
      this.id = id
      this
    }

    def withPartId(partId: Option[Long]): PartDetailBuilder = {
      this.partId = partId
      this
    }

    def withDescription(description: String): PartDetailBuilder = {
      this.description = description
      this
    }

    def build(): PartDetail = PartDetail(id, partId, description)
  }
}
