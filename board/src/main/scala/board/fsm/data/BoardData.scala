package board.fsm.data

import akka.actor.ActorRef

sealed trait BoardData

object BoardData {
  case object Uninitialized extends BoardData

  case class InitializeData(friends: ActorRef, friendsAck: Boolean, enemies: ActorRef, enemiesAck: Boolean) extends BoardData
}
