package service

import com.google.inject.{Inject, Singleton}
import dao.{PartDetailMapper, PartMapper}
import exception.{PartDetailNotFoundException, PartNotFoundException}
import model.{Part, PartDetail}
import org.apache.ibatis.session.{SqlSession, SqlSessionFactory}
import org.slf4j.LoggerFactory

@Singleton
class PartServiceImpl @Inject()(sqlSessionFactory: SqlSessionFactory, partMapper: PartMapper,
                                partDetailMapper: PartDetailMapper) extends PartService {

  private val logger = LoggerFactory.getLogger(classOf[PartServiceImpl])

  private def withSession[T](block: (SqlSession) => T): T = {
    val session = sqlSessionFactory.openSession()
    try {
      block(session)
    } finally {
      session.close()
    }
  }


  override def create(part: Part, partDetail: Seq[PartDetail]): Unit = withSession { session =>
    partMapper.insertPart(part)

    partDetail.map {
      pd => PartDetail.builder.withPartId(pd.id).build()
    }

    partDetailMapper.insertDetails(partDetail)

    session.commit()

    logger.info(s"Saved part $part with detail $partDetail")
  }


  override def update(part: Option[Part], partDetail: Option[PartDetail]): Unit = withSession { session =>


    part match {
      case Some(p) =>
        findById(p.id.get)

        partMapper.updatePart(p)
        logger.info(s"Updated part ${p}")

      case None =>
        logger.info("No Part to Update")
    }

    partDetail match
      case Some(pd) =>
        val detail = partDetailMapper.selectPartDetailById(pd.id.get)
        if (detail == null) {
          throw new PartDetailNotFoundException()
        }
        partDetailMapper.updateDetailDescription(partDetail.get.description)
        logger.info(s"Updated detail $pd")

      case None =>
        logger.info("No Part Detail to Update")

    session.commit()
  }

  override def findById(id: Long): Part = {
    findPart(partMapper.selectPartById, id, s"No part found with ID $id")
  }

  override def findByName(name: String): Part = {
    findPart(partMapper.selectPartByName, name, s"No part found with name $name")
  }

  override def findAll(offset: Int, limit: Int): Seq[Part] = {
    partMapper.selectParts(offset, limit)
  }

  override def findPartDetails(id: Long, offset: Int, limit: Int): Seq[PartDetail] = {
    partDetailMapper.selectPartWithDetails(id, offset, limit)
  }

  override def delete(id: Long): Unit = {
    findById(id)
    partMapper.deletePart(id)
  }


  private def findPart[T](criteria: T => Part, criterion: T, errorMessage: String): Part = withSession { * =>
    val part = criteria(criterion)
    if (part == null) {
      throw new PartNotFoundException(errorMessage)
    }
    logger.info(s"Found part: $part")
    part
  }

}
