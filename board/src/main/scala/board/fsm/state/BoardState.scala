package board.fsm.state

sealed trait BoardState

object BoardState {
  case object Init extends BoardState

  case object Setup extends BoardState

  case object Error extends BoardState
}
