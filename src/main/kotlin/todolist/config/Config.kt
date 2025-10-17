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
//    val appPort = env["APP_PORT"] ?: "8080"
    val secretKey = env["JWT_SECRET"] ?: ""
}