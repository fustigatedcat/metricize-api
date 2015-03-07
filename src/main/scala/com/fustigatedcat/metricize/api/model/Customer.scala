package com.fustigatedcat.metricize.api.model

import slick.driver.MySQLDriver.simple._

case class Customer(id : Option[Long], name : String, customerKey : String)

class Customers(tag : Tag) extends Table[Customer](tag, "Customer") {

  def id = column[Long]("customer_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def customerKey = column[String]("customer_key")

  def * = (id.?, name, customerKey) <> (Customer.tupled, Customer.unapply)
}