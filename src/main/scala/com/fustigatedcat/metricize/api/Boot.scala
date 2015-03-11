package com.fustigatedcat.metricize.api

import akka.actor.{Props, ActorSystem}
import spray.servlet.WebBoot

// this class is instantiated by the servlet initializer
// it needs to have a default constructor and implement
// the spray.servlet.WebBoot trait
class Boot extends WebBoot {

  val system = ActorSystems.webActorSystem

  // the service actor replies to incoming HttpRequests
  val serviceActor = system.actorOf(Props[MyServiceActor])
}