package com.fustigatedcat.metricize.api

import akka.actor.Actor
import com.fustigatedcat.metricize.api.controller._
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport
import spray.routing.HttpService

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with HttpService with Auth with Json4sSupport
  with CustomerController
  with AgentController
  with StatisticController {

  override implicit val json4sFormats : Formats = DefaultFormats

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(pathPrefix("api") { customerRoutes ~ agentRoutes ~ statisticRoutes })

}