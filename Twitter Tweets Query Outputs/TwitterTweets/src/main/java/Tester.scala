import scala.io.Source


object Tester {
  def main(args: Array[String]) {
    println(scala.io.Source.fromURL("http://google.com").mkString)

  }
}