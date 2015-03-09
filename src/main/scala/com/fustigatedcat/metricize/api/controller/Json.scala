package com.fustigatedcat.metricize.api.controller

import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport

trait Json extends Json4sSupport {

  implicit val json4sFormats = DefaultFormats

}
