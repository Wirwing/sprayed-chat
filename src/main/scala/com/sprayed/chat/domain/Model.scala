package com.sprayed.chat.domain

import scala.slick.driver.JdbcProfile
import scala.slick.driver.H2Driver
import scala.slick.jdbc.JdbcBackend.Database

class Model(name: String, dal: DAL, db: Database) {

  // We only need the DB/session imports outside the DAL
  import dal._
  import dal.profile.simple._

  // Put an implicitSession in scope for database actions
  implicit val implicitSession = db.createSession

  def createDB = dal.create

  def dropDB = dal.drop
  
  def purgeDB = dal.purge

  def findUser(username: String, password: String): Option[User] = {
    find(username, password)
  }

  def addUser(user: User): User = {
    save(user)
  }
}