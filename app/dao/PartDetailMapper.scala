package dao

import model.PartDetail
import org.apache.ibatis.annotations.*

@Mapper
trait PartDetailMapper {

  @Insert(Array("INSERT INTO PART_DETAIL (PART_ID, DESCRIPTION) VALUES (#{partId}, #{description})"))
  @Options(useGeneratedKeys = true, keyProperty = "id")
  def insertDetail(partDetail: PartDetail): Unit


  @Select(Array("SELECT * FROM PART_DETAIL WHERE ID = #{id}"))
  def findDetail(@Param("id") id: Long): PartDetail

  @Update(Array("UPDATE PART_DETAIL SET DESCRIPTION = #{description} WHERE ID = #{id}"))
  def updateDetail(@Param("description") description:String): Unit

}
