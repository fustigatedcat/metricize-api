package com.fustigatedcat.metricize.api.controller

import com.fustigatedcat.metricize.api.model.Customer
import spray.http.MediaTypes._
import spray.routing.HttpService

import spray.httpx.marshalling._
import com.fustigatedcat.metricize.api.model.JsonProtocol._
import spray.httpx.SprayJsonSupport._

trait CustomerController extends HttpService { this: Auth =>

  def getCustomer(customer : Customer) = respondWithMediaType(`application/json`) {
    complete {
      marshal(customer)
    }
  }

  val customerRoutes = pathPrefix("api") {
    pathPrefix("customers" / "me") {
      pathEnd {
        authenticate(validateCustomer) { customer =>
          get {
            getCustomer(customer)
          }
        }
      }
    }
  }

}
