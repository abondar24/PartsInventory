package dao

import model.Part

trait PartDao {

  def create(part: Part): Unit

  def findById(id: Long): Option[Part]
  
  def findByName(name: String): Option[Part]

  def findAll(offset: Int, limit: Int): Seq[Part]

  def update(part: Part): Unit

  def delete(id: Long): Unit
  
}
