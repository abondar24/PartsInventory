package dao

import com.google.inject.Inject
import model.{Part, PartWithDetails}
import org.apache.ibatis.session.SqlSessionFactory


class PartDaoImpl @Inject()(sqlSessionFactory: SqlSessionFactory) extends PartDao {
  private val sqlSession = sqlSessionFactory.openSession()
  private val mapper = sqlSession.getMapper(classOf[PartMapper])

  override def create(part: Part): Unit = {
    mapper.insertPart(part)
    sqlSession.commit()
  }

  override def findById(id: Long): Option[Part] = {
    Option(mapper.selectPart(id))
  }

  override def findByName(name: String): Option[Part] = {
    Option(mapper.selectPart(name))
  }

  override def update(part: Part): Unit = {
    mapper.updatePart(part)
    sqlSession.commit()
  }

  override def delete(id: Long): Unit = {
    mapper.deletePart(id)
    sqlSession.commit()
  }

  override def findAll(offset: Int, limit: Int): Seq[Part] = {
    mapper.listParts(offset, limit)
  }

  override def findWithDetails(id: Long): Option[PartWithDetails] = {
    Option(mapper.selectPartWithDetails(id))
  }

}
