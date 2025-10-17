package todolist.application.todo

import todolist.application.auth.UserSession
import todolist.models.*
import todolist.models.exceptions.TodoNotFoundException
import todolist.repository.DbTodoRepository

class TodoService (
    private val todoRepository: DbTodoRepository,
    private val session: UserSession,
    private val todoList: TodoList = TodoList(),
) {
    fun getAllTodos(): List<Todo>{
        val result = todoList.listTodos()
        return result.getOrElse {
            val todos = todoRepository.findAll(session.userId)
            todoList.clearAndAddAll(todos)
            todos
        }
    }

    fun addTodo(todoContent: String) {
        val localResult = todoList.addTodo(todoContent, session.userId)
        localResult.getOrThrow()

        val newTodo = Todo(content = todoContent)
        todoRepository.add(newTodo, session.userId)

    }

    fun editTodo(id: Int, newContent: String) {
        todoList.editTodoText(id, newContent).getOrThrow()

        val todo = todoRepository.findById(id) ?: throw TodoNotFoundException(id)
        todoRepository.update(todo)
    }

    fun editTodoStatus(id: Int, newStatus: NoteStatus) {
        todoList.editTodoStatus(id, newStatus).getOrThrow()

        val todo = todoRepository.findById(id) ?: throw TodoNotFoundException(id)
        todoRepository.update(todo)
    }

    fun deleteTodo(id: Int) {
        todoList.deleteTodo(id).getOrThrow()

        todoRepository.delete(id)
    }

//    fun loadFromDatabase() {
//        val todos = todoRepository.findAll(session.userId)
//        todoList.clearAndAddAll(todos)
//    }

}