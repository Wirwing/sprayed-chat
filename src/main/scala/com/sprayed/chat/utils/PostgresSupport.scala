package com.sprayed.chat.utils

import slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.meta.MTable

import com.sprayed.chat.{ Configs => C }

trait PostgresSupport {

  def db = Database.forURL(
    url    = s"jdbc:postgresql://${C.pgHost}:${C.pgPort}/${C.pgDBName}",
    driver = C.pgDriver
  )

  implicit val session: Session = db.createSession()
  
}