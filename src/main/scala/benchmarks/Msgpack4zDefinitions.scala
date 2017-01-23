package benchmarks

import msgpack4z._
import msgpack4z.CodecInstances.all._
import org.openjdk.jmh.annotations._

trait Msgpack4zFooInstances {

  implicit val msgpack4zFoo: MsgpackCodec[Foo] = CaseCodec.codec(Foo.apply _, Foo.unapply _)
}

trait Msgpack4zData { self: ExampleData =>
  @inline def encodeMZ[A](a: A)(implicit C: MsgpackCodec[A]): Array[Byte] =
    C.toBytes(a, new MsgpackJavaPacker())

  val foosMZ: Array[Byte] = encodeMZ(foos)
  val intsMZ: Array[Byte] = encodeMZ(ints)
}

trait Msgpack4zEncoding { self: ExampleData =>
  @Benchmark
  def encodeFoosMZ: Array[Byte] = encodeMZ(foos)

  @Benchmark
  def encodeFooMZ: Array[Byte] = encodeMZ(foo)

  @Benchmark
  def encodeIntsMZ: Array[Byte] = encodeMZ(ints)
}

trait Msgpack4zDecoding { self: ExampleData =>
  @Benchmark
  def decodeFoosMZ: Map[String, Foo] =
    MsgpackCodec[Map[String, Foo]].unpackAndClose(MsgpackJavaUnpacker.defaultUnpacker(foosMZ))
      .getOrElse(throw new Exception)

  @Benchmark
  def decodeFooMZ: Foo =
    MsgpackCodec[Foo].unpackAndClose(MsgpackJavaUnpacker.defaultUnpacker(fooMZ))
      .getOrElse(throw new Exception)

  @Benchmark
  def decodeIntsMZ: Vector[Int] =
    MsgpackCodec[Vector[Int]].unpackAndClose(MsgpackJavaUnpacker.defaultUnpacker(intsMZ))
      .getOrElse(throw new Exception)
}
