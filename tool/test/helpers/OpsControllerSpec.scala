package helpers

import domain.ops.Ops
import org.scalacheck.Arbitrary
import org.scalatestplus.play.BrowserInfo
import play.api.mvc.Call
import protobuf.entity.Entity

abstract class OpsControllerSpec[A](implicit arbitrary: Arbitrary[A], entity: Entity[A])
  extends ToolSpec with Go with Interaction {

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
        val (a, _) = arbitrary2

        go(list)

        assert(ops.get === Nil)

        fill(a)

        click("create")

        eventually {
          assert(ops.get === a :: Nil)
        }
      }
    }

    get(":id").url must {
      s"update with nothing changed ${browser.name}" in {
        val (a, _) = arbitrary2
        ops.set(a)

        go(get(a))

        val last = context.database.get()
        context.database.get() must be theSameInstanceAs last
        assert(ops.get === a :: Nil)

        click("update")

        eventually {
          context.database.get() mustNot be theSameInstanceAs last
          assert(ops.get === a :: Nil)
        }
      }

      s"update ${browser.name}" in {
        val (a, b) = arbitrary2
        val c = update(a, b)
        ops.set(a)

        go(get(a))

        assert(ops.get === a :: Nil)

        fill(c)

        click("update")

        eventually {
          assert(ops.get === c :: Nil)
        }
      }

      s"copy ${browser.name}" in {
        val (a, b) = arbitrary2
        val c = update(b, a)
        ops.set(a)

        go(get(a))

        assert(ops.get === a :: Nil)

        fill(c)

        click("copy")

        eventually {
          assert(ops.get === a :: c :: Nil)
        }
      }

      s"delete ${browser.name}" in {
        val (a, _) = arbitrary2
        ops.set(a)

        go(get(a))

        assert(ops.get === a :: Nil)

        click("delete")

        eventually {
          assert(ops.get === Nil)
        }
      }
    }
  }
}
