organization := "grupo1.dragonball.tadp"

name := "dragonball"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
    "Typesafe" at "http://repo.typesafe.com",
    "Maven Central Repo" at "http://search.maven.org"
    )

libraryDependencies ++= List(
        "org.scalatest" %% "scalatest" % "2.2.4" % "test",
        "junit" % "junit" % "4.12"
        )
        
EclipseKeys.withSource := true
