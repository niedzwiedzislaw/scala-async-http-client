package pl.http.client

import org.asynchttpclient._

import scala.concurrent.{Future, Promise}


object ScalaAsyncHttpClient {

  implicit class BoundRequestBuilderExt(val request: BoundRequestBuilder) extends AnyVal {
    def asyncExecute(): Future[Response] = {
      asyncExecuteAndMap(identity)
    }
    def asyncExecuteAsString(): Future[String] = {
      asyncExecuteAndMap(response => response.getResponseBody)
    }
    def asyncExecuteAndMap[T](mapper: Response => T): Future[T] = {
      val p = Promise[T]()
      request.execute(new PromiseAsyncCompletionHandler(p)(mapper))
      p.future
    }
  }

  implicit class AsyncHttpClientExt(val client: AsyncHttpClient) extends AnyVal {
    def asyncExecute(request: Request): Future[Response] = {
      asyncExecuteAndMap(request)(identity)
    }
    def asyncExecuteAsString(request: Request): Future[String] = {
      asyncExecuteAndMap(request)(response => response.getResponseBody)
    }
    def asyncExecuteAndMap[T](request: Request)(mapper: Response => T): Future[T] = {
      val p = Promise[T]()
      client.executeRequest(request, new PromiseAsyncCompletionHandler(p)(mapper))
      p.future
    }
  }

  private class PromiseAsyncCompletionHandler[T](p: Promise[T])(mapper: Response => T)
    extends AsyncCompletionHandler[Response] {

    def onCompleted(response: Response): Response = {
      p.success(mapper(response))
      response
    }

    override def onThrowable(t: Throwable) = {
      p.failure(t)
    }

  }

}