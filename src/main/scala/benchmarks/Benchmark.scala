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

port from https://github.com/circe/circe-benchmarks/blob/3fcc6c1d8d2932ea5bccb8e322dc3aca83c952ad/src/main/scala/io/circe/benchmarks/Benchmark.scala

change log

  * change benchmark targets
  * delete printing and parsing benchmarks
  * List to Vector

*/

package benchmarks

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._

class ExampleData extends ArgonautData with CirceData with ZeroFormatterData with Msgpack4zData with ScalaPBData {
  lazy val ints: Vector[Int] = (0 to 1000).toVector

  lazy val foos: Map[String, Foo] = List.tabulate(100) { i =>
    ("b" * i) -> Foo("a" * i, (i + 2.0) / (i + 1.0), i, i * 1000L, (0 to i).map(_ % 2 == 0).toVector)
  }.toMap

  val intsJson: String = intsC.noSpaces
  val foosJson: String = foosC.noSpaces
}

/**
 * Compare the performance of encoding operations.
 *
 * The following command will run the benchmarks with reasonable settings:
 *
 * > sbt "jmh:run -i 10 -wi 10 -f 2 -t 1 benchmarks.EncodingBenchmark"
 */
@State(Scope.Thread)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
class EncodingBenchmark extends ExampleData
  with ArgonautEncoding with CirceEncoding with ZeroFormatterEncoding with Msgpack4zEncoding with ScalaPBEncoding

/**
 * Compare the performance of decoding operations.
 *
 * The following command will run the benchmarks with reasonable settings:
 *
 * > sbt "jmh:run -i 10 -wi 10 -f 2 -t 1 benchmarks.DecodingBenchmark"
 */
@State(Scope.Thread)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
class DecodingBenchmark extends ExampleData
  with ArgonautDecoding with CirceDecoding with ZeroFormatterDecoding with Msgpack4zDecoding with ScalaPBDecoding
