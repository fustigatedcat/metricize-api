package com.fustigatedcat.metricize.api

import com.fustigatedcat.metricize.api.model.{MYSQLAgentConfigs, Agent, Agents, Customers}
import org.json4s._
import org.json4s.JsonDSL._

import scala.slick.driver.MySQLDriver.simple._

object CoreDB {

  val db = Database.forURL("jdbc:mysql://localhost:3306/metricize", user = "root", password = "", driver="com.mysql.jdbc.Driver")

  val customers = TableQuery[Customers]

  val agents = TableQuery[Agents]

  val mysqlagentconfigs = TableQuery[MYSQLAgentConfigs]

  def getAgent(agent : Agent) : JValue = db.withSession { implicit session =>
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

}