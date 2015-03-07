package com.fustigatedcat.metricize.api

import akka.actor.Actor
import com.fustigatedcat.metricize.api.controller.{AgentController, Auth, CustomerController}
import spray.routing.HttpService

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with HttpService with Auth
  with CustomerController
  with AgentController {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(customerRoutes ~ agentRoutes)

}