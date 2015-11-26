import akka.actor.{ActorLogging, Actor}
import scala.collection.mutable.Map

class BarTender extends Actor with ActorLogging {
  var total = 0
  var available = 0
  var orderMap: Map[String, Int] = Map()

  def receive = {

    case OpenBar(capacity) =>
      total += capacity
      available += capacity
      log.info("It's a lovely day to be drunk. So, please come and get drunk...")

    case Ticket(quantity) =>
      if (available > 0) {
        Thread.sleep(1000)
        servePint(quantity - 1)
      } else {
        sender ! "Sorry mate, no more drinks in the bar"
        self ! CloseBar()
      }

    case EmptyPint() =>
      val remainingPints = orderMap(sender.path.toString)
      remainingPints match {

        case 0 =>
          log.info(s"${sender.path.toString} drank those pints quick, Time for you to leave the bar")
          orderMap remove sender.path.toString

        case n =>
          if (available > 0) {
            servePint(remainingPints - 1)
          } else {
            sender ! "Sorry mate, no more drinks in the bar"
            self ! CloseBar()
          }
      }

    case CloseBar() =>
      if (available > 0)
        log.info(s"In the pub city of Bangalore, people don't even drink ${total} pints")
      else
        log.info("What a perfect day!")
      context.system.terminate()
  }

  private def servePint(quantity: Int) = {
    log.info(s"Your Pint is ready, here you go [${sender.path}]")
    sender ! Pint()
    available -= 1
    orderMap put(sender.path.toString, quantity)
  }


}
