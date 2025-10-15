package todolist.models

import todolist.domain.models.NoteStatus
import todolist.models.exceptions.TodoInvalidContentException
import todolist.models.exceptions.TodoListNoContentException
import todolist.models.exceptions.TodoNotFoundException

class TodoList(
    private val todos: MutableList<Todo> = mutableListOf()
) {

    fun editTodoText(id: Int, newTodoText: String): Result<Unit>{
        val todo = todos.find { todo -> todo.id == id } ?: return Result.failure(TodoNotFoundException(id))

        if(todos.isEmpty()) return Result.failure(TodoListNoContentException())

        if(newTodoText.isBlank()) return Result.failure(TodoInvalidContentException())

        todo.edit(newTodoText)
        return Result.success(Unit)
    }


    fun addTodo(todoContent: String): Result<Unit> {
        if(todoContent.isBlank()) return Result.failure(TodoInvalidContentException())

        val todo = Todo(todoContent)
        todos.add(todo)
        return Result.success(Unit)
    }

    fun editTodoStatus(id: Int, newStatus: NoteStatus): Result<Unit> {
        val todo = todos.find {todo -> todo.id == id } ?: return Result.failure(TodoNotFoundException(id))

        todo.changeStatus(newStatus)
        return Result.success(Unit)
    }

    fun deleteTodo(id: Int): Result<Unit> {
        val todo = todos.find {todo -> todo.id == id } ?: return Result.failure(TodoNotFoundException(id))
        todos.remove(todo)
        return Result.success(Unit)
    }

    fun listTodos(): Result<List<Todo>> {
        if(todos.isEmpty()) return Result.failure(TodoListNoContentException())

        return Result.success(todos.toList())
    }
}