package todolist.application.auth

data class UserSession(
    val userId: Int,
    val email: String,
    val createdAt: Long = System.currentTimeMillis()
)