ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version := "0.1.0-SNAPSHOT"

Compile / run / fork := true
Compile / run / javaOptions += "-XstartOnFirstThread"

lazy val lwjglVersion = "3.3.3"

libraryDependencies ++= Seq(
  "org.lwjgl" % "lwjgl" % lwjglVersion,
  "org.lwjgl" % "lwjgl-glfw" % lwjglVersion,
  "org.lwjgl" % "lwjgl-opengl" % lwjglVersion,
  "org.lwjgl" % "lwjgl-stb" % lwjglVersion, // optional (for text/images)
  "org.lwjgl" % "lwjgl" % lwjglVersion classifier "natives-macos-arm64",
  "org.lwjgl" % "lwjgl-glfw" % lwjglVersion classifier "natives-macos-arm64",
  "org.lwjgl" % "lwjgl-opengl" % lwjglVersion classifier "natives-macos-arm64",
  "org.lwjgl" % "lwjgl-stb" % lwjglVersion classifier "natives-macos-arm64",
"org.joml" % "joml" % "1.10.5"
)