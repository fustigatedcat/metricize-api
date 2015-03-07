package com.fustigatedcat.metricize.api.controller

import akka.actor.Actor
import com.fustigatedcat.metricize.api.CoreDB
import com.fustigatedcat.metricize.api.model.{Agent, Customer}
import spray.routing.{MissingHeaderRejection, AuthorizationFailedRejection}
import spray.routing.authentication._

import scala.concurrent.Future

import scala.slick.driver.MySQLDriver.simple._

trait Auth { this : Actor =>

  implicit val executionContext = context.dispatcher

  def validateCustomer : ContextAuthenticator[Customer] = {
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

  def validateAgent : ContextAuthenticator[Agent] = {
    ctx => ctx.request.headers.find(_.name == "Authorization") match {
      case Some(key) => {
        CoreDB.db.withSession { implicit session =>
          CoreDB.agents.filter(_.agentKey === key.value).firstOption match {
            case Some(agent) => Future(Right(agent))
            case _ => Future(Left(AuthorizationFailedRejection))
          }
        }
      }
      case _ => Future(Left(MissingHeaderRejection("Authorization header is required")))
    }
  }

}
