package board.fsm.data

sealed trait LinesData

object LinesData {
  case object Uninitialized extends LinesData

  case class Data(front: List[PieceHolder], rear: List[PieceHolder]) extends LinesData
}
