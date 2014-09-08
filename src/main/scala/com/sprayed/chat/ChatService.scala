package com.sprayed.chat

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import shapeless._

import akka.event.Logging
import spray.client.pipelining._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ChatServiceActor extends Actor with ChatService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait ChatService extends HttpService with StaticResources with TwirlPages with LoginService{

  val myRoute = staticResources ~ twirlPages ~ loginRoutes

}

trait LoginService extends HttpService {

  val loginRoutes = path("login"){
    post{
      formFields('username, 'password) { (username, password) =>
        println(username)
        println(password)
        complete("chido")
      }
    }
  }

}

// Trait for serving static resources
// Sends 404 for 'favicon.icon' requests and serves static resources in 'bootstrap' folder.
trait StaticResources extends HttpService {

  val staticResources = pathPrefix("js") { get { getFromResourceDirectory("js") } } ~ 
                pathPrefix("css") { get { getFromResourceDirectory("css") } }

}

trait TwirlPages extends HttpService {

  val twirlPages = {
    get {
       path("index") { 
        respondWithMediaType(`text/html`) {
          complete (html.simple.render("Irving").toString )
        }
       } ~
       path("proxy.html") {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete (html.proxy.render.toString)
        }
        
       }
    }
  }

}