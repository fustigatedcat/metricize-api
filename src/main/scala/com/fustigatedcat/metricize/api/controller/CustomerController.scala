package com.fustigatedcat.metricize.api.controller

import spray.http.MediaTypes._
import spray.routing.HttpService

import spray.httpx.marshalling._
import com.fustigatedcat.metricize.api.model.JsonProtocol._
import spray.httpx.SprayJsonSupport._

trait CustomerController extends HttpService { this: Auth =>

  val customerRoutes = pathPrefix("api") {
    authenticate(validateToken) { customer =>
      pathPrefix("customer") {
        get {
          respondWithMediaType(`application/json`) {
            complete {
              marshal(customer)
            }
          }
        }
      }
    }
  }

}
