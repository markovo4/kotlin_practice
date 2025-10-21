package todolist.todolist.config

import io.github.cdimascio.dotenv.dotenv

object Config {
    private val env = dotenv {
        ignoreIfMissing = true
    }

    val dbUrl = env["DB_URL"] ?: "jdbc:mysql://localhost:3306/todo_app"
    val dbDriver = env["DB_DRIVER"] ?: "com.mysql.cj.jdbc.Driver"
    val dbUser = env["DB_USER"] ?: "root"
    val dbPassword = env["DB_PASSWORD"] ?: ""
    val appPort: Int = (env["APP_PORT"] ?: "8080").toInt()
    val secretKey = env["JWT_SECRET"] ?: ""

    object Redis {
        val host = env["REDIS_HOST"] ?: "localhost"
        val port = (env["REDIS_PORT"] ?: "6379").toInt()
        val database = (env["REDIS_DB"] ?: "0").toInt()
        val password = env["REDIS_PASSWORD"] ?: ""

        val url: String
            get() = if (password.isNotBlank())
                "redis://:$password@$host:$port/$database"
            else
                "redis://$host:$port/$database"
    }
}