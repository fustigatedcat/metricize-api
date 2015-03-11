package com.fustigatedcat.metricize.api

import akka.actor.{Props, ActorSystem}
import com.fustigatedcat.metricize.api.processor.InputStatisticProcessor
import com.fustigatedcat.metricize.api.queue.core.{InvalidEdgeQueue, EdgeQueue}

object ActorSystems {

  val webActorSystem = ActorSystem("webActorSystem")

  val generalActorSystem = ActorSystem("generalActorSystem")

  val edgeQueue = generalActorSystem.actorOf(Props[EdgeQueue])

  val invalidEdgeQueue = generalActorSystem.actorOf(Props[InvalidEdgeQueue])

  val inputStatisticProcessor = generalActorSystem.actorOf(Props[InputStatisticProcessor])

}
