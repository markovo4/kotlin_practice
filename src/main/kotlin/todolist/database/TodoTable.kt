package todolist.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface TodoEntity : Entity<TodoEntity> {
    companion object : Entity.Factory<TodoEntity>()
        var id: Int
        var content: String
        var status: String
        var user: UserEntity
}

object Todos: Table<TodoEntity>("todos") {
    val id = int("id").primaryKey().bindTo { it.id }
    val content = varchar("content").bindTo { it.content }
    val status = varchar("status").bindTo { it.status }
    val userId = int("user_id").references(Users) { it.user }
}