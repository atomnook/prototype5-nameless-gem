package board.message

import akka.actor.ActorRef

sealed trait BoardMessage

sealed trait EscalatedBoardMessage

object BoardMessage {
  case object InitializeBoard extends BoardMessage

  case class LinesReady(ref: ActorRef) extends BoardMessage

  case object BoardReady extends BoardMessage with EscalatedBoardMessage

  case object FallIntoErrorState extends BoardMessage with EscalatedBoardMessage
}
