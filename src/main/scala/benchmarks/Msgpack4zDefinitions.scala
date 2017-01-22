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

  @inline def decodeMZ[A](a: Array[Byte])(implicit C: MsgpackCodec[A]): A =
    C.unpackAndClose(MsgpackJavaUnpacker.defaultUnpacker(a))
      .getOrElse(throw new Exception)

  val foosMZ: Array[Byte] = encodeMZ(foos)
  val intsMZ: Array[Byte] = encodeMZ(ints)
}

trait Msgpack4zEncoding { self: ExampleData =>
  @Benchmark
  def encodeFoosMZ: Array[Byte] = encodeMZ(foos)

  @Benchmark
  def encodeIntsMZ: Array[Byte] = encodeMZ(ints)
}

trait Msgpack4zDecoding { self: ExampleData =>
  @Benchmark
  def decodeFoosMZ: Map[String, Foo] =
    MsgpackCodec[Map[String, Foo]].unpackAndClose(MsgpackJavaUnpacker.defaultUnpacker(foosMZ))
      .getOrElse(throw new Exception)

  @Benchmark
  def decodeIntsMZ: List[Int] =
    MsgpackCodec[List[Int]].unpackAndClose(MsgpackJavaUnpacker.defaultUnpacker(intsMZ))
      .getOrElse(throw new Exception)
}
