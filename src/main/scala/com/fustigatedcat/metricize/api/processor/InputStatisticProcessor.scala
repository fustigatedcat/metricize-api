package com.fustigatedcat.metricize.api.processor

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.zip.GZIPInputStream

import akka.actor.Actor
import akka.camel.CamelMessage
import com.fustigatedcat.metricize.api.ActorSystems
import com.fustigatedcat.metricize.api.model.Agent
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.DigestUtils
import org.json4s.JObject
import org.json4s.DefaultFormats

class InputStatisticProcessor extends Actor {

  implicit val formats = DefaultFormats

  def readInflater(inflater : GZIPInputStream, stream : ByteArrayOutputStream) : Array[Byte] = {
    val tmp = new Array[Byte](100)
    inflater.read(tmp) match {
      case -1 => {
        inflater.close()
        stream.toByteArray
      }
      case num => {
        stream.write(tmp, 0, num)
        readInflater(inflater, stream)
      }
    }
  }

  def extractAndInflate(msg : String) : String = {
    val bytes = Base64.decodeBase64(msg)
    val out = readInflater(new GZIPInputStream(new ByteArrayInputStream(bytes)), new ByteArrayOutputStream())
    new String(out, "UTF-8")
  }

  def MD5Valid_?(msg : String, md5 : String) : Boolean = {
    DigestUtils.md5Hex(msg) == md5
  }

  override def receive: Receive = {
    case (agent : Agent, input : JObject) => {
      val orig = extractAndInflate((input \ "msg").extract[String])
      val status = (input \ "status").extract[String]
      val startTime = (input \ "startTime").extract[Long]
      val time = (input \ "time").extract[Long]
      if(MD5Valid_?(orig, (input \ "md5").extract[String])) {
        ActorSystems.edgeQueue ! CamelMessage(
          orig,
          Map(
            "agent-id" -> agent.id.get,
            "agent-type" -> agent.agentType.get,
            "status" -> status,
            "time" -> time,
            "startTime" -> startTime
          )
        )
      } else {
        ActorSystems.invalidEdgeQueue ! CamelMessage(
          orig,
          Map(
            "agent-id" -> agent.id.get,
            "agent-type" -> agent.agentType.get,
            "status" -> status,
            "time" -> time,
            "startTime" -> startTime
          )
        )
      }
    }
    case _ => println("Invalid statistic")
  }

}
