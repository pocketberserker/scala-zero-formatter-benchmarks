package benchmarks

import org.openjdk.jmh.annotations._

trait ScalaPBData { self: ExampleData =>

  //val foosPBValue =
  //  test.Foos(foos.mapValues(v => test.Foo(v.s, v.d, v.i, v.l, v.bs)))

  val intsPBValue = test.Ints(ints)

  //val foosPB: Array[Byte] = foosPBValue.toByteArray
  val intsPB: Array[Byte] = intsPBValue.toByteArray
}

trait ScalaPBEncoding { self: ExampleData =>
  //@Benchmark
  //def encodeFoosPB: Array[Byte] = foosPBValue.toByteArray

  @Benchmark
  def encodeIntsPB: Array[Byte] = intsPBValue.toByteArray
}

trait ScalaPBDecoding { self: ExampleData =>
  //@Benchmark
  //def decodeFoosPB: test.Foos = test.Foos.parseFrom(foosPB)

  @Benchmark
  def decodeIntsPB: test.Ints = test.Ints.parseFrom(intsPB)
}
