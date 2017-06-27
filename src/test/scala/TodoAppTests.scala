package test

import net.liftweb.json.JsonAST.JArray
import net.liftweb.mocks.MockHttpServletRequest
import net.liftweb.mockweb.MockWeb
import org.specs2.mutable.Specification

/**
  * Created by Riccardo Sirigu on 24/06/17.
  *
  * TODO
  */
object TodoAppTests extends Specification with JettySetupAndTearDown{

  val testUrl = "http://localhost:8080/"

  val mockReq = new MockHttpServletRequest()

  mockReq.method = "GET"
  mockReq.contentType = "application/json"
  mockReq.path = testUrl

  "Get Todos when no Todos are present" >> {
    "Return an empty json array" >> {
      MockWeb.testReq(mockReq){ resp =>
        resp.forcedBodyAsJson.map(_ == JArray(Nil)).getOrElse(false)
      }
    }
  }

  override def beforeAll(): Unit = setup()

  override def afterAll(): Unit = destroy()
}
