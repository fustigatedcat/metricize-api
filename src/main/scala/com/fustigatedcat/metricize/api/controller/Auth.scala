package com.fustigatedcat.metricize.api.controller

import akka.actor.Actor
import com.fustigatedcat.metricize.api.CoreDB
import com.fustigatedcat.metricize.api.model.Customer
import spray.routing.{MissingHeaderRejection, AuthorizationFailedRejection}
import spray.routing.authentication._

import scala.concurrent.Future

import scala.slick.driver.MySQLDriver.simple._

trait Auth { this : Actor =>

  implicit val executionContext = context.dispatcher

  def validateToken : ContextAuthenticator[Customer] = {
    ctx => ctx.request.headers.find(_.name == "Authorization") match {
      case Some(key) => {
        CoreDB.db.withSession { implicit session =>
          CoreDB.customers.filter(_.customerKey === key.value).firstOption match {
            case Some(customer) => Future(Right(customer))
            case _ => Future(Left(AuthorizationFailedRejection))
          }
        }
      }
      case _ => Future(Left(MissingHeaderRejection("Authorization header is required")))
    }
  }

}
