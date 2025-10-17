package todolist.models

class User (
    email: String,
    username: String,
    private var passwordHash: String,
){
    var email: String = email
        private set

    var username: String = username
        private set

    fun changePassword(newPasswordHash: String) {
        passwordHash = newPasswordHash
    }
}
