addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.2.20")
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.6")

libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.6.0-pre2"

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-Xlint" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  "-Yno-adapted-args" ::
  Nil
)
