package todolist.database

import org.ktorm.database.Database
import todolist.todolist.config.Config

object DatabaseFactory {
    val db: Database by lazy {
        Database.connect(
            url = Config.dbUrl,
            driver = Config.dbDriver,
            user = Config.dbUser,
            password = Config.dbPassword
        )
    }

    fun init(): Boolean {
        return try {
            db.useConnection { connection ->
                println("Connected to MySQL: ${connection.metaData.url}")
                println("   DB version: ${connection.metaData.databaseProductVersion}")
                true
            }
        }catch (e: Exception) {
            println("Failed to connect: ${e.message}")
            false
        }
    }
}