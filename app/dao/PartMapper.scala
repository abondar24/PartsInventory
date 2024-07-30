package dao

import model.Part
import org.apache.ibatis.annotations.*

@Mapper
trait PartMapper {

  @Insert(Array("INSERT INTO PART (NAME, QUANTITY, PRICE) VALUES (#{name}, #{quantity}, #{price})"))
  @Options(useGeneratedKeys = true, keyProperty = "id")
  def insertPart(part: Part): Unit

  @Select(Array("SELECT * FROM PART WHERE ID = #{id}"))
  def selectPartById(@Param("id") id: Long): Part

  @Select(Array("SELECT * FROM PART WHERE NAME = #{name}"))
  def selectPartByName(@Param("name") name: String): Part

  @Update(Array("UPDATE PART SET NAME = COALESCE(#{name}, NAME), QUANTITY = COALESCE(#{quantity},QUANTITY), PRICE = COALESCE(#{price},PRICE) WHERE ID = #{id}"))
  def updatePart(part: Part): Unit

  @Delete(Array("DELETE FROM PART WHERE ID = #{id}"))
  def deletePart(@Param("id") id: Long): Unit

  @Select(Array("SELECT * FROM PART OFFSET #{offset}, LIMIT #{limit}"))
  def selectParts(@Param("offset") offset: Int, @Param("limit") limit: Int): List[Part]

  
}
