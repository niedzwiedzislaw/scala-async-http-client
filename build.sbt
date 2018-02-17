lazy val `scala-async-http-client` = project.in(file("."))
  .settings(
    version := "1.0",
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq("2.12.4", "2.11.11", "2.10.6")
  )

libraryDependencies ++= Seq(
  "org.asynchttpclient" % "async-http-client" % "2.4.1"
)
