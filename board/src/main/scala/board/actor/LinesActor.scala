package board.actor

import akka.actor.FSM
import board.fsm.data.LinesData
import board.fsm.data.LinesData.Uninitialized
import board.fsm.state.LinesState
import board.fsm.state.LinesState.{Init, Ready}
import board.message.BoardMessage.LinesReady
import board.message.LinesMessage.InitializeLines
import board.setting.LinesSetting

class LinesActor(setting: LinesSetting) extends FSM[LinesState, LinesData] {
  startWith(Init, Uninitialized)

  when(Init) {
    case Event(InitializeLines, Uninitialized) =>
      // todo
      stay()
  }

  when(Ready) {
    case _ =>
      stay()
  }

  onTransition {
    case Init -> Ready => context.parent ! LinesReady(self)
  }

  initialize()
}

object LinesActor extends ActorCompanion[LinesActor, LinesSetting] {
  override def apply(setting: LinesSetting): LinesActor = new LinesActor(setting)
}
