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

port from https://github.com/circe/circe-benchmarks/blob/3fcc6c1d8d2932ea5bccb8e322dc3aca83c952ad/src/main/scala/io/circe/benchmarks/ArgonautDedinitions.scala

change log

  * change benchmark code
  * delete printing and parsing benchmarks

*/

package benchmarks

import argonaut._, Argonaut._
import org.openjdk.jmh.annotations._

trait ArgonautFooInstances {
  implicit val argonautCodecFoo: CodecJson[Foo] = CodecJson(
    {
      case Foo(s, d, i, l, bs) =>
        ("s" := s) ->: ("d" := d) ->: ("i" := i) ->: ("l" := l) ->: ("bs" := bs) ->: jEmptyObject
    },
    c => for {
      s  <- (c --\ "s").as[String]
      d  <- (c --\ "d").as[Double]
      i  <- (c --\ "i").as[Int]
      l  <- (c --\ "l").as[Long]
      bs <- (c --\ "bs").as[List[Boolean]]
    } yield Foo(s, d, i, l, bs)
  )
}

trait ArgonautData { self: ExampleData =>
  @inline def encodeA[A](a: A)(implicit encode: EncodeJson[A]): Json = encode(a)

  val foosA: Json = encodeA(foos)
  val intsA: Json = encodeA(ints)
}

trait ArgonautEncoding { self: ExampleData =>
  @Benchmark
  def encodeFoosA: String = encodeA(foos).nospaces

  @Benchmark
  def encodeIntsA: String = encodeA(ints).nospaces
}

trait ArgonautDecoding { self: ExampleData =>
  @Benchmark
  def decodeFoosA: Map[String, Foo] = foosJson.decodeOption[Map[String, Foo]].getOrElse(throw new Exception)

  @Benchmark
  def decodeIntsA: List[Int] = intsJson.decodeOption[List[Int]].getOrElse(throw new Exception)
}
