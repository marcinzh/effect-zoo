ThisBuild / organization := "com.github.marcinzh"
ThisBuild / version := "1.0.0"
ThisBuild / scalaVersion := "3.4.1"

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
    val scalatest = "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    val catsCore = "org.typelevel" %% "cats-core" % "2.12.0"
    val catsMtl = "org.typelevel" %% "cats-mtl" % "1.4.0"
    val catsEff = "org.atnos" %% "eff" % "7.0.4"
    val catsEffect = "org.typelevel" %% "cats-effect" % "3.5.4"
    val zio = "dev.zio" %% "zio" % "2.1-RC1"
    val zioPrelude = "dev.zio" %% "zio-prelude" % "1.0.0-RC27"
    val turbolift = "io.github.marcinzh" %% "turbolift-core" % "0.89.0-SNAPSHOT"
    val betterFiles = ("com.github.pathikrit" %% "better-files" % "3.9.2").cross(CrossVersion.for3Use2_13)
    val kyo = "io.getkyo" %% "kyo-core" % "0.10.2"
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
    Deps.catsEffect,
    Deps.turbolift,
    Deps.zio,
    Deps.zioPrelude,
    Deps.kyo,
  ))

lazy val chart = project
  .in(file("modules/chart"))
  .settings(name := "effect-zoo-chart")

lazy val diy = project
  .in(file("modules/diy"))
  .settings(name := "effect-zoo-diy")
  .settings(run / fork := true)
  .settings(javaOptions ++= Seq("-Xms2g", "-Xmx2g"))
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


addCommandAlias("runbench", "bench/Jmh/run -i 10 -wi 10 -f1 -t1 -r 1 -w 1")
addCommandAlias("runbench3", "bench/Jmh/run -i 5 -wi 3 -f1 -t1 -r 3 -w 3")
addCommandAlias("runbench10", "bench/Jmh/run -i 5 -wi 3 -f1 -t1 -r 10 -w 10")
addCommandAlias("runbench1", "bench/Jmh/run -i 1 -wi 1 -f1 -t1 -r 1 -w 1")
