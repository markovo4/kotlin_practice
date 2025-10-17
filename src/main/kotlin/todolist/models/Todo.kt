package todolist.models

import org.ktorm.dsl.QueryRowSet
import todolist.database.Todos

data class Todo(
    var content: String,
    var userId: Int? = null,
    var status: NoteStatus = NoteStatus.TODO,
    val id: Int? = null
) {
    fun edit(newTodo: String) {
        content = newTodo
    }

    fun changeStatus(newStatus: NoteStatus) {
        status = newStatus
    }

    companion object {
        fun fromRow(row: QueryRowSet): Todo {
            return Todo(
                id = row[Todos.id] ?: 0,
                content = row[Todos.content].orEmpty(),
                status = NoteStatus.valueOf(row[Todos.status].orEmpty()),
                userId = row[Todos.userId] ?: 0
            )
        }
    }
}