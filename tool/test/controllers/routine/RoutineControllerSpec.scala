package controllers.routine

import domain.ops.Ops
import helpers.{Go, ToolSpec}
import org.scalatestplus.play.BrowserInfo
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.entity.Entity
import protobuf.routine.{Action, Alternative, Routine}

class RoutineControllerSpec extends ToolSpec with Go {
  private[this] def list: Call = routes.RoutineController.list()

  private[this] def get(a: Routine): Call = routes.RoutineController.get(a.getId.id)

  private[this] def ops: Ops[Routine] = service.routines

  private[this] def fill(a: Alternative, index: Option[Int]): Unit = {
    val i = index.map(_.toString).getOrElse("")
    textField(s"alternative-id$i").value = a.getId.id
    singleSel(s"condition$i").value = a.condition.name
    a.`then`.foreach(id => singleSel(s"then$i").value = id.id)
    a.`else`.foreach(id => singleSel(s"else$i").value = id.id)
  }

  private[this] def fill(a: Action, index: Option[Int]): Unit = {
    val i = index.map(_.toString).getOrElse("")
    textField(s"action-id$i").value = a.getId.id
    singleSel(s"name$i").value = a.name.name
    singleSel(s"type$i").value = a.`type`.name
    multiSel(s"intersection$i").values = a.intersection.map(_.name)
    a.otherwise.foreach(id => singleSel(s"otherwise$i").value = id.id)
  }

  override def sharedTests(browser: BrowserInfo): Unit = {
    list.url must {
      s"create ${browser.name}" in {
        val button = id("create")
        val a = arbitrary1[Routine].clearAlternatives.clearActions

        go(list, button)

        assert(ops.get === Nil)

        textField("id").value = a.getId.id

        click on button

        eventually {
          assert(ops.get === a :: Nil)
        }
      }
    }

    get(implicitly[Entity[Routine]].identity(":id")).url must {
      s"apply ${browser.name}" in {
        val button = id("apply")

        def eventuallyApplied(r: Routine): Routine = {
          click on button

          eventually {
            assert(ops.get === r :: Nil)
          }

          explicitlyWait(id("footer"))

          r
        }

        def remove(q: String): Unit = {
          val button = id(q)

          click on button

          eventually {
            assert(button.findElement === None)
          }
        }

        val empty = arbitrary1[Routine].clearAlternatives.clearActions
        ops.set(empty)

        go(get(empty), button)

        assert(ops.get === empty :: Nil)

        // add alternatives
        val alternatives = Seq(0, 1).foldLeft(empty) { case (routine, i) =>
          val alt = arbitrary1[Alternative].update(_.id.id := s"alt$i").clearThen.clearElse

          fill(alt, None)

          eventuallyApplied(routine.update(_.alternatives :+= alt))
        }

        // add actions
        val actions = Seq(0, 1).foldLeft(alternatives) { case (routine, i) =>
          val act = arbitrary1[Action].update(_.id.id := s"act$i").clearOtherwise

          fill(act, None)

          eventuallyApplied(routine.update(_.actions :+= act))
        }

        // change
        val alt0 = actions.alternatives.head
        val act0 = actions.actions.head
        val changedAlt1 = arbitrary1[Alternative].update(_.`then` := alt0.getId, _.`else` := act0.getId)
        val changedAct1 = arbitrary1[Action].update(_.otherwise := act0.getId)

        fill(changedAlt1, Some(1))
        fill(changedAct1, Some(1))

        eventuallyApplied(actions.update(
          _.alternatives := alt0 :: changedAlt1 :: Nil,
          _.actions := act0 :: changedAct1 :: Nil))

        // remove
        remove("alternative-remove1")
        remove("action-remove1")

        eventuallyApplied(actions.update(
          _.alternatives := alt0 :: Nil,
          _.actions := act0 :: Nil))
      }

      s"delete s${browser.name}" in {
        val button = id("delete")
        val a = arbitrary1[Routine].clearAlternatives.clearActions
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
