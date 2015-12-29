package com.goticks

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{MustMatchers, WordSpecLike}

class SilentActorSpec extends TestKit(ActorSystem("testSystem"))
    with WordSpecLike
    with MustMatchers
    with StopSystemAfterAll {

  "A silent Actor" must {
    "change state when it receives a message, signel threaded" in {
      import SilentActor._

      val silentActor = TestActorRef[SilentActor]
      silentActor ! SilentMessage("whisper")

      silentActor.underlyingActor.state must contain("whisper")
    }

    "change state when it receives a message, multi-threaded" in {
      import SilentActor._

      val silentActor = system.actorOf(Props[SilentActor], "silentActor")
      silentActor ! SilentMessage("whisper1")
      silentActor ! SilentMessage("whisper2")

      silentActor ! GetState(testActor)

      expectMsg(Vector("whisper1", "whisper2"))
    }
  }

}
