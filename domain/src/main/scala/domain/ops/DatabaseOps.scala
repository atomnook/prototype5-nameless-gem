package domain.ops

import java.util.concurrent.atomic.AtomicReference

import com.trueaccord.lenses.Lens
import protobuf.Database
import protobuf.Database.DatabaseLens
import protobuf.entity.Entity

class DatabaseOps[A] private(database: AtomicReference[Database], all: Database => Seq[A], lens: Lens[Database, Seq[A]])
                            (implicit entity: Entity[A]) extends Ops[A] {
  override def set(a: A): Unit = {
    val data = database.get()
    val updated = all(data).filterNot(b => entity.equal(a, b)) :+ a
    database.set(lens.set(updated)(data))
  }

  override def get: List[A] = all(database.get()).toList

  override def delete(a: A): Unit = {
    val data = database.get()
    val updated = all(data).filterNot(b => entity.equal(a, b))
    database.set(lens.set(updated)(data))
  }
}

object DatabaseOps {
  private[this] val unit = Database.DatabaseLens(Lens.unit[Database])

  def apply[A : Entity](database: AtomicReference[Database],
                        f: Database => Seq[A],
                        g: DatabaseLens[Database] => Lens[Database, Seq[A]]): DatabaseOps[A] = {
    new DatabaseOps(database, f, g(unit))
  }
}
