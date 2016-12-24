package board.setting

import java.util.UUID

import protobuf.Database

case class BoardSetting(id: UUID, database: Database, friends: Lines, enemies: Lines)
