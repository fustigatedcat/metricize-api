package com.fustigatedcat.metricize.api.model

import slick.driver.MySQLDriver.simple._

case class Agent(id : Option[Long], customerId : Option[Long], name : String, agentKey : Option[String], agentType : Option[String])

class Agents(tag : Tag) extends Table[Agent](tag, "Agent") {
  def id = column[Long]("agent_id", O.PrimaryKey, O.AutoInc)
  def customerId = column[Long]("customer_id")
  def name = column[String]("name")
  def agentKey = column[String]("agent_key")
  def agentType = column[String]("agent_type")

  def * = (id.?, customerId.?, name, agentKey.?, agentType.?) <> (Agent.tupled, Agent.unapply)
}