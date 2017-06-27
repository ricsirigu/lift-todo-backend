package code.rest

import net.liftweb.common.Box
import net.liftweb.http.Req
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST.JValue

/**
  * Created by Riccardo Sirigu on 24/06/17.
  *
  * Needed because Lift doesn't implement PATCH method yet
  */
trait MyRestHelper extends RestHelper{
  /**
    * A trait that defines the TestPatch extractor.  Is
    * the request a POST, has JSON or XML data in the post body
    * and something that expects JSON or XML in the response.
    * Subclass this trait to change the behavior
    */
  protected trait TestPatch[T] {
    /**
      * Test to see if the request is a PATCH, has JSON data in the
      * body and expecting JSON in the response.
      * The path, JSON Data and the Req instance are extracted.
      */
    def unapply(r: Req): Option[(List[String], (T, Req))] =
      if (r.request.method == "PATCH" && testResponse_?(r))
        body(r).toOption.map(t => (r.path.partPath -> (t -> r)))
      else None


    def testResponse_?(r: Req): Boolean

    def body(r: Req): Box[T]
  }

  /**
    * The stable identifier for JsonPatch.  You can use it
    * as an extractor.
    */
  protected lazy val JsonPatch = new TestPatch[JValue] with JsonTest with JsonBody

}
