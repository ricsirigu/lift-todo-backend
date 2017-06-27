package code.model

import net.liftweb.common.Box
import net.liftweb.json.JsonAST.JValue
import net.liftweb.util.Helpers

import scala.collection.concurrent.TrieMap


/**
  * Created by Riccardo Sirigu on 24/06/17.
  */
case class Todo(id: Long, title: String, completed: Boolean, uri: String, order: Option[Int])

object Todo extends Decomposable[Todo]{

  private val store: TrieMap[Long, Todo] = TrieMap.empty[Long, Todo]

  def apply(in: JValue): Box[Todo] = Helpers.tryo{in.extract[Todo]}

  def unapply(in: String): Option[Todo] = Helpers.asLong(in)
    .flatMap{ id =>  Todo.get(id) }

  def unapply(in: JValue): Option[Todo] = apply(in)

  def unapply(in: Any): Option[(Long, String, Boolean, String, Option[Int])] = {
    in match {
      case t: Todo => Some((t.id, t.title, t.completed, t.uri, t.order))
      case _ => None
    }
  }

  def add(todo: Todo): Todo = {
    store.put(todo.id, todo)
    todo
  }

  def getAll: Seq[Todo] = {
    store.values.toSeq
  }

  def deleteAll(): Unit = {
    store.clear()
  }

  def get(id: Long): Option[Todo] = {
    store.get(id)
  }

  def delete(id: Long): Option[Todo] = {
    store.remove(id)
  }

}