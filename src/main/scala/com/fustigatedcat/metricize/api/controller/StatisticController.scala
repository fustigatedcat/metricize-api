package com.fustigatedcat.metricize.api.controller

import spray.httpx.Json4sSupport
import spray.routing.HttpService

import spray.http.MediaTypes._

trait StatisticController extends HttpService { this: Auth with Json4sSupport =>

  val statisticRoutes = pathPrefix("api") {
    pathPrefix("statistics") {
      authenticate(validateAgent) { agent =>
        post {
          respondWithMediaType(`application/json`) {
            complete {
              ("status" -> "ok")
            }
          }
        }
      }
    }
  }

}
