package com.fustigatedcat.metricize.api

import com.typesafe.config.ConfigFactory

import collection.JavaConversions._

object Configuration {

  val config = ConfigFactory.load()

  object database {
    object core {
      object jdbc {
        val url = config.getString("database.core.jdbc.url")
        val driver = config.getString("database.core.jdbc.driver")
      }
      val username = config.getString("database.core.username")
      val password = config.getString("database.core.password")
    }
  }

  object queue {
    object core {
      val addresses = config.getConfigList("queue.core.addresses").map(c => (c.getString("hostname"), c.getInt("port"))).toList
      val username = config.getString("queue.core.username")
      val password = config.getString("queue.core.password")
      val virtualhost = config.getString("queue.core.virtualhost")
    }

  }

}
