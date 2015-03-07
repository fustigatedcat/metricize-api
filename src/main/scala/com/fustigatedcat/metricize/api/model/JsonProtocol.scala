package com.fustigatedcat.metricize.api.model

import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol{

  implicit val CustomerFormat = jsonFormat3(Customer)

}
