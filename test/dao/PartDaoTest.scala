package dao

import model.{Part, PartWithDetails}
import org.apache.ibatis.session.{SqlSession, SqlSessionFactory}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{verify, when}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

class PartDaoTest extends PlaySpec with MockitoSugar {

  "PartDaoImpl#create" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)

    val partDao = new PartDaoImpl(sessionFactory)

    val part = Part(None, "PartName", 10, 100.0)
    partDao.create(part)

    verify(mapper).insertPart(part)
    verify(sqlSession).commit()
  }

  "PartDaoImpl#update" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)

    val partDao = new PartDaoImpl(sessionFactory)

    val part = Part(None, "PartName", 10, 100.0)
    partDao.update(part)

    verify(mapper).updatePart(part)
    verify(sqlSession).commit()
  }

  "PartDaoImpl#findById" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)
    val partDao = new PartDaoImpl(sessionFactory)

    val part = Part(Option(1), "PartName", 10, 100.0)
    when(mapper.selectPart(1)).thenReturn(part)

    partDao.findById(1)

    verify(mapper).selectPart(1)
  }

  "PartDaoImpl#findByName" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)
    val partDao = new PartDaoImpl(sessionFactory)

    val part = Part(Option(1), "PartName", 10, 100.0)
    when(mapper.selectPart(part.name)).thenReturn(part)

    partDao.findByName(part.name)

    verify(mapper).selectPart(part.name)
  }


  "PartDaoImpl#delete" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)

    val partDao = new PartDaoImpl(sessionFactory)

    partDao.delete(1)

    verify(mapper).deletePart(1L)
    verify(sqlSession).commit()
  }

  "PartDaoImpl#findAll" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)
    val partDao = new PartDaoImpl(sessionFactory)

    val part = Part(Option(1), "PartName", 10, 100.0)

    when(mapper.listParts(0,1)).thenReturn(List(part))

    partDao.findAll(0,1)

    verify(mapper).listParts(0,1)
  }

  "PartDaoImpl#findWithDetails" in {
    val sessionFactory = mock[SqlSessionFactory]
    val sqlSession = mock[SqlSession]
    val mapper = mock[PartMapper]

    when(sessionFactory.openSession()).thenReturn(sqlSession)
    when(sqlSession.getMapper(classOf[PartMapper])).thenReturn(mapper)
    val partDao = new PartDaoImpl(sessionFactory)

    val part = Part(Option(1), "PartName", 10, 100.0)

    when(mapper.selectPartWithDetails(1)).thenReturn(PartWithDetails(part.name,part.quantity,part.price,List("test")))

    partDao.findWithDetails(1)

    verify(mapper).selectPartWithDetails(1)
  }
}
