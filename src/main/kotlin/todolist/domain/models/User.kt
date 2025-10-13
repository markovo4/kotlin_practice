package todolist.domain.models;

public class User (
    email: String,
    username: String,
    private var passwordHash: String,
    id: Int = nextId()
){
    var email: String = email
        private set;

    var username: String = username
        private set;

    fun changePassword(newPasswordHash: String){
        passwordHash = newPasswordHash
    }
    var id: Int = id
        private set

    companion object{
        private var lastId = 0;

        private fun nextId(): Int{
            lastId++
            return lastId
        }
    }
}
