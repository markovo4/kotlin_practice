package todolist

import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import todolist.database.DatabaseFactory
import todolist.presentation.cli.TodoCLI
import todolist.todolist.config.Config
import todolist.todolist.database.RedisFactory
import todolist.todolist.presentation.routes.todoRoutes
import java.util.logging.LogManager

fun main() {

    embeddedServer(
        factory = Netty,
        port = Config.appPort,
        module = Application::module
    ).start(wait = true)

    LogManager.getLogManager().reset()

    if(!DatabaseFactory.init()) return println("Database not connected. Exiting...\n\n")

    val todoCLI = TodoCLI()
    todoCLI.start()

    try {
        RedisFactory.commands // заставляем инициализироваться
        println("Redis connected successfully.")
    } catch (e: Exception) {
        println("Redis connection failed: ${e.message}")
    }
}



fun Application.module() {
    install(CallLogging)

    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }

    val redis = RedisFactory.commands
    environment.monitor.subscribe(ApplicationStopping){
        RedisFactory.close()
    }

    routing { todoRoutes() }
}

