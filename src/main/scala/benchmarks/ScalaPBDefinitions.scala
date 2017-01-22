package benchmarks

import org.openjdk.jmh.annotations._

trait ScalaPBData { self: ExampleData =>

  lazy val fooPBValue =
    test.Foo("a" * 100, 102.0 / 101.0, 100, 100000L, (0 to 100).map(_ % 2 == 0).toVector)

  lazy val intsPBValue = test.Ints((0 to 1000).toVector)

  val fooPB: Array[Byte] = fooPBValue.toByteArray
  val intsPB: Array[Byte] = intsPBValue.toByteArray
}

trait ScalaPBEncoding { self: ExampleData =>
  @Benchmark
  def encodeFooPB: Array[Byte] = fooPBValue.toByteArray

  @Benchmark
  def encodeIntsPB: Array[Byte] = intsPBValue.toByteArray
}

trait ScalaPBDecoding { self: ExampleData =>
  @Benchmark
  def decodeFooPB: test.Foo = test.Foo.parseFrom(fooPB)

  @Benchmark
  def decodeIntsPB: test.Ints = test.Ints.parseFrom(intsPB)
}
