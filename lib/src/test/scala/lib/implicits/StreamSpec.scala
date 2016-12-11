package lib.implicits

import Stream._
import org.scalatest.FlatSpec

class StreamSpec extends FlatSpec {
  private[this] def tupled(n: Int)(f: Stream[Int] => Unit) = {
    it should s"have tupled$n" in {
      f((1 to n).toStream)
      f((1 to n * 2).toStream)

      intercept[IllegalArgumentException](f(Nil.toStream))
      intercept[IllegalArgumentException](f((1 until n).toStream))
    }
  }

  tupled(2)(stream => assert(stream.tupled2 == (1, 2)))
  tupled(3)(stream => assert(stream.tupled3 == (1, 2, 3)))
  tupled(4)(stream => assert(stream.tupled4 == (1, 2, 3, 4)))
  tupled(5)(stream => assert(stream.tupled5 == (1, 2, 3, 4, 5)))
  tupled(6)(stream => assert(stream.tupled6 == (1, 2, 3, 4, 5, 6)))
  tupled(7)(stream => assert(stream.tupled7 == (1, 2, 3, 4, 5, 6, 7)))
  tupled(8)(stream => assert(stream.tupled8 == (1, 2, 3, 4, 5, 6, 7, 8)))
  tupled(9)(stream => assert(stream.tupled9 == (1, 2, 3, 4, 5, 6, 7, 8, 9)))
}
