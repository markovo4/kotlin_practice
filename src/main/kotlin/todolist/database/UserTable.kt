package todolist.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()
    var id: Int
    var userName: String
    var email: String
    var password: String
}

object Users: Table<UserEntity>("users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val userName = varchar("username").bindTo { it.userName }
    val email = varchar("email").bindTo { it.email }
    val password = varchar("password").bindTo { it.password }
}