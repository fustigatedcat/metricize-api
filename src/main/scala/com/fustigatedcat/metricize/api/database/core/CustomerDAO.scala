package com.fustigatedcat.metricize.api.database.core

import com.fustigatedcat.metricize.api.model.{Customer, Customers}

import scala.slick.driver.MySQLDriver.simple._

object CustomerDAO {

  val customers = TableQuery[Customers]

  def getCustomerByKey(key : String) : Option[Customer] = DB.db.withSession { implicit session =>
    customers.filter(_.customerKey === key).firstOption
  }

}
