package com.fustigatedcat.metricize.api.database.core

import com.fustigatedcat.metricize.api.model.{Customer, MYSQLAgentConfigs, Agents, Agent}
import org.apache.commons.lang3.RandomStringUtils
import org.json4s._
import org.json4s.JsonDSL._

import scala.slick.driver.MySQLDriver.simple._

object AgentDAO {

  val agents = TableQuery[Agents]

  val mysqlagentconfigs = TableQuery[MYSQLAgentConfigs]

  def getAgentByKey(key : String) : Option[Agent] = DB.db.withSession { implicit session =>
    agents.filter(_.agentKey === key.value).firstOption
  }

  implicit def agentToJValue(agent : Agent) : JValue = DB.db.withSession { implicit session =>
    mysqlagentconfigs.filter(_.agentId === agent.id).firstOption match {
      case Some(mysqlConfig) => {
        ("id" -> agent.id) ~
          ("customerId" -> agent.customerId) ~
          ("name" -> agent.name) ~
          ("agentKey" -> agent.agentKey) ~
          ("agentType" -> agent.agentType) ~
          ("config" ->
            ("mysqlAgentConfigId" -> mysqlConfig.mysqlAgentConfigId) ~
              ("fqdn" -> mysqlConfig.fqdn) ~
              ("port" -> mysqlConfig.port) ~
              ("username" -> mysqlConfig.username) ~
              ("password" -> mysqlConfig.password)
            )
      }
    }
  }

  def createAgent(customer : Customer, name : String) = DB.db.withSession { implicit session =>
    val a = Agent(None, Some(customer.id.get), name, Some(RandomStringUtils.randomAlphabetic(512)), Some("NONE"))
    val agentId = agents.returning(agents.map(_.id)) += a
    Agent(Some(agentId), a.customerId, a.name, a.agentKey, a.agentType)
  }

}
