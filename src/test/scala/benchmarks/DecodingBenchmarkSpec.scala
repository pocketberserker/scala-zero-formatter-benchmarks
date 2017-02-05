package benchmarks

import org.scalatest.FlatSpec

class DecodingBenchmarkSpec extends FlatSpec {
  val benchmark: DecodingBenchmark = new DecodingBenchmark

  import benchmark._

  it should "correctly decode integers using ZeroFormatter" in {
    assert(decodeIntsZ === ints)
  }

  it should "correctly decode integers using msgpack4z" in {
    assert(decodeIntsMZ === ints)
  }

  it should "correctly decode case classes using ZeroFormatter" in {
    assert(decodeFoosZ === foos)
  }

  it should "correctly decode case classes using msgpack4z" in {
    assert(decodeFoosMZ === foos)
  }
}
