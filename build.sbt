name := "Lift Todo Backend"

version := "0.6.0"

organization := "com.ricsirigu"

scalaVersion := "2.12.2"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
                 )

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

addCommandAlias("run", "~jetty:start")

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "3.2.0-M1"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion           % "compile",
    "org.eclipse.jetty" % "jetty-webapp"        % "9.4.6.v20170531"     % "compile",
    "org.eclipse.jetty" % "jetty-plus"          % "9.4.6.v20170531"     % "container,test", // For Jetty Config
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.1.3",
    "org.specs2"        %% "specs2-core"        % "3.9.1"               % "test",
    "net.liftweb"       %% "lift-testkit"       %  liftVersion          % "test"

  )
}

enablePlugins(JettyPlugin)

enablePlugins(JavaAppPackaging)

bashScriptConfigLocation := Some("${app_home}/../conf/jvmopts")

mainClass in Compile := Some("bootstrap.liftweb.Start")

scalacOptions in Test ++= Seq("-Yrangepos")
