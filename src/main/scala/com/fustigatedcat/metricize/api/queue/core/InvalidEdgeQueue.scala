package com.fustigatedcat.metricize.api.queue.core

import akka.actor.Actor
import akka.camel.{Oneway, Producer}
import com.fustigatedcat.metricize.api.Configuration

class InvalidEdgeQueue extends Actor with Producer with Oneway {

  def endpointUri = Configuration.queue.invalidedge.url

}
