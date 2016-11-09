
object MovieParser {
  def apply(line: String): Option[(String, String, String, String, String, String, String, String)] = {
    val baseMatcherPattern = """(.*?) (\(\S{4,}\))\s?(\(.+\))?\s?(\{(.*?)(\(.+?\))\})?\s*(\{\{SUSPENDED\}\})?\s*(.*$)""".r
    baseMatcherPattern.findFirstMatchIn(line).map {
      matcher =>
        (matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8))
    }
  }
}
