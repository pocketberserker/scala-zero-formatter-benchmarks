package benchmarks

import msgpack4z._
import msgpack4z.CodecInstances.all._
import org.openjdk.jmh.annotations._

trait Msgpack4zFooInstances {
  val factory = new PackerUnpackerFactory {
    def packer = MsgOutBuffer.create()
    def unpacker(bytes: Array[Byte]) = MsgInBuffer(bytes)
  }

  val mapCodec = CaseMapCodec.string(factory)

  implicit val msgpack4zFoo = mapCodec.codec(Foo.apply _, Foo.unapply _)("s", "d", "i", "l", "bs")
}

trait Msgpack4zData { self: ExampleData =>
  @inline def encodeMZ[A](a: A)(implicit C: MsgpackCodec[A]): Array[Byte] =
    C.toBytes(a, MsgOutBuffer.create())

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
    MsgpackCodec[Map[String, Foo]].unpackAndClose(MsgInBuffer(foosMZ))
      .getOrElse(throw new Exception)

  @Benchmark
  def decodeIntsMZ: List[Int] =
    MsgpackCodec[List[Int]].unpackAndClose(MsgInBuffer(intsMZ))
      .getOrElse(throw new Exception)
}
