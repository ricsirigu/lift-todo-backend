package test

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.webapp.WebAppContext

object JettyTestServer extends App {
  private val server: Server = {
    val svr = new Server
    val connector = new SelectChannelConnector
    connector.setMaxIdleTime(30000)

    val context = new WebAppContext
    context.setServer(svr)
    context.setContextPath("/")
    context.setWar("/src/main/webapp")

    svr.setConnectors(Array(connector))
    svr.setHandler(context)
    svr
  }

  lazy val port = server.getConnectors.head.getLocalPort
  lazy val url = "http://localhost:" + port

  def baseUrl = url

  def urlFor(path: String) = baseUrl + path

  lazy val start = server.start()

  def stop(){
    server.stop()
    server.join()
  }
}