package com.fustigatedcat.metricize.api

import akka.actor.Props
import akka.io.IO
import akka.util.Timeout
import spray.can.Http
import akka.pattern.ask
import scala.concurrent.duration._

// this class is instantiated by the servlet initializer
// it needs to have a default constructor and implement
// the spray.servlet.WebBoot trait
object Boot extends App {

  implicit val system = ActorSystems.webActorSystem

  // the service actor replies to incoming HttpRequests
  val serviceActor = system.actorOf(Props[MyServiceActor])

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(serviceActor, interface = "localhost", port = 8080)
}