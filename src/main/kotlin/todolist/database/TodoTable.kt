package todolist.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import todolist.models.NoteStatus
import todolist.utils.enumColumn

interface TodoEntity : Entity<TodoEntity> {
    companion object : Entity.Factory<TodoEntity>()
        var id: Int
        var content: String
        var status: NoteStatus
}

object Todos: Table<TodoEntity>("todos") {
    val id = int("id").primaryKey().bindTo { it.id }
    val content = varchar("content").bindTo { it.content }
    val status = enumColumn<NoteStatus>("status").bindTo { it.status }
}