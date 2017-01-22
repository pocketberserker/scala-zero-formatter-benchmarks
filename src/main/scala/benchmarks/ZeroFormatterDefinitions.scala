package benchmarks

import shapeless._
import zeroformatter._
import org.openjdk.jmh.annotations._

trait ZeroFormatterFooInstances {
  implicit val zeroFormatterFoo: Formatter[Foo] = cachedImplicit
}

trait ZeroFormatterData { self: ExampleData =>
  @inline def encodeZ[A](a: A)(implicit F: Formatter[A]): Array[Byte] =
    ZeroFormatter.serialize(a)

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
  def encodeIntsZ: Array[Byte] = encodeZ(ints)
}

trait ZeroFormatterDecoding { self: ExampleData =>
  @Benchmark
  def decodeFoosZ: Map[String, Foo] = ZeroFormatter.deserialize[Map[String, Foo]](foosZ)

  @Benchmark
  def decodeFooZ: Foo = ZeroFormatter.deserialize[Foo](fooZ)

  @Benchmark
  def decodeIntsZ: Vector[Int] = ZeroFormatter.deserialize[Vector[Int]](intsZ)
}
