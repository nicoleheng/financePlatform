name := "financePlatform"

version := "1.0"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  cache,
  javaWs
)