package todolist.application.auth

import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import todolist.database.DatabaseFactory
import todolist.database.UserEntity
import todolist.database.Users
import todolist.todolist.application.auth.SessionServices
import todolist.utils.PasswordHasher

object AuthService {
    private val db = DatabaseFactory.db

    fun register(username: String, email: String, password: String): Boolean {
        val exists = db.from(Users)
            .select()
            .where { Users.email eq email }
            .totalRecordsInAllPages > 0

        if(exists) return false

        val user = UserEntity {
            this.userName = username
            this.email = email
            this.password = PasswordHasher.hash(password)
        }

        db.sequenceOf(Users).add(user)

        return true
    }

    fun login(email: String, password: String): String? {
        val user = db.sequenceOf(Users).find { it.email eq email } ?: return null

        if (!PasswordHasher.verify(password, user.password)) return null

        return SessionServices.createSession(user.id, user.email)
    }
}