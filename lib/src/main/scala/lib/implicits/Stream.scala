package lib.implicits

object Stream {
  implicit class StreamToTuple[A](stream: Stream[A]) {
    private[this] def take[B](n: Int)(f: PartialFunction[List[A], B]) = {
      f.lift(stream.take(n).toList).getOrElse(throw new IllegalArgumentException(s"$stream < $n"))
    }

    def tupled2: (A, A) = {
      take(2) {
        case a :: b :: Nil => (a, b)
      }
    }

    def tupled3: (A, A, A) = {
      take(3) {
        case a :: b :: c :: Nil => (a, b, c)
      }
    }

    def tupled4: (A, A, A, A) = {
      take(4) {
        case a :: b :: c :: d :: Nil => (a, b, c, d)
      }
    }

    def tupled5: (A, A, A, A, A) = {
      take(5) {
        case a :: b :: c :: d :: e :: Nil => (a, b, c, d, e)
      }
    }

    def tupled6: (A, A, A, A, A, A) = {
      take(6) {
        case a :: b :: c :: d :: e :: f :: Nil => (a, b, c, d, e, f)
      }
    }

    def tupled7: (A, A, A, A, A, A, A) = {
      take(7) {
        case a :: b :: c :: d :: e :: f :: g :: Nil => (a, b, c, d, e, f, g)
      }
    }

    def tupled8: (A, A, A, A, A, A, A, A) = {
      take(8) {
        case a :: b :: c :: d :: e :: f :: g :: h :: Nil => (a, b, c, d, e, f, g, h)
      }
    }

    def tupled9: (A, A, A, A, A, A, A, A, A) = {
      take(9) {
        case a :: b :: c :: d :: e :: f :: g :: h :: i :: Nil => (a, b, c, d, e, f, g, h, i)
      }
    }
  }
}
