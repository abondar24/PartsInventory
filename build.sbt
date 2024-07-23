name := """PartsInventory"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.4.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += jdbc
libraryDependencies += ws
libraryDependencies += "org.mybatis" % "mybatis" % "3.5.16"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.33"
libraryDependencies += "org.flywaydb" % "flyway-mysql" % "10.16.0"
libraryDependencies += "com.typesafe.play" %% "play-jdbc" % "2.9.4" // Core JDBC support
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.17.1"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.17.1"
libraryDependencies += "org.flywaydb" %% "flyway-play" % "9.1.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
