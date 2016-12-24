package board.message

sealed trait LinesMessage

object LinesMessage {
  case object InitializeLines extends LinesMessage
}
