name := """PartsInventory"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.4.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += jdbc
libraryDependencies += ws
libraryDependencies +=  "org.mybatis" % "mybatis" % "3.5.16"
libraryDependencies +=    "mysql" % "mysql-connector-java" % "8.0.33"
libraryDependencies += "org.flywaydb" % "flyway-core" % "10.15.2"
libraryDependencies += "com.typesafe.play" %% "play-jdbc" % "2.9.4" // Core JDBC support
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
