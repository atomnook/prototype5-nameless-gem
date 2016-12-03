package controllers

import domain.ops.Ops
import helpers.{Go, ToolSpec}
import org.scalatestplus.play._
import play.api.mvc.Call
import protobuf.Character
import protobuf.arbitrary._

class CharacterControllerSpec extends ToolSpec with Go {
  protected[this] def list: Call = routes.CharacterController.list()

  protected[this] def get(id: String): Call = routes.CharacterController.get(id)

  protected[this] def get(a: Character): Call = get(a.getId.id)

  protected[this] def ops: Ops[Character] = service.characters

  protected[this] def update(id: Character, data: Character): Character = data.update(_.id := id.getId)

  protected[this] def fill(a: Character): Unit = {
    textField("id").value = a.getId.id
    textField("name").value = a.name
  }

  override def sharedTests(browser: BrowserInfo): Unit = {
    list.url must {
      s"create ${browser.name}" in {
        val button = id("create")
        val a = arbitrary1[Character]

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
      s"update ${browser.name}" in {
        val button = id("update")
        val (a, b) = arbitrary2[Character]
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
        val (a, b) = arbitrary2[Character]
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
        val a = arbitrary1[Character]
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
