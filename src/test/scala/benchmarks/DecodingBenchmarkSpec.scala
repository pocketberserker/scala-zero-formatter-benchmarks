package benchmarks

import org.scalatest.FlatSpec

class DecodingBenchmarkSpec extends FlatSpec {
  val benchmark: DecodingBenchmark = new DecodingBenchmark

  import benchmark._

  "The decoding benchmark" should "correctly decode integers using Circe" in {
    assert(decodeIntsC === ints)
  }

  it should "correctly decode integers using Argonaut" in {
    assert(decodeIntsA === ints)
  }

  it should "correctly decode integers using ZeroFormatter" in {
    assert(decodeIntsZ === ints)
  }

  it should "correctly decode integers using msgpack4z" in {
    assert(decodeIntsMZ === ints)
  }

  it should "correctly decode case classes using Circe" in {
    assert(decodeFoosC === foos)
  }

  it should "correctly decode case classes using Argonaut" in {
    assert(decodeFoosA === foos)
  }

  it should "correctly decode case classes using ZeroFormatter" in {
    assert(decodeFoosZ === foos)
  }

  it should "correctly decode case classes using msgpack4z" in {
    assert(decodeFoosMZ === foos)
  }
}
