package com.sprayed.chat.domain

case class User( id: Long, username: String, password: String )

// It is an adaptation of the official Slick example set (Cake Pattern):
// https://github.com/slick/slick-examples (MultiDBCakeExample.scala)
// Understanding that example will help to understand this code.
trait UserComponent { this: Profile =>
  import profile.simple._

  class Users(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def password = column[String]("password")
    def * = (id, name, password) <> ((User.apply _).tupled, User.unapply)

  }
  
  val slickUsers = TableQuery[Users]

  def find(name: String, password: String)(implicit session: Session) = {
    slickUsers.filter(_.name === name).filter(_.password === password).firstOption
  }
      
  def save(user: User)(implicit session: Session) = {
    slickUsers.filter(_.id === user.id).firstOption match {
      case Some(userFound) => slickUsers.filter(_.id === user.id).update(user)
      case None => slickUsers.insert(user)
    }
    user // We do not change the entity => return it
  }

}