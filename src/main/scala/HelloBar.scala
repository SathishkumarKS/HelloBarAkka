import akka.actor.{ActorSystem, Props}

case class Ticket(quantity: Int)
case class Pint()
case class EmptyPint()
case class CloseBar()
case class OpenBar(capacity: Int)

object HelloBar extends App {
  val system = ActorSystem("hello-bar")

  val barOwner   = system.actorOf(Props(new Drunkard), "barOwner")
  val barTender = system.actorOf(Props(new BarTender), "barTender")
  barTender.tell(OpenBar(10), barOwner)

  val sathish   = system.actorOf(Props(new Drunkard), "sathish")
  barTender.tell(Ticket(1), sathish)

  val partha     = system.actorOf(Props(new Drunkard), "partha")
  barTender.tell(Ticket(9), partha)

  val anil = system.actorOf(Props(new Drunkard), "anil")
  barTender.tell(Ticket(1), anil)

}
