package todolist

import todolist.database.DatabaseFactory
import todolist.presentation.cli.TodoCLI

fun main() {
    DatabaseFactory.testConnection()
//    val todoCLI = TodoCLI()
//    todoCLI.start()
}