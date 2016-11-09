import shapeless._
import syntax.std.traversable._

object MovieParser {
  def apply(line: String): Option[(Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String])] = {
    val baseMatcherPattern = """(.*?) (\(\S{4,}\))\s?(\(.+\))?\s?(\{(.*?)(\(.+?\))\})?\s*(\{\{SUSPENDED\}\})?\s*(.*$)""".r
    baseMatcherPattern.findFirstMatchIn(line).map {
      matcher =>
        (for( i <- 1 to 8) yield Option(matcher.group(i))).toHList[Option[String]::Option[String]::Option[String]::Option[String]::Option[String]::Option[String]::Option[String]::Option[String]::HNil].get.tupled
    }
  }
}
