import akka.actor.{ActorLogging, Actor}

class Drunkard extends Actor with ActorLogging {
  def receive = {
    case Pint() =>
      log.info(s"I'll make short work of pint")

      Thread.sleep(2000)

      log.info(s"Done, here is the empty glass for pint")

      sender ! EmptyPint()

    case s: String =>
      log.info(s)
  }
}
