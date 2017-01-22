package benchmarks

import zeroformatter._

case class Foo(
  @Index(0) s: String,
  @Index(1) d: Double,
  @Index(2) i: Int,
  @Index(3) l: Long,
  @Index(4) bs: Vector[Boolean]
)

object Foo extends ArgonautFooInstances with CirceFooInstances with ZeroFormatterFooInstances with Msgpack4zFooInstances {
}
