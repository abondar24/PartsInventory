package service

import com.google.inject.{Inject, Singleton}
import dao.{PartDetailMapper, PartMapper}
import exception.PartNotFoundException
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
      pd => PartDetail.withPartId(pd,Some(part.id.get))
    }
    
    partDetailMapper.insertDetails(partDetail)

    session.commit()

    logger.info(s"Saved part $part with detail $partDetail")
  }


  override def update(part: Part, partDetail: PartDetail): Unit = withSession { session =>
     
    if (part!=null){
      findById(part.id.get)

      if (part.price != null || part.name != null || part.price != null) {
        partMapper.updatePart(part)

        logger.info(s"Updated part $part")
      }

    } 
    
    if(partDetail!=null) {
      findById(partDetail.partId.get)

      if (partDetail.description != null) {
        partDetailMapper.updateDetailDescription(partDetail.description)
        logger.info(s"Updated detail $partDetail for part $part")
      }

    }
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
    partDetailMapper.selectPartWithDetails(id,offset, limit)
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
