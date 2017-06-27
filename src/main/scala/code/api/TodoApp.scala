package code.api

import code.model.Todo
import code.rest.MyRestHelper
import net.liftweb.json.JsonAST.{JBool, JNothing, JValue}

/**
  * Created by Riccardo Sirigu on 24/06/17.
  */
object TodoApp extends MyRestHelper{

  serve{
    case Nil JsonGet _ =>
      Todo.getAll: JValue

    case Nil Options _ =>
      Todo.getAll: JValue

    case Nil JsonPost Todo(todo) -> _ =>
      Todo.add(todo): JValue

    case Nil JsonDelete _ =>
      Todo.deleteAll()
      JNothing

    case "todos" :: Todo(todo) :: Nil JsonGet _ =>
      todo: JValue

    case "todos" :: Todo(todo) :: Nil JsonPatch todoJson -> _ =>
      Todo(mergeJson(todo, todoJson)).map(Todo.add(_): JValue)

    case "todos" :: Todo(todo) :: Nil JsonDelete _ =>
      Todo.delete(todo.id) match {
        case Some(t) => JBool(true)
        case _ => JBool(false)
      }
  }

}
