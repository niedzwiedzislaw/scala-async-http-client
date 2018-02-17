package pl.http.client


import org.asynchttpclient.{Response => AResponse}

class Response(response: AResponse) {

  import collection.JavaConverters._

  lazy val status: Int = response.getStatusCode
  lazy val body: String = response.getResponseBody
  lazy val headers: Map[String, String] = response.getHeaders.entries().asScala.map { header =>
    header.getKey -> header.getValue
  }.toMap

}