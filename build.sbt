name := "Lift Todo Backend"

version := "0.6.0"

organization := "com.ricsirigu"

scalaVersion := "2.12.2"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
                 )

seq(webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

addCommandAlias("cs", "~container:start")

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "3.1.0-RC1"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion           % "compile",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.17.v20150415"    % "container,test",
    "org.eclipse.jetty" % "jetty-plus"          % "8.1.17.v20150415"    % "container,test", // For Jetty Config
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.1.3",
    "org.specs2"        %% "specs2-core"        % "3.9.1"               % "test",
    "net.liftweb"       %% "lift-testkit"       %  liftVersion          % "test"

  )
}

scalacOptions in Test ++= Seq("-Yrangepos")
