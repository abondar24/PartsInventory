package dao

import model.Part
import org.apache.ibatis.annotations.{Delete, Insert, Mapper, Options, Param, Select, Update}

@Mapper
trait PartMapper {

  @Insert(Array("INSERT INTO PART (NAME, QUANTITY, PRICE) VALUES (#{name}, #{quantity}, #{price})"))
  @Options(useGeneratedKeys = true, keyProperty = "id")
  def insertPart(part: Part): Unit

  @Select(Array("SELECT * FROM PART WHERE ID = #{id}"))
  def selectPart(@Param("id") id: Long): Part

  @Select(Array("SELECT * FROM PART WHERE NAME = #{name}"))
  def selectPart(@Param("name") name: String): Part

  @Update(Array("UPDATE PART SET NAME = #{name}, QUANTITY = #{quantity}, PRICE = #{price} WHERE ID = #{id}"))
  def updatePart(part: Part): Unit

  @Delete(Array("DELETE FROM PART WHERE ID = #{id}"))
  def deletePart(@Param("id") id: Long): Unit

  @Select(Array("SELECT * FROM PART OFFSET #{offset} LIMIT #{limit}"))
  def listParts(@Param("offset" )offset: Int, @Param("limit") limit: Int): List[Part]
}
