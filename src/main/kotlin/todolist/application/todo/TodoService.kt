package todolist.application.todo

import todolist.application.auth.UserSession
import todolist.database.DatabaseFactory
import todolist.models.*
import todolist.models.exceptions.TodoNotFoundException
import todolist.repository.DbTodoRepository

class TodoService (
    private val session: UserSession,
    private val todoRepository: DbTodoRepository = DbTodoRepository(DatabaseFactory.db),
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
        val newTodo = Todo(content = todoContent)
        val result = todoRepository.add(newTodo, session.userId)
        val savedTodo = result.getOrThrow()

        if(savedTodo.id == null) return

        val localResult = todoList.addTodo(todoContent, session.userId, savedTodo.id)
        localResult.getOrThrow()

    }

    fun editTodo(id: Int, newContent: String): Todo {
        todoList.editTodoText(id, newContent).getOrThrow()

        val todo = todoRepository.findById(id) ?: throw TodoNotFoundException(id)
        todo.content = newContent
        todoRepository.update(todo)

        return todo
    }

    fun editTodoStatus(id: Int, newStatus: NoteStatus): Todo {
        todoList.editTodoStatus(id, newStatus).getOrThrow()

        val todo = todoRepository.findById(id) ?: throw TodoNotFoundException(id)
        todo.status = newStatus
        todoRepository.update(todo)

        return todo
    }

    fun deleteTodo(id: Int) {
        todoList.deleteTodo(id).getOrThrow()

        todoRepository.delete(id)
    }

    fun getTodoById(id: Int): Todo {
        return todoRepository.findById(id) ?: throw TodoNotFoundException(id)
    }

}