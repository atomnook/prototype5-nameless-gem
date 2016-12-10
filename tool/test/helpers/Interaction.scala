package helpers

import com.trueaccord.scalapb.GeneratedEnum
import helpers.Interaction.Assignment
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite}

import scala.concurrent.duration._

trait Interaction { this: OneServerPerSuite with AllBrowsersPerSuite =>
  private[this] val timeout: FiniteDuration = 10.seconds

  private[this] def explicitlyWait(q: String): Unit = {
    val by = id(q).by
    val wait =  new WebDriverWait(webDriver, timeout.toSeconds)
    wait.until(ExpectedConditions.visibilityOfElementLocated(by))
    wait.until(ExpectedConditions.presenceOfElementLocated(by))
    assert(id(q).findElement.nonEmpty, s"$q not located")
  }

  private[this] def apply[A](q: String, f: A => Unit): Assignment[A] = {
    explicitlyWait(q)
    new Assignment[A] {
      override def :=(value: A): Unit = f(value)
    }
  }

  protected[this] def select(q: String): Assignment[String] = {
    apply(q, value => singleSel(q).value = value)
  }

  protected[this] def enumSelect(q: String): Assignment[GeneratedEnum] = {
    apply(q, value => singleSel(q).value = value.name)
  }

  protected[this] def enumsSelect(q: String): Assignment[Seq[_ <: GeneratedEnum]] = {
    apply(q, value => multiSel(q).values = value.map(_.name))
  }

  protected[this] def text(q: String): Assignment[String] = {
    apply(q, value => textField(q).value = value)
  }

  protected[this] def number(q: String): Assignment[Long] = {
    apply(q, value => numberField(q).value = value.toString)
  }

  protected[this] def click(q: String): Unit = {
    explicitlyWait(q)
    click on id(q)
  }
}

object Interaction {
  trait Assignment[A] {
    def :=(value: A): Unit
  }
}
