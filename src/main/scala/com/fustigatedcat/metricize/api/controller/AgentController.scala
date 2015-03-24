package com.fustigatedcat.metricize.api.controller

import com.fustigatedcat.metricize.api.database.core.AgentDAO, AgentDAO._
import org.json4s.{JObject, JValue}

import spray.http.MediaTypes._
import spray.httpx.Json4sSupport
import spray.routing.HttpService

trait AgentController extends HttpService { this: Auth with Json4sSupport =>

  val agentRoutes = pathPrefix("agents") {
    pathEnd {
      authenticate(validateCustomer) { customer =>
        post {
          entity(as[JObject]) { body =>
            respondWithMediaType(`application/json`) {
              complete {
                AgentDAO.createAgent(customer, (body \ "name").extract[String])
              }
            }
          }
        }
      }
    }
  } ~
  pathPrefix("agents" / "me") {
    pathEnd {
      authenticate(validateAgent) { agent =>
        get {
          respondWithMediaType(`application/json`) {
            complete {
              agentToJValue(agent)
            }
          }
        }
      }
    }
  }
}
