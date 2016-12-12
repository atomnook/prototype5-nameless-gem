package controllers.routine

import domain.ops.Ops
import helpers.{Go, Interaction, ToolSpec}
import org.scalatest.time.{Millis, Seconds, Span}
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.entity.Entity
import protobuf.routine.{Action, Alternative, Routine}

class RoutineControllerSpec extends ToolSpec with Go with Interaction {
  private[this] implicit override val patienceConfig =
    PatienceConfig(timeout = scaled(Span(30, Seconds)), interval = scaled(Span(150, Millis)))

  private[this] def list: Call = routes.RoutineController.list()

  private[this] def get(a: Routine): Call = routes.RoutineController.get(a.getId.id)

  private[this] def ops: Ops[Routine] = service.routines

  private[this] def fill(a: Alternative, index: Option[Int]): Unit = {
    val i = index.map(_.toString).getOrElse("")
    text(s"alternative-id$i") := a.getId.id
    enumSelect(s"condition$i") := a.condition
    a.satisfied.foreach(id => select(s"satisfied$i") := id.id)
    a.otherwise.foreach(id => select(s"alternative-otherwise$i") := id.id)
  }

  private[this] def fill(a: Action, index: Option[Int]): Unit = {
    val i = index.map(_.toString).getOrElse("")
    text(s"action-id$i") := a.getId.id
    enumSelect(s"name$i") := a.name
    enumSelect(s"type$i") := a.`type`
    enumsSelect(s"intersection$i") := a.intersection
    a.otherwise.foreach(id => select(s"action-otherwise$i") := id.id)
  }

  list.url must {
    s"create ${browser.name}" in {
      val a = arbitrary1[Routine].clearAlternatives.clearActions

      go(list)

      assert(ops.get === Nil)

      text("id") := a.getId.id

      click("create")

      eventually {
        assert(ops.get === a :: Nil)
      }
    }
  }

  get(implicitly[Entity[Routine]].identity(":id")).url must {
    s"apply ${browser.name}" in {
      def eventuallyApplied(r: Routine): Routine = {
        click("apply")

        eventually {
          assert(ops.get === r :: Nil)
        }

        r
      }

      def remove(q: String): Unit = {
        click(q)

        eventually {
          assert(id(q).findElement === None)
        }
      }

      val empty = arbitrary1[Routine].clearAlternatives.clearActions
      ops.set(empty)

      go(get(empty))

      assert(ops.get === empty :: Nil)

      // add alternatives
      val alternatives = Seq(0, 1).foldLeft(empty) { case (routine, i) =>
        val alt = arbitrary1[Alternative].update(_.id.id := s"alt$i").clearSatisfied.clearOtherwise

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
      val changedAlt1 = arbitrary1[Alternative].update(_.satisfied := alt0.getId, _.otherwise := act0.getId)
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
      val a = arbitrary1[Routine].clearAlternatives.clearActions
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
