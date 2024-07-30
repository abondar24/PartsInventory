package exception

class PartDetailNotFoundException(message: String)  extends RuntimeException(message){

  def this() =this("Part detail not found")

}
