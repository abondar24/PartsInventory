package dao

import exception.PartNotFoundException
import model.{Part, PartDetail}
import org.apache.ibatis.session.{SqlSession, SqlSessionFactory}
import org.mockito.Mockito.{reset, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import service.PartServiceImpl

class PartServiceTest extends PlaySpec with MockitoSugar with BeforeAndAfterEach{


  private val sessionFactory = mock[SqlSessionFactory]
  private val sqlSession = mock[SqlSession]
  private val partMapper = mock[PartMapper]
  private val detailMapper = mock[PartDetailMapper]
  private val partService = PartServiceImpl(sessionFactory, partMapper, detailMapper)


   override def beforeEach(): Unit = {
    super.beforeEach()
    reset(sessionFactory, sqlSession, partMapper, detailMapper)
    when(sessionFactory.openSession()).thenReturn(sqlSession)
  }

  "PartService" should {

    "create part" in {
      val part = Part(Some(1), "PartName", 10, 100.0)
      val partDetail = PartDetail(None, None, "Test")
      val details = List(partDetail)
      partService.create(part, details)

      verify(partMapper).insertPart(part)
      verify(detailMapper).insertDetails(details)
      verify(sqlSession).commit()
    }

    "find part by id" in {
      val part = Part(None, "PartName", 10, 100.0)

      when(partMapper.selectPartById(1L)).thenReturn(part)

      val res = partService.findById(1L)

      res shouldEqual part
    }

    "throw an exception if part not found" in {
      when(partMapper.selectPartById(1L)).thenReturn(null)
      val exception = intercept[PartNotFoundException] {
        partService.findById(1L)
      }

      exception.getMessage shouldEqual "No part found with ID 1"
    }

    "find part by name" in {
      val part = Part(None, "PartName", 10, 100.0)

      when(partMapper.selectPartByName(part.name)).thenReturn(part)

      val res = partService.findByName(part.name)

      res shouldEqual part
    }


    "find parts" in {
      val part = Part(None, "PartName", 10, 100.0)

      when(partMapper.selectParts(0,1)).thenReturn(List(part))

      val res = partService.findAll(0,1)

      res.length shouldEqual 1
    }

    "find details by part id" in {
      val partDetail = PartDetail(None,Some(1), "Test")

      when(detailMapper.selectPartWithDetails(1L,0,1)).thenReturn(List(partDetail))

      val res = partService.findPartDetails(1L,0,1)

      res.length shouldEqual 1
    }

    "update part with detail" in {
      val part = Part(Some(1), "PartName", 10, 100.0)
      val partDetail = PartDetail(None, Some(1), "Test")

      when(partMapper.selectPartById(part.id.get)).thenReturn(part)

      partService.update(part, partDetail)

      verify(partMapper).updatePart(part)
      verify(detailMapper).updateDetailDescription(partDetail.description)
      verify(sqlSession).commit()
    }

    "update part without detail" in {
      val part = Part(Some(1), "PartName", 10, 100.0)

      when(partMapper.selectPartById(part.id.get)).thenReturn(part)

      partService.update(part, null)

      verify(partMapper).updatePart(part)
      verify(sqlSession).commit()
    }

    "update  detail without part" in {
      val part = Part(Some(1), "PartName", 10, 100.0)
      val partDetail = PartDetail(None, Some(1), "Test")

      when(partMapper.selectPartById(partDetail.partId.get)).thenReturn(part)

      partService.update(null, partDetail)

      verify(detailMapper).updateDetailDescription(partDetail.description)
    }

    "delete detail" in {
      val part = Part(Some(1), "PartName", 10, 100.0)

      when(partMapper.selectPartById(part.id.get)).thenReturn(part)

      partService.delete(part.id.get)

      verify(partMapper).deletePart(part.id.get)
    }


  }


}
