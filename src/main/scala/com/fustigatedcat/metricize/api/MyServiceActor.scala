package com.fustigatedcat.metricize.api

import akka.actor.Actor
import com.fustigatedcat.metricize.api.model.Customers
import spray.http.MediaTypes._
import spray.routing.{MissingHeaderRejection, HttpService}
import spray.routing.authentication._

import scala.concurrent.Future

import slick.driver.MySQLDriver.api._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with HttpService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  implicit val executionContext = context.dispatcher

  val customers = TableQuery[Customers]

  val db = Database.forURL("jdbc:mysql://localhost:3306/metricize", user = "root", password = "", driver="com.mysql.jdbc.Driver")

  def validateToken : ContextAuthenticator[String] = {
    ctx => ctx.request.headers.find(_.name == "Authorization") match {
      case Some(key) => {
        db.withSession { implicit session =>
          customers.filter(_.customerKey === key.value)
          Future(Right("Foo"))
        }
      }
      case _ => Future(Left(MissingHeaderRejection("Authorization header is required")))
    }
  }

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(pathPrefix("api" / "customer") {
    authenticate(validateToken) { user =>
      get {
        respondWithMediaType(`application/json`) {
          complete {
            s"{ }"
          }
        }
      }
    }
  })

}