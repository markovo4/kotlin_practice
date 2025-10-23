package todolist.todolist.application.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import todolist.application.auth.UserSession

suspend fun ApplicationCall.withSession(block: suspend (UserSession) -> Unit) {
    val sessionId = request.cookies["SESSION_ID"]
    if(sessionId == null){
        respond(HttpStatusCode.Unauthorized, "No Session Found")
        return
    }

    val session = SessionServices.getSession(sessionId)
    if (session == null) {
        respond(HttpStatusCode.Unauthorized, "Invalid or expired session")
        return
    }

    block(session)
}