package benchmarks

import shapeless._
import cats.Eval
import zeroformatter._
import org.openjdk.jmh.annotations._

trait ZeroFormatterFooInstances {
  implicit val zeroFormatterFoo: Formatter[Foo] = cachedImplicit
}

trait ZeroFormatterBarInstances {

  import zeroformatter.cats._

  implicit val zeroFormatterBar: Formatter[Bar] = cachedImplicit
}

trait ZeroFormatterData { self: ExampleData =>
  @inline def encodeZ[A](a: A)(implicit F: Formatter[A]): Array[Byte] =
    ZeroFormatter.serialize(a)

  lazy val bar: Bar = Bar(
    Eval.now("a" * 100),
    Eval.now(102.0 / 101.0),
    Eval.now(100),
    Eval.now(100000L),
    Eval.now((0 to 100).map(_ % 2 == 0).toVector)
  )

  val barZ: Array[Byte] = encodeZ(bar)
  val fooZ: Array[Byte] = encodeZ(foo)
  val foosZ: Array[Byte] = encodeZ(foos)
  val intsZ: Array[Byte] = encodeZ(ints)
}

trait ZeroFormatterEncoding { self: ExampleData =>
  @Benchmark
  def encodeFoosZ: Array[Byte] = encodeZ(foos)

  @Benchmark
  def encodeFooZ: Array[Byte] = encodeZ(foo)

  @Benchmark
  def encodeBarZ: Array[Byte] = encodeZ(bar)

  @Benchmark
  def encodeIntsZ: Array[Byte] = encodeZ(ints)
}

trait ZeroFormatterDecoding { self: ExampleData =>
  @Benchmark
  def decodeFoosZ: Map[String, Foo] = ZeroFormatter.deserialize[Map[String, Foo]](foosZ)

  @Benchmark
  def decodeFooZ: Foo = ZeroFormatter.deserialize[Foo](fooZ)

  @Benchmark
  def decodeBarZ: Bar = ZeroFormatter.deserialize[Bar](barZ)

  @Benchmark
  def decodeIntsZ: Vector[Int] = ZeroFormatter.deserialize[Vector[Int]](intsZ)
}
