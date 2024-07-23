package dao

import model.PartDetail

trait PartDetailDao {

  def create(partDetail: PartDetail): Unit
  
  def update(description: String): Unit
  
  def find(partId: Long, offset: Int, limit: Int): Seq[PartDetail]
  
}
