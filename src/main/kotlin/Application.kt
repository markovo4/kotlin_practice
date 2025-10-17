package todolist

import todolist.database.DatabaseFactory
import todolist.presentation.cli.TodoCLI
import java.util.logging.LogManager

fun main() {

    LogManager.getLogManager().reset()

    if(!DatabaseFactory.testConnection()){
        println("Database not connected. Exiting...\n\n")
        return
    }

    println("Database connected. Starting TodoList...\n\n")
    val todoCLI = TodoCLI()
    todoCLI.start()
}