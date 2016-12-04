package models.setter

import models.value.Identifier
import protobuf.routine.Expression

package object routine {
  implicit class IdentifierToExpression(id: Identifier) {
    def expression: Expression = Expression().update(_.id := id.id)
  }
}
