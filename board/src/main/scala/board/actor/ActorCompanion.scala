package board.actor

import akka.actor.{Actor, Props}

import scala.reflect.ClassTag

/**
  * @see [[http://doc.akka.io/docs/akka/2.4/scala/actors.html#Recommended_Practices]]
  */
abstract class ActorCompanion[A <: Actor : ClassTag, B] {
  def apply(setting: B): A

  def props(setting: B): Props = Props(apply(setting))
}
