package helpers

import java.io.File

import domain.service.{DatabaseService, ServiceContext}
import org.openqa.selenium.WebDriver
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play.BrowserFactory.UnavailableDriver
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

trait ToolSpec
  extends PlaySpec
    with OneServerPerSuite with OneBrowserPerSuite with FirefoxFactory with BeforeAndAfterEach with BeforeAndAfterAll {

  implicit override lazy val app: Application = {
    new GuiceApplicationBuilder().configure("database.init.enable " -> false).build()
  }

  protected[this] val context: ServiceContext = app.injector.instanceOf(classOf[ServiceContext])

  protected[this] val service = DatabaseService(context)

  protected[this] val browser = FirefoxInfo(firefoxProfile)

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    context.clear()
  }

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
