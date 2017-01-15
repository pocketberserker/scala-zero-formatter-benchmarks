/*

circe-benchmarks is licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

/*

port from https://github.com/circe/circe-benchmarks/blob/3fcc6c1d8d2932ea5bccb8e322dc3aca83c952ad/src/main/scala/io/circe/benchmarks/CirceDedinitions.scala

change log

  * change benchmark code
  * delete printing and parsing benchmarks

*/

package benchmarks

import io.circe._, io.circe.parser._, io.circe.syntax._
import org.openjdk.jmh.annotations._

trait CirceFooInstances {
  implicit val circeEncodeFoo: Encoder[Foo] = new Encoder[Foo] {
    def apply(foo: Foo): Json = Json.obj(
      "s" -> foo.s.asJson,
      "d" -> foo.d.asJson,
      "i" -> foo.i.asJson,
      "l" -> foo.l.asJson,
      "bs" -> foo.bs.asJson
    )
  }

  implicit val circeDecodeFoo: Decoder[Foo] = new Decoder[Foo] {
    def apply(c: HCursor): Decoder.Result[Foo] = for {
      s <- c.get[String]("s").right
      d <- c.get[Double]("d").right
      i <- c.get[Int]("i").right
      l <- c.get[Long]("l").right
      bs <- c.get[List[Boolean]]("bs").right
    } yield Foo(s, d, i, l, bs)
  }
}

trait CirceData { self: ExampleData =>
  @inline def encodeC[A](a: A)(implicit encode: Encoder[A]): Json = encode(a)

  val foosC: Json = encodeC(foos)
  val intsC: Json = encodeC(ints)
}

trait CirceEncoding { self: ExampleData =>
  @Benchmark
  def encodeFoosC: String = encodeC(foos).noSpaces

  @Benchmark
  def encodeIntsC: String = encodeC(ints).noSpaces
}

trait CirceDecoding { self: ExampleData =>
  @Benchmark
  def decodeFoosC: Map[String, Foo] = decode[Map[String, Foo]](foosJson).right.getOrElse(throw new Exception)

  @Benchmark
  def decodeIntsC: List[Int] = decode[List[Int]](intsJson).right.getOrElse(throw new Exception)
}
