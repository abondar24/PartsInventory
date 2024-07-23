package dao

import model.PartDetail
import org.apache.ibatis.session.{SqlSession, SqlSessionFactory}
import org.mockito.Mockito.{verify, when}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

class PartDetailDaoTest extends PlaySpec with MockitoSugar{

  "PartDetailDaoImpl#create" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartDetailMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartDetailMapper])).thenReturn(mapper)

    val partDetailDao = new PartDetailDaoImpl(sessionFactory)

    val partDetail = PartDetail(None,1,"PartName")
    partDetailDao.create(partDetail)

    verify(mapper).insertDetail(partDetail)
    verify(sqlSession).commit()
  }


  "PartDetailDaoImpl#update" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartDetailMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartDetailMapper])).thenReturn(mapper)

    val partDetailDao = new PartDetailDaoImpl(sessionFactory)

    partDetailDao.update("test")

    verify(mapper).updateDetail("test")
    verify(sqlSession).commit()
  }


  "PartDetailDaoImpl#find" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartDetailMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartDetailMapper])).thenReturn(mapper)

    val partDetailDao = new PartDetailDaoImpl(sessionFactory)

    val partDetail = PartDetail(Option(1), 1, "PartName")
    when(mapper.findDetail(partDetail.id.get,0,1)).thenReturn(List(partDetail))

    partDetailDao.find(partDetail.id.get,0,1)

    verify(mapper).findDetail(partDetail.id.get,0,1)
  }

}
