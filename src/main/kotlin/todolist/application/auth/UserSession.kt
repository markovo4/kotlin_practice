package todolist.application.auth

data class UserSession(
    val userId: Int,
    val email: String,
)