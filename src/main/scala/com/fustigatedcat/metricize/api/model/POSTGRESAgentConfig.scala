package com.fustigatedcat.metricize.api.model

import slick.driver.MySQLDriver.simple._

case class POSTGRESAgentConfig(
                             postgresAgentConfigId : Option[Long],
                             agentId : Long,
                             username : String,
                             password : String,
                             fqdn : String,
                             port : Int,
                             queryString : String,
                             countPer : Int,
                             timeUnit : String,
                             dbName : String)

class POSTGRESAgentConfigs(tag : Tag) extends Table[POSTGRESAgentConfig](tag, "POSTGRESAgentConfig") {
  def postgresAgentConfigId = column[Long]("postgres_agent_config_id", O.PrimaryKey, O.AutoInc)
  def agentId = column[Long]("agent_id")
  def username = column[String]("username")
  def password = column[String]("password")
  def fqdn = column[String]("fqdn")
  def port = column[Int]("port")
  def queryString = column[String]("query_string")
  def countPer = column[Int]("count_per")
  def timeUnit = column[String]("time_unit")
  def dbName = column[String]("db_name")

  def * = (postgresAgentConfigId.?, agentId, username, password, fqdn, port, queryString, countPer, timeUnit, dbName) <> (POSTGRESAgentConfig.tupled, POSTGRESAgentConfig.unapply)
}

