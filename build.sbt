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

val zeroFormatterVersion = "0.7.0"

lazy val benchmark = project.in(file("."))
  .settings(baseSettings)
  .settings(
    PB.targets in Compile := Seq(
      scalapb.gen(grpc=false) -> (sourceManaged in Compile).value
    ),
    libraryDependencies ++= Seq(
      "com.github.pocketberserker" %% "zero-formatter-cats-core" % zeroFormatterVersion,
      "com.github.pocketberserker" %% "zero-formatter-unsafe" % zeroFormatterVersion,
      "com.github.xuwei-k" %% "msgpack4z-core" % "0.3.5",
      "com.github.xuwei-k" % "msgpack4z-java" % "0.3.5",
      "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  ).enablePlugins(JmhPlugin)
