package com.fustigatedcat.metricize.api.controller

import com.fustigatedcat.metricize.api.ActorSystems
import org.json4s.JObject
import org.json4s._
import spray.http.StatusCodes
import spray.httpx.Json4sSupport
import spray.routing.HttpService

import spray.http.MediaTypes._

trait StatisticController extends HttpService { this: Auth with Json4sSupport =>

  val statisticRoutes = pathPrefix("api") {
    pathPrefix("statistics") {
      authenticate(validateAgent) { agent =>
        post {
          entity(as[JObject]) { req =>
            respondWithMediaType(`application/json`) { ctx =>
              (
                (req \ "status").extractOpt[String],
                (req \ "time").extractOpt[Long],
                (req \ "md5").extractOpt[String],
                (req \ "msg").extractOpt[String]
              ) match {
                case (Some(_), Some(_), Some(_), Some(_)) => {
                  ActorSystems.inputStatisticProcessor !(agent, req)
                  ctx.complete(StatusCodes.Accepted)
                }
                case _ => ctx.complete(StatusCodes.BadRequest)
              }
            }
          }
        }
      }
    }
  }

}
