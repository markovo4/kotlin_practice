package todolist.todolist.presentation.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import todolist.application.todo.TodoService
import todolist.models.Todo
import todolist.todolist.presentation.dto.EditStatusRequest
import todolist.todolist.presentation.dto.EditTodoContentRequest


fun Route.todoRoutes(service: TodoService){
    route("/todos"){

        get{
            val allTodos = service.getAllTodos().toList()
            call.respond(allTodos)
        }

        get("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val todo = service.getTodoById(id)
            call.respond(HttpStatusCode.OK, todo)
        }

        put("/edit/status/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<EditStatusRequest>()

            val updated = service.editTodoStatus(id, request.status)

            call.respond(HttpStatusCode.OK, updated)
        }


        put("/edit/content/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<EditTodoContentRequest>()

            val updatedTodo = service.editTodo(id, request.content)

            return@put call.respond(HttpStatusCode.OK, updatedTodo)
        }

        delete("/delete/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            service.deleteTodo(id)
        }

        post("/create") {
            val newTodo = call.receive<Todo>()

            service.addTodo(newTodo.content)
        }

    }

}