package test

import org.specs2.mutable
import org.specs2.specification.BeforeAfterAll

/**
  * Created by Riccardo Sirigu on 24/06/17.
  */

trait JettySetupAndTearDown extends BeforeAfterAll{ _: mutable.Specification =>
  def setup() = JettyTestServer.start
  def destroy() = JettyTestServer.stop()
}
