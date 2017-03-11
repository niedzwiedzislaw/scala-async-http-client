val akkaStreamsVersion = "2.4.16"
val scalazVersion = "7.2.8"

lazy val `scala-async-http-client` = project.in(file("."))
  .settings(
    version := "1.0",
    scalaVersion := "2.12.1"
  )

libraryDependencies ++= Seq(
  "org.asynchttpclient" % "async-http-client" % "2.0.29"
)
