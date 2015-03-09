package com.fustigatedcat.metricize.api.controller

import com.fustigatedcat.metricize.api.CoreDB
import com.fustigatedcat.metricize.api.model.{Agent, Customer}
import org.apache.commons.lang3.RandomStringUtils
import org.json4s._
import org.json4s.JsonAST.JObject

import spray.http.MediaTypes._
import spray.httpx.Json4sSupport
import spray.routing.HttpService

import scala.slick.driver.MySQLDriver.simple._

trait AgentController extends HttpService { this: Auth with Json4sSupport =>

  def createAgent(customer : Customer, name : String) = CoreDB.db.withSession { implicit session =>
    val a = Agent(None, Some(customer.id.get), name, Some(RandomStringUtils.randomAlphabetic(512)), Some("NONE"))
    val agentId = CoreDB.agents.returning(CoreDB.agents.map(_.id)) += a
    Agent(Some(agentId), a.customerId, a.name, a.agentKey, a.agentType)
  }

  val agentRoutes = pathPrefix("api") {
    pathPrefix("agents") {
      pathEnd {
        authenticate(validateCustomer) { customer =>
          post {
            entity(as[JObject]) { body =>
              respondWithMediaType(`application/json`) {
                complete {
                  createAgent(customer, (body \ "name").extract[String])
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
                CoreDB.getAgent(agent)
              }
            }
          }
        }
      }
    }
  }
}
