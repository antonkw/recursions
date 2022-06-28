ThisBuild / scalaVersion := "2.13.8"
ThisBuild / organization := "io.github.antonkw"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)

// https://mvnrepository.com/artifact/org.typelevel/cats-effect
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.12"

lazy val `root` =
  project
    .in(file("."))
    .settings(
      name := "recursions"
    )
