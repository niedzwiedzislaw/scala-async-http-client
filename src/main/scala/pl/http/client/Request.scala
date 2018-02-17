package pl.http.client

import io.netty.handler.codec.http.HttpMethod
import org.asynchttpclient.{Request => ARequest, RequestBuilder}

sealed abstract class Request(method: HttpMethod) {

  import collection.JavaConverters._

  def apply(
    url: String
  ): ARequest = apply(url, Map.empty[CharSequence, AnyRef])

  def apply(
    url: String,
    headers: Map[CharSequence, AnyRef]
  ): ARequest = {
    new RequestBuilder()
      .setMethod(method.name())
      .setUrl(url)
      .setSingleHeaders(headers.asJava)
      .build()
  }

  def apply(
    url: String,
    body: String
  ): ARequest = apply(url, body, Map.empty[CharSequence, AnyRef])

  def apply(
    url: String,
    body: String,
    headers: Map[CharSequence, AnyRef]
  ): ARequest = {
    new RequestBuilder()
      .setMethod(method.name())
      .setUrl(url)
      .setBody(body)
      .setSingleHeaders(headers.asJava)
      .build()
  }

  def apply(
    url: String,
    body: Array[Byte]
  ): ARequest = apply(url, body, Map.empty[CharSequence, AnyRef])

  def apply(
    url: String,
    body: Array[Byte],
    headers: Map[CharSequence, AnyRef]
  ): ARequest = {
    new RequestBuilder()
      .setMethod(method.name())
      .setUrl(url)
      .setBody(body)
      .setSingleHeaders(headers.asJava)
      .build()
  }
}

object Request {
  object Get extends Request(HttpMethod.GET)
  object Post extends Request(HttpMethod.POST)
  object Put extends Request(HttpMethod.PUT)
  object Delete extends Request(HttpMethod.DELETE)
  object Patch extends Request(HttpMethod.PATCH)
  object Options extends Request(HttpMethod.OPTIONS)
  object Head extends Request(HttpMethod.HEAD)
}