package dao

import model.PartDetail
import org.apache.ibatis.annotations.*

@Mapper
trait PartDetailMapper {

  @Insert(Array("INSERT INTO PART_DETAIL (PART_ID, DESCRIPTION) VALUES (#{partId}, #{description})"))
  @Options(useGeneratedKeys = true, keyProperty = "id")
  def insertDetail(partDetail: PartDetail): Unit

  @Select(Array("SELECT * FROM PART_DETAIL WHERE PART_ID = #{partId} OFFSET #{offset} LIMIT #{limit}"))
  def findDetail(@Param("partId") partId: Long, @Param("offset") offset: Int,
                 @Param("limit") limit: Int): List[PartDetail]

  @Update(Array("UPDATE PART_DETAIL SET DESCRIPTION = #{description} WHERE ID = #{id}"))
  def updateDetail(@Param("description") description: String): Unit

}
