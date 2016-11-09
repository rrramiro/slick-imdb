import org.scalatest.FunSuite

import scala.io.{Codec, Source}

class MovieParserTest extends FunSuite {
  test(""){
    Source.fromFile("./data/movies.partial.list" )(Codec.UTF8).getLines().foreach{ line =>
      println(MovieParser(line))
    }
  }
}
