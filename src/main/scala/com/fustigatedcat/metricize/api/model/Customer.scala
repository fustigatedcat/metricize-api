package com.fustigatedcat.metricize.api.model

import slick.driver.MySQLDriver.api._

class Customers(tag : Tag) extends Table[(Option[Long], String, String)](tag, "Customer") {

  def id = column[Long]("customer_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def customerKey = column[String]("customer_key")

  def * = (id.?, name, customerKey)
}