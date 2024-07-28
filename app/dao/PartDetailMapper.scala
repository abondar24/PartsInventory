package dao

import model.PartDetail
import org.apache.ibatis.annotations.*

@Mapper
trait PartDetailMapper {

  @Insert(Array("<script>"+
    "INSERT INTO PART_DETAIL (PART_ID, DESCRIPTION) VALUES"+
    " <foreach collection='partDetails' item='detail' separator=','>"+
    " (#{detail.partId}, #{detail.description})"+
    " </foreach>"+
    "</script>"))
  @Options(useGeneratedKeys = true, keyProperty = "id")
  def insertDetails(partDetails: Seq[PartDetail]): Unit
  
  @Update(Array("UPDATE PART_DETAIL SET DESCRIPTION = #{description} WHERE ID = #{id}"))
  def updateDetailDescription(@Param("description") description: String): Unit

  @Select(Array("SELECT DESCRIPTION FROM PART_DETAILS WHERE PART_ID=#{partId} OFFSET #{offset}, LIMIT #{limit}"))
  def selectPartWithDetails(@Param("partId") partId: Long, @Param("offset") offset: Int, @Param("limit") limit: Int): List[PartDetail]
  
}
