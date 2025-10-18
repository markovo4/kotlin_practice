package todolist.models

import todolist.models.exceptions.TodoInvalidContentException
import todolist.models.exceptions.TodoListNoContentException
import todolist.models.exceptions.TodoNotFoundException

class TodoList(
    private val todoList: MutableList<Todo> = mutableListOf()
) {
    fun editTodoText(id: Int, newTodoText: String): Result<Unit>{

        val todo = todoList.find { todo -> todo.id == id } ?: return Result.failure(TodoNotFoundException(id))

        if(todoList.isEmpty()) return Result.failure(TodoListNoContentException())

        if(newTodoText.isBlank()) return Result.failure(TodoInvalidContentException())

        todo.edit(newTodoText)
        return Result.success(Unit)
    }

    fun addTodo(todoContent: String, userId: Int, id: Int): Result<Unit> {
        if(todoContent.isBlank()) return Result.failure(TodoInvalidContentException())

        val todo = Todo(todoContent, userId, id)
        todoList.add(todo)
        return Result.success(Unit)
    }

    fun editTodoStatus(id: Int, newStatus: NoteStatus): Result<Unit> {
        val todo = todoList.find {todo -> todo.id == id } ?: return Result.failure(TodoNotFoundException(id))

        todo.changeStatus(newStatus)
        return Result.success(Unit)
    }

    fun deleteTodo(id: Int): Result<Unit> {
        val todo = todoList.find {todo -> todo.id == id } ?: return Result.failure(TodoNotFoundException(id))
        todoList.remove(todo)
        return Result.success(Unit)
    }

    fun listTodos(): Result<List<Todo>> {
        if(todoList.isEmpty()) return Result.failure(TodoListNoContentException())

        return Result.success(todoList.toList())
    }

    fun clearAndAddAll(newTodos: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodos)
    }

}