package service

import model.{Part, PartDetail}

trait PartService {

  def create(part: Part,partDetail: Seq[PartDetail]): Unit

  def findById(id: Long): Part

  def findByName(name: String): Part

  def findAll(offset: Int, limit: Int): Seq[Part]

  def findPartDetails(id: Long, offset: Int, limit: Int): Seq[PartDetail]
  
  def update(part: Option[Part],partDetail: Option[PartDetail]): Unit
  
  def delete(id: Long): Unit
}
