package com.fustigatedcat.metricize.api.processor

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.security.MessageDigest
import java.util._
import java.util.zip.{GZIPInputStream, Inflater}

import akka.actor.Actor
import com.fustigatedcat.metricize.api.ActorSystems
import com.fustigatedcat.metricize.api.model.Agent
import org.apache.commons.codec.digest.DigestUtils
import org.json4s.JObject
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._

class InputStatisticProcessor extends Actor {

  implicit val formats = DefaultFormats

  val base64Decoder = Base64.getDecoder

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
    val bytes = base64Decoder.decode(msg)
    val out = readInflater(new GZIPInputStream(new ByteArrayInputStream(bytes)), new ByteArrayOutputStream())
    new String(out, "UTF-8")
  }

  def MD5Valid_?(msg : String, md5 : String) : Boolean = {
    DigestUtils.md5Hex(msg) == md5
  }

  override def receive: Receive = {
    case (agent : Agent, input : JObject) => {
      val orig = extractAndInflate((input \ "msg").extract[String])
      if(MD5Valid_?(orig, (input \ "md5").extract[String])) {
        ActorSystems.edgeQueue ! orig
      } else {
        ActorSystems.invalidEdgeQueue ! orig
      }
    }
    case _ => println("Invalid statistic")
  }

}
