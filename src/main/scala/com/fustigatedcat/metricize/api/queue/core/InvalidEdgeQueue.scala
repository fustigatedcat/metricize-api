package com.fustigatedcat.metricize.api.queue.core

import akka.actor.Actor
import akka.camel.{Oneway, Producer}
import com.fustigatedcat.metricize.api.Configuration

class InvalidEdgeQueue extends Actor with Producer with Oneway {

  val parameters = Map(
    "queue" -> "metricize-edge",
    "vhost" -> Configuration.queue.core.virtualhost,
    "username" -> Configuration.queue.core.username,
    "password" -> Configuration.queue.core.password,
    "routingKey" -> "invalid-edge",
    "declare" -> "false"
  ).map(x => x._1 + "=" + x._2).mkString("&")

  def endpointUri = "rabbitmq://" + Configuration.queue.core.addresses(0)._1 + ":" + Configuration.queue.core.addresses(0)._2 +
    "/metricize?" + parameters

}
