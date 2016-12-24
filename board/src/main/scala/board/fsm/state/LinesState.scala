package board.fsm.state

sealed trait LinesState

object LinesState {
  case object Init extends LinesState

  case object Ready extends LinesState
}
