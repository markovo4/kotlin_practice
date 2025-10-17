package todolist.utils

import org.mindrot.jbcrypt.BCrypt


object PasswordHasher {

    fun hash(password: String): String {
        val salt = BCrypt.gensalt(12)
        return BCrypt.hashpw(password, salt)
    }

    fun verify(password: String, hashed: String): Boolean {
        return BCrypt.checkpw(password, hashed)
    }

}