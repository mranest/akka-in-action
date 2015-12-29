package com.goticks

import akka.actor.{Actor, ActorRef}

object SilentActor {
  case class SilentMessage(data: String)
  case class GetState(receiver: ActorRef)
}

class SilentActor extends Actor {
  import SilentActor._
  var internalState = Vector[String]()

  override def receive: Receive = {
    case SilentMessage(data) =>
      internalState = internalState :+ data
    case GetState(receiver) =>
      receiver ! state
  }

  def state = internalState
}
