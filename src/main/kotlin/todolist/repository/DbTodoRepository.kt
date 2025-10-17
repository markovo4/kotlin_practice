package todolist.repository

import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import todolist.database.Todos
import todolist.models.Todo
import todolist.models.exceptions.TodoNotFoundException

class DbTodoRepository (private val db: Database): TodoRepository {

    override fun add(todo: Todo, userId: Int): Result<Todo> = runCatching {
        val id = db.insertAndGenerateKey(Todos) {
            set(it.content, todo.content)
            set(it.status, todo.status.toString())
            set(it.userId, userId)
        } as Int

        todo.copy(id = id, userId = userId)
    }

    override fun delete(id: Int): Result<Unit> {
        val result = db.delete(Todos) { it.id eq id}

        if(result == 0) return Result.failure(TodoNotFoundException(id))

        return Result.success(Unit)
    }

    override fun findAll(userId: Int): List<Todo> {
        return db.from(Todos)
            .select()
            .where { Todos.userId eq userId }
            .map { Todo.fromRow(it) }
    }

    override fun findById(id: Int): Todo? {
        return db.from(Todos)
            .select()
            .where { Todos.id eq id }
            .map { Todo.fromRow(it) }
            .firstOrNull()
    }

    override fun update(todo: Todo) = runCatching {
        db.update(Todos) {
            set(it.content, todo.content)
            set(it.status, todo.status.toString())
            where { it.id eq (todo.id ?: error("Todo ID must not be null")) }
        }
    }

}

