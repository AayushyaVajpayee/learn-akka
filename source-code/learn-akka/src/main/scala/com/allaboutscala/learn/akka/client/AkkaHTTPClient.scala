package com.allaboutscala.learn.akka.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpMethods}
import akka.stream.ActorMaterializer

import scala.util.{Success, Failure}

/**
  * Created by Nadim Bahadoor on 28/06/2016.
  *
  *  Tutorial: Learn How To Use Akka HTTP
  *
  * [[http://allaboutscala.com/scala-frameworks/akka/]]
  *
  * Copyright 2016 Nadim Bahadoor (http://allaboutscala.com)
  *
  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
  * use this file except in compliance with the License. You may obtain a copy of
  * the License at
  *
  *  [http://www.apache.org/licenses/LICENSE-2.0]
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  * License for the specific language governing permissions and limitations under
  * the License.
  */
object AkkaHttpClient extends App {

  implicit val system = ActorSystem("akka-http-donuts-client")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val donutsUri = "http://localhost:8080/donuts"
  val donutsHttpRequest = HttpRequest(
    uri = donutsUri,
    method = HttpMethods.GET
  )

  val donutsResponse = Http().singleRequest(donutsHttpRequest)
  donutsResponse
    .onComplete {
      case Success(donutsResponse) => println(s"Raw HttpResponse = $donutsResponse")
      case Failure(e) => println(s"Failed to HTTP GET $donutsUri, error = ${e.getMessage}")
    }

  Thread.sleep(3000)
  system.terminate()
}