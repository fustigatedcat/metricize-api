package com.fustigatedcat.metricize.api.controller

import spray.http.MediaTypes._
import spray.httpx.Json4sSupport
import spray.routing.HttpService

trait CustomerController extends HttpService { this: Auth with Json4sSupport =>

  val customerRoutes = pathPrefix("customers" / "me") {
    pathEnd {
      authenticate(validateCustomer) { customer =>
        respondWithMediaType(`application/json`) {
          complete {
            customer
          }
        }
      }
    }
  }

}
