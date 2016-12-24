package board.actor

import akka.actor.FSM
import board.message.BoardMessage._
import board.fsm.data.BoardData
import board.fsm.data.BoardData.{InitializeData, Uninitialized}
import board.fsm.state.BoardState._
import board.setting.{BoardSetting, Lines, LinesSetting}
import board.fsm.state.BoardState
import board.message.LinesMessage.InitializeLines

class BoardActor(setting: BoardSetting) extends FSM[BoardState, BoardData] {
  startWith(Init, Uninitialized)

  private[this] def linesSetting(lines: Lines): LinesSetting = LinesSetting(database = setting.database, lines = lines)

  when(Init) {
    case Event(InitializeBoard, Uninitialized) =>
      val friends = context.actorOf(LinesActor.props(linesSetting(setting.friends)))
      val enemies = context.actorOf(LinesActor.props(linesSetting(setting.enemies)))

      friends ! InitializeLines
      enemies ! InitializeLines

      stay using InitializeData(friends = friends, friendsAck = false, enemies = enemies, enemiesAck = false)

    case Event(LinesReady(ref), d: InitializeData) =>
      val data = d.copy(friendsAck = d.friendsAck || d.friends == ref, enemiesAck = d.enemiesAck || d.enemies == ref)

      if (data.friendsAck && data.enemiesAck) {
        goto(Setup) using data
      } else {
        stay using data
      }
  }

  when(Setup) {
    case _ => stay()
  }

  whenUnhandled {
    case Event(e, s) =>
      log.error("received unhandled request {} in state {}/{}", e, stateName, s)
      goto(Error)
  }

  onTransition {
    case Init -> Setup => context.parent ! BoardReady
    case _ -> Error => context.parent ! FallIntoErrorState
  }

  initialize()
}

object BoardActor extends ActorCompanion[BoardActor, BoardSetting] {
  override def apply(setting: BoardSetting): BoardActor = new BoardActor(setting)
}
