package code.model

import net.liftweb.json.{DefaultFormats, Extraction}
import net.liftweb.json.JsonAST.JValue

/**
  * Created by riccardo on 25/06/17.
  */
trait Decomposable[T] {

  implicit val formats = DefaultFormats

  implicit def toJson(item: T): JValue =
    Extraction.decompose(item)

  implicit def toJson(items: Seq[T]): JValue =
    Extraction.decompose(items)
}
