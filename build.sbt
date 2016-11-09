
name := "slick-imdb"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.h2database" % "h2" % "1.3.170",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)