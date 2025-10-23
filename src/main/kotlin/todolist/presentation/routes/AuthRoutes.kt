package todolist.todolist.presentation.routes

import io.ktor.http.Cookie
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import todolist.application.auth.AuthService
import todolist.todolist.application.auth.SessionServices

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val userName: String, val email: String, val password: String)

fun Route.authRoutes() {
    post("/register") {
        val body = call.receive<RegisterRequest>()
        val created = AuthService.register(body.userName, body.email, body.password)

        if(!created){
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return@post
        }

        call.respond(HttpStatusCode.Created, "User registered successfully")
    }


    post("/login") {
        val body = call.receive<LoginRequest>()
        val sessionId = AuthService.login(body.email, body.password)

        if(sessionId == null){
            call.respond(HttpStatusCode.NotAcceptable, "Invalid credentials")
            return@post
        }

        println("\n\n\n\n\nSession ID: $sessionId\n\n\n\n\n")

        call.response.cookies.append(
            Cookie(
                name = "SESSION_ID",
                value = sessionId,
                path = "/",
                httpOnly = true,
                secure = false,
                maxAge = 3600
            )
        )

        call.respond(HttpStatusCode.OK, "Logged in successfully" )
    }


    post("/logout") {
        val sessionId = call.request.cookies["SESSION_ID"]
        if (sessionId != null) {
            SessionServices.deleteSession(sessionId)
            call.sessions.clear("SESSION_ID")
        }
        call.respondText("Logged out")
        }
}