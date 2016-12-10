package helpers

import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite}
import play.api.mvc.Call

trait Go { this: OneServerPerSuite with AllBrowsersPerSuite =>
  protected[this] def go(call: Call): Unit = {
    go to s"http://localhost:$port" + call.url
  }
}
