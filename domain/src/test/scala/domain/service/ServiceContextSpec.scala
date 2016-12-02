package domain.service

import java.util.concurrent.atomic.AtomicReference

import org.scalacheck.Prop._
import org.scalatest.FlatSpec
import org.scalatest.prop.Checkers
import protobuf.Database
import protobuf.arbitrary._

class ServiceContextSpec extends FlatSpec with Checkers {
  it should "write/apply a string" in {
    check { database: Database =>
      val expected = ServiceContext(new AtomicReference[Database](database))
      ServiceContext(expected.write).database.get() == database
    }
  }
}
