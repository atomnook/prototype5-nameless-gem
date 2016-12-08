package helpers

import domain.ops.Ops
import org.scalacheck.Arbitrary
import org.scalatestplus.play.BrowserInfo
import play.api.mvc.Call
import protobuf.entity.Entity

abstract class OpsControllerSpec[A](implicit arbitrary: Arbitrary[A], entity: Entity[A]) extends ToolSpec with Go {
  protected[this] def list: Call

  protected[this] def get(a: A): Call

  protected[this] def ops: Ops[A]

  protected[this] def update(id: A, data: A): A

  protected[this] def fill(a: A): Unit

  protected[this] def arbitrary2: (A, A) = protobuf.arbitrary.arbitrary2

  private[this] def get(id: String): Call = get(entity.identity(id))

  override def sharedTests(browser: BrowserInfo): Unit = {
    list.url must {
      s"create ${browser.name}" in {
        val button = id("create")
        val (a, _) = arbitrary2

        go(list, button)

        assert(ops.get === Nil)

        fill(a)

        click on button

        eventually {
          assert(ops.get === a :: Nil)
        }
      }
    }

    get(":id").url must {
      s"update with nothing changed ${browser.name}" in {
        val button = id("update")
        val (a, _) = arbitrary2
        ops.set(a)

        go(get(a), button)

        val last = context.database.get()
        context.database.get() must be theSameInstanceAs last
        assert(ops.get === a :: Nil)

        click on button

        eventually {
          context.database.get() mustNot be theSameInstanceAs last
          assert(ops.get === a :: Nil)
        }
      }

      s"update ${browser.name}" in {
        val button = id("update")
        val (a, b) = arbitrary2
        val c = update(a, b)
        ops.set(a)

        go(get(a), button)

        assert(ops.get === a :: Nil)

        fill(c)

        click on button

        eventually {
          assert(ops.get === c :: Nil)
        }
      }

      s"copy ${browser.name}" in {
        val button = id("copy")
        val (a, b) = arbitrary2
        val c = update(b, a)
        ops.set(a)

        go(get(a), button)

        assert(ops.get === a :: Nil)

        fill(c)

        click on button

        eventually {
          assert(ops.get === a :: c :: Nil)
        }
      }

      s"delete ${browser.name}" in {
        val button = id("delete")
        val (a, _) = arbitrary2
        ops.set(a)

        go(get(a), button)

        assert(ops.get === a :: Nil)

        click on button

        eventually {
          assert(ops.get === Nil)
        }
      }
    }
  }
}
