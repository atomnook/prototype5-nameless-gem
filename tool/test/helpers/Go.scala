package helpers

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite}
import play.api.mvc.Call

import scala.concurrent.duration._

trait Go { this: OneServerPerSuite with AllBrowsersPerSuite =>
  protected[this] def go(call: Call, query: Query, timeout: FiniteDuration = 10.seconds): Unit = {
    go to s"http://localhost:$port" + call.url
    explicitlyWait(query, timeout)
  }

  protected[this] def explicitlyWait(query: Query, timeout: FiniteDuration = 10.seconds): Unit = {
    new WebDriverWait(webDriver, timeout.toSeconds).until(ExpectedConditions.presenceOfElementLocated(query.by))
  }
}
