ThisBuild / organization := "com.github.marcinzh"
ThisBuild / version := "1.0.0"
ThisBuild / scalaVersion := "3.2.0"

ThisBuild / watchBeforeCommand := Watch.clearScreen
ThisBuild / watchTriggeredMessage := Watch.clearScreenOnTrigger
ThisBuild / watchForceTriggerOnAnyChange := true

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Ykind-projector:underscores",
)

val Deps = {
  object deps {
    val scalatest = "org.scalatest" %% "scalatest" % "3.2.10" % "test"
    val catsCore = "org.typelevel" %% "cats-core" % "2.6.1"
    val catsMtl = "org.typelevel" %% "cats-mtl" % "1.2.1"
    val catsEff = "org.atnos" %% "eff" % "5.18.0"
    val zio = "dev.zio" %% "zio" % "1.0.16"
    val zioPrelude = "dev.zio" %% "zio-prelude" % "1.0.0-RC5"
    val turbolift = "io.github.marcinzh" %% "turbolift-core" % "0.27.0"
    val betterFiles = ("com.github.pathikrit" %% "better-files" % "3.9.1").cross(CrossVersion.for3Use2_13)
  }
  deps
}


lazy val root = project
  .in(file("."))
  .settings(sourcesInBase := false)
  .aggregate(core, chart, diy, meta, bench)

lazy val core = project
  .in(file("modules/core"))
  .settings(name := "effect-zoo-core")
  .settings(libraryDependencies ++= Seq(
    Deps.scalatest,
    Deps.catsCore,
    Deps.catsMtl,
    Deps.catsEff,
    Deps.turbolift,
    Deps.zio,
    Deps.zioPrelude,
  ))

lazy val chart = project
  .in(file("modules/chart"))
  .settings(name := "effect-zoo-chart")

lazy val diy = project
  .in(file("modules/diy"))
  .settings(name := "effect-zoo-diy")
  .settings(run / fork := true)
  .settings(javaOptions += "-Xmx2G")
  .dependsOn(core, chart)

lazy val meta = project
  .in(file("modules/meta"))
  .settings(name := "effect-zoo-meta")
  .settings(libraryDependencies += Deps.betterFiles)
  .dependsOn(core)

lazy val bench = project
  .in(file("modules/bench"))
  .settings(name := "effect-zoo-bench")
  .dependsOn(core, chart)
  .enablePlugins(JmhPlugin)
  .settings(Jmh / run / mainClass := Some("effect_zoo.bench.Main"))


addCommandAlias("runbench", "bench/Jmh/run -i 3 -wi 3 -f1 -t1 -r 5 -w 5")
addCommandAlias("runbench1", "bench/Jmh/run -i 3 -wi 3 -f1 -t1 -r 1 -w 1")
addCommandAlias("runbench0", "bench/Jmh/run -i 1 -wi 1 -f1 -t1 -r 1 -w 1")
