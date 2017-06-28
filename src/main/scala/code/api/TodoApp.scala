package code.api

import code.model.Todo
import code.rest.MyRestHelper
import net.liftweb.json.JsonAST.{JArray, JBool, JNothing, JValue}
import net.liftweb.util.BasicTypesHelpers.AsLong

/**
  * Created by Riccardo Sirigu on 24/06/17.
  */
object TodoApp extends MyRestHelper{

  serve{
    case Nil JsonGet _ =>
      Todo.getAll: JValue

    case Nil Options _ =>
      Todo.getAll: JValue

    case Nil JsonPost Todo(todo) -> req =>
      val url = req.hostAndPath
      val parsedTodo = todo.copy(url = Some(url))
      Todo.add(parsedTodo): JValue

    case Nil JsonDelete _ =>
      Todo.deleteAll()
      JArray(List())

    case "todos" :: Todo(todo) :: Nil JsonGet _ =>
      todo: JValue

    case "todos" :: Todo(todo) :: Nil Options _ =>
      todo: JValue

    case "todos" :: Todo(todo) :: Nil JsonPatch todoJson -> _ =>
      Todo(mergeJson(todo, todoJson)).map(Todo.update(_): JValue)

    case "todos" :: AsLong(todoId) :: Nil JsonDelete _ =>
      Todo.delete(todoId) match {
        case Some(t) => JBool(true)
        case _ => JBool(false)
      }
  }

}
