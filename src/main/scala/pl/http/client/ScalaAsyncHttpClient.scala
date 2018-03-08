package pl.http.client

import org.asynchttpclient.{AsyncCompletionHandler, AsyncHttpClient, Request => ARequest, Response => AResponse}

import scala.concurrent.{Future, Promise}


object ScalaAsyncHttpClient {

  implicit class AsyncHttpClientExt(val client: AsyncHttpClient) extends AnyVal {
    def asyncExecute(request: ARequest): Future[Response] = {
      asyncExecuteAndMap(request)(new Response(_))
    }

    def asyncExecuteAsString(request: ARequest): Future[String] = {
      asyncExecuteAndMap(request)(_.getResponseBody)
    }

    def asyncExecuteAndMap[T](request: ARequest)(mapper: AResponse => T): Future[T] = {
      val p = Promise[T]()
      // TODO: .toCompletableFuture.toScala
      client.executeRequest(request, new PromiseAsyncCompletionHandler(p)(mapper))
      p.future
    }
  }

  private class PromiseAsyncCompletionHandler[T](p: Promise[T])(mapper: AResponse => T)
    extends AsyncCompletionHandler[AResponse] {

    def onCompleted(response: AResponse): AResponse = {
      p.success(mapper(response))
      response
    }

    override def onThrowable(t: Throwable): Unit = {
      p.failure(t)
    }

  }

}
