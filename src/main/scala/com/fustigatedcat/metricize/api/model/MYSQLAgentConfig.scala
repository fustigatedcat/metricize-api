package com.fustigatedcat.metricize.api.model

import slick.driver.MySQLDriver.simple._

case class MYSQLAgentConfig(mysqlAgentConfigId : Option[Long], agentId : Long, username : String, password : String, fqdn : String, port : Int, queryString : String)

class MYSQLAgentConfigs(tag : Tag) extends Table[MYSQLAgentConfig](tag, "MYSQLAgentConfig") {
  def mysqlAgentConfigId = column[Long]("mysql_agent_config_id", O.PrimaryKey, O.AutoInc)
  def agentId = column[Long]("agent_id")
  def username = column[String]("username")
  def password = column[String]("password")
  def fqdn = column[String]("fqdn")
  def port = column[Int]("port")
  def queryString = column[String]("query_string")

  def * = (mysqlAgentConfigId.?, agentId, username, password, fqdn, port, queryString) <> (MYSQLAgentConfig.tupled, MYSQLAgentConfig.unapply)
}
