package todolist.application.auth

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import todolist.database.DatabaseFactory
import todolist.database.UserEntity
import todolist.database.Users
import todolist.utils.JwtConfig
import todolist.utils.PasswordHasher

object AuthService {
    private val db = DatabaseFactory.db

    fun register(username: String, email: String, password: String): Boolean {
        val exists = db.sequenceOf(Users).find { it.email eq email } != null

        if(exists) return false

        val user = UserEntity {
            this.userName = username
            this.email = email
            this.password = PasswordHasher.hash(password)
        }

        db.sequenceOf(Users).add(user)
        return true
    }

    fun login(email: String, password: String): UserSession? {
        val user = db.sequenceOf(Users).find { it.email eq email } ?: return null

        return if (PasswordHasher.verify(password, user.password)){
            JwtConfig.makeToken(email)
            return UserSession(userId = user.id, email = user.email)
        } else {
            null
        }
    }
}