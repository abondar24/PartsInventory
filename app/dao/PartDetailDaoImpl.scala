package dao

import com.google.inject.Inject
import model.PartDetail
import org.apache.ibatis.session.SqlSessionFactory

class PartDetailDaoImpl @Inject()(sqlSessionFactory: SqlSessionFactory) extends PartDetailDao {

  private val sqlSession = sqlSessionFactory.openSession()
  private val mapper = sqlSession.getMapper(classOf[PartDetailMapper])

  override def create(partDetail: PartDetail): Unit = {
    mapper.insertDetail(partDetail)
    sqlSession.commit()
  }

  override def update(description: String): Unit = {
    mapper.updateDetail(description)
    sqlSession.commit()
  }

  override def find(partId: Long, offset: Int, limit: Int): Seq[PartDetail] = {
    mapper.findDetail(partId,offset,limit)
  }


}
