package com.fustigatedcat.metricize.api

import com.typesafe.config.ConfigFactory

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

}
