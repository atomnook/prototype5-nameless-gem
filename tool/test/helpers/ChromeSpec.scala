package helpers

import org.scalatestplus.play.{BrowserInfo, ChromeFactory, ChromeInfo}

trait ChromeSpec extends ToolSpec with ChromeFactory {
  override protected[this] lazy val browser: BrowserInfo = ChromeInfo
}
