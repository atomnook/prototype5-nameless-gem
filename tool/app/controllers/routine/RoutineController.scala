package controllers.routine

import javax.inject.Inject

import controllers.OpsController
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.routine.RoutineSetter
import protobuf.routine.Routine
import views.html

class RoutineController @Inject() (context: ServiceContext) extends OpsController[Routine, RoutineSetter](context) {
  override protected[this] def list(a: List[Routine]): HtmlContent = html.routine.RoutineController.list(a)

  override protected[this] def get(a: Routine): HtmlContent = html.routine.RoutineController.get(a)

  override protected[this] val ops: Ops[Routine] = service.routines
}
