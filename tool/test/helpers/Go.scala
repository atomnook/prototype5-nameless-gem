package helpers

import org.scalatestplus.play.{OneBrowserPerSuite, OneServerPerSuite}
import play.api.mvc.Call

trait Go { this: OneServerPerSuite with OneBrowserPerSuite =>
  protected[this] def go(call: Call): Unit = {
    go to s"http://localhost:$port" + call.url
  }
}
