package com.fustigatedcat.metricize.api.processor

import akka.actor.Actor
import com.fustigatedcat.metricize.api.ActorSystems
import com.fustigatedcat.metricize.api.model.Agent
import org.json4s.JObject
import org.json4s.native.JsonMethods._

class InputStatisticProcessor extends Actor {

  override def receive: Receive = {
    case (agent : Agent, input : JObject) => {
      ActorSystems.edgeQueue ! pretty(render(input))
    }
    case _ => println("Invalid statistic")
  }

}
