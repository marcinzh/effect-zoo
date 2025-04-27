ThisBuild / organization := "com.github.marcinzh"
ThisBuild / version := "1.0.0"
ThisBuild / scalaVersion := "3.6.4"

ThisBuild / watchBeforeCommand := Watch.clearScreen
ThisBuild / watchTriggeredMessage := Watch.clearScreenOnTrigger
ThisBuild / watchForceTriggerOnAnyChange := true

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xkind-projector:underscores",
  "-language:experimental.betterFors"
)

val Deps = {
  val v_kyo = "0.17.0"
  val v_tur = "0.114.0"
  object deps {
    val scalatest = "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    val catsCore = "org.typelevel" %% "cats-core" % "2.13.0"
    val catsMtl = "org.typelevel" %% "cats-mtl" % "1.5.0"
    val catsEff = "org.atnos" %% "eff" % "7.0.5"
    val catsEffect = "org.typelevel" %% "cats-effect" % "3.5.7"
    val zio = "dev.zio" %% "zio" % "2.1-RC1"
    val zioPrelude = "dev.zio" %% "zio-prelude" % "1.0.0-RC39"
    val turbolift = "io.github.marcinzh" %% "turbolift-core" % v_tur
    val turbolift_bindless =
      "io.github.marcinzh" %% "turbolift-bindless" % v_tur
    val betterFiles = ("com.github.pathikrit" %% "better-files" % "3.9.2")
      .cross(CrossVersion.for3Use2_13)
    val kyo = "io.getkyo" %% "kyo-core" % v_kyo
    val kyo_direct = "io.getkyo" %% "kyo-direct" % v_kyo
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
  .settings(
    libraryDependencies ++= Seq(
      Deps.scalatest,
      Deps.catsCore,
      Deps.catsMtl,
      Deps.catsEff,
      Deps.catsEffect,
      Deps.turbolift,
      Deps.turbolift_bindless,
      Deps.zio,
      Deps.zioPrelude,
      Deps.kyo,
      Deps.kyo_direct
    )
  )

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
