package todolist.repository

import todolist.models.Todo

interface TodoRepository {
    fun add(todo: Todo, userId: Int): Result<Todo>
    fun update(todo: Todo): Result<Int>
    fun delete(id: Int): Result<Unit>
    fun findById(id: Int): Todo?
    fun findAll(userId: Int): List<Todo>
}