package client
import scala.io.Source
import machine._
import automaticTester.TestAvatar
import database._

object Client extends App {
  TestAvatar.check(MachineImpl)
  val server = MachineImpl
  val ask: String = readLine()

  //Appel à la fonction ask de MachineImpl:
  val answers: List[String] = server.ask(ask)
  for (lign <- answers) {
    println("\n" + lign)
  }
}
