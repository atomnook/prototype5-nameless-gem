package helpers

import java.io.File

import org.openqa.selenium.WebDriver
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.BrowserFactory.UnavailableDriver
import org.scalatestplus.play.{BrowserInfo, FirefoxFactory, FirefoxInfo}

trait FirefoxSpec extends ToolSpec with FirefoxFactory with BeforeAndAfterAll {
  override protected[this] lazy val browser: BrowserInfo = FirefoxInfo(firefoxProfile)

  override protected def afterAll(): Unit = {
    super.afterAll()
    webDriver match {
      case _: UnavailableDriver =>
      case other => other.quit()
    }
  }

  private[this] val driver = "webdriver.gecko.driver"

  override implicit lazy val webDriver: WebDriver = {
    if (sys.props.get(driver).exists(file => new File(file).isFile)) {
      createWebDriver()
    } else {
      UnavailableDriver(None, s"$driver not found")
    }
  }
}
