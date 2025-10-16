package todolist.application.todo

import todolist.models.NoteStatus
import todolist.models.Todo
import todolist.models.TodoList

class TodoService (
    private val todoList: TodoList = TodoList()

) {
    fun getAllTodos(): List<Todo>{
        val result = todoList.listTodos()
        return result.getOrThrow()
    }

    fun addTodo(todoContent: String) = todoList.addTodo(todoContent).getOrThrow()

    fun editTodo(id: Int, newContent: String) = todoList.editTodoText(id, newContent).getOrThrow()

    fun editTodoStatus(id: Int, newStatus: NoteStatus) = todoList.editTodoStatus(id, newStatus).getOrThrow()

    fun deleteTodo(id: Int) = todoList.deleteTodo(id).getOrThrow()

}