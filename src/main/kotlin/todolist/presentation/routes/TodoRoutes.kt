package todolist.todolist.presentation.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import todolist.application.todo.TodoService
import todolist.models.Todo
import todolist.todolist.application.auth.withSession
import todolist.todolist.presentation.dto.EditStatusRequest
import todolist.todolist.presentation.dto.EditTodoContentRequest


fun Route.todoRoutes() {

    route("/todos"){
        get{
            call.withSession { session ->
                val service = TodoService(session)
                val todos = service.getAllTodos()
                call.respond(HttpStatusCode.OK, todos)
            }
        }

        get("/{id}"){
            call.withSession { session ->
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@withSession call.respond(HttpStatusCode.BadRequest, "Invalid ID")

                val service = TodoService(session)
                val todo = service.getTodoById(id)
                call.respond(HttpStatusCode.OK, todo)
            }
        }

        put("/edit/status/{id}") {
            call.withSession { session ->
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@withSession call.respond(HttpStatusCode.BadRequest, "Invalid ID")

                val service = TodoService(session)
                val request = call.receive<EditStatusRequest>()

                val updated = service.editTodoStatus(id, request.status)
                call.respond(HttpStatusCode.OK, updated)
            }
        }


        put("/edit/content/{id}") {
            call.withSession { session ->
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@withSession call.respond(HttpStatusCode.BadRequest, "Invalid ID")

                val service = TodoService(session)

                val request = call.receive<EditTodoContentRequest>()
                val updatedTodo = service.editTodo(id, request.content)
                call.respond(HttpStatusCode.OK, updatedTodo)
            }
        }

        delete("/delete/{id}"){
            call.withSession { session ->
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@withSession call.respond(HttpStatusCode.BadRequest, "Invalid ID")

                val service = TodoService(session)
                service.deleteTodo(id)
                call.respond(HttpStatusCode.OK, "Todo deleted successfully")
            }

        }

        post("/create") {
            call.withSession { session ->
                val newTodo = call.receive<Todo>()

                val service = TodoService(session)
                service.addTodo(newTodo.content)
                call.respond(HttpStatusCode.Created, "Todo created successfully")
            }

        }

    }

}