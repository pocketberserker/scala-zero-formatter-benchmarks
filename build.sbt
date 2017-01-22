scalaVersion := "2.12.1"

val compilerOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-unchecked",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Ywarn-unused-import"
)

val baseSettings = Seq(
  scalacOptions ++= compilerOptions,
  scalacOptions in (Compile, console) ~= {
    _.filterNot(Set("-Ywarn-unused-import"))
  },
  scalacOptions in (Test, console) ~= {
    _.filterNot(Set("-Ywarn-unused-import"))
  },
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases")
  )
)

lazy val benchmark = project.in(file("."))
  .settings(baseSettings)
  .settings(
    PB.targets in Compile := Seq(
      scalapb.gen(grpc=false) -> (sourceManaged in Compile).value
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.7.0",
      "io.circe" %% "circe-parser" % "0.7.0",
      "io.argonaut" %% "argonaut" % "6.2-RC2",
      "com.github.pocketberserker" %% "zero-formatter-cats-core" % "0.2.0",
      "com.github.xuwei-k" %% "msgpack4z-core" % "0.3.5",
      "com.github.xuwei-k" %% "msgpack4z-native" % "0.3.1",
      "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf"
    )
  ).enablePlugins(JmhPlugin)
