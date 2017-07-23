package code.model

import java.util.concurrent.atomic.AtomicLong

import net.liftweb.common.Box
import net.liftweb.json.JsonAST.JValue
import net.liftweb.util.Helpers

import scala.collection.concurrent.TrieMap


/**
  * Created by Riccardo Sirigu on 24/06/17.
  */
case class Todo(id: Option[Long],
                title: Option[String],
                completed: Option[Boolean],
                order: Option[Int],
                url: Option[String]
               )

object Todo extends Decomposable[Todo]{

  private val store: TrieMap[Long, Todo] = TrieMap.empty[Long, Todo]
  private val idSeq: AtomicLong = new AtomicLong()

  def apply(in: JValue): Box[Todo] = Helpers.tryo{in.extract[Todo]}

  def unapply(in: String): Option[Todo] = Helpers.asLong(in)
    .flatMap{ id =>  Todo.get(id) }

  def unapply(in: JValue): Option[Todo] = apply(in)

  def unapply(in: Any): Option[(Option[Long], Option[String], Option[Boolean], Option[Int], Option[String])] = {
    in match {
      case t: Todo => Some((t.id, t.title, t.completed, t.order, t.url))
      case _ => None
    }
  }

  def add(todo: Todo): Todo = {
    val id = idSeq.incrementAndGet()
    val url = todo.url.map{u => s"$u/todos/$id"}.getOrElse("")
    val todoEntity = todo.copy(
      id = Some(id),
      url = Some(url),
      completed = todo.completed orElse Some(false)
    )
    store.put(id, todoEntity)
    todoEntity
  }

  def update(todo: Todo): Todo = {
    todo.id.foreach{ todoId =>
      store.put(todoId, todo)
    }
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
