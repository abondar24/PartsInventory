package dao

import model.PartDetail

trait PartDetailDao {

  def create(partDetail: PartDetail): Unit
  
  def update(description: String): Unit
  
  def find(id: Long): Option[PartDetail]
  
}
