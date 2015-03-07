package com.fustigatedcat.metricize.api

import com.fustigatedcat.metricize.api.model.Customers

import scala.slick.driver.MySQLDriver.simple._

object CoreDB {

  val db = Database.forURL("jdbc:mysql://localhost:3306/metricize", user = "root", password = "", driver="com.mysql.jdbc.Driver")

  val customers = TableQuery[Customers]

}