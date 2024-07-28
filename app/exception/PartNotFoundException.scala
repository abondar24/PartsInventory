package exception

class PartNotFoundException(message: String)  extends RuntimeException(message){

  def this() = this("Part not found")

}
