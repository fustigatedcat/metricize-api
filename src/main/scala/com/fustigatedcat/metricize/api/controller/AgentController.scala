package com.fustigatedcat.metricize.api.controller

import com.fustigatedcat.metricize.api.CoreDB
import com.fustigatedcat.metricize.api.model.{Agent, Customer}

import spray.http.MediaTypes._
import spray.routing.HttpService

import spray.httpx.marshalling._
import com.fustigatedcat.metricize.api.model.JsonProtocol._
import spray.httpx.SprayJsonSupport._

import scala.slick.driver.MySQLDriver.simple._

trait AgentController extends HttpService { this: Auth =>

  def createAgent(customer : Customer, agent : Agent) = respondWithMediaType(`application/json`) {
    complete {
      CoreDB.db.withSession { implicit session =>
        val a = Agent(None, Some(customer.id.get), agent.name, agent.agentKey)
        val agentId = CoreDB.agents.returning(CoreDB.agents.map(_.id)) += a
        marshal(Agent(Some(agentId), a.customerId, a.name, a.agentKey))
      }
    }
  }

  def getAgent(agent : Agent) = respondWithMediaType(`application/json`) {
    complete {
      marshal(agent)
    }
  }

  val agentRoutes = pathPrefix("api") {
    pathPrefix("agents") {
      pathEnd {
        authenticate(validateCustomer) { customer =>
          post {
            entity(as[Agent]) { agent =>
              createAgent(customer, agent)
            }
          }
        }
      }
    } ~
    pathPrefix("agents" / "me") {
      pathEnd {
        authenticate(validateAgent) { agent =>
          get {
            getAgent(agent)
          }
        }
      }
    }
  }

}
