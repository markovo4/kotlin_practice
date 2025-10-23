package todolist.todolist.application.auth

import com.google.gson.Gson
import todolist.application.auth.UserSession
import todolist.todolist.database.RedisFactory
import java.util.UUID

object SessionServices {
    private val redis = RedisFactory.commands
    private val gson = Gson()

    fun createSession(userId: Int, email: String, ttlSeconds: Long = 3600): String {
        val sessionId = UUID.randomUUID().toString()
        val key = "session:$sessionId"

        val gsonData = gson.toJson(UserSession(userId, email))
        redis.setex(key, ttlSeconds, gsonData)
        return sessionId
    }

    fun getSession(sessionId: String): UserSession? {
        val key = "session:$sessionId"
        val json = redis.get(key) ?: return null
        return gson.fromJson(json, UserSession::class.java)
    }

    fun deleteSession(sessionId: String){
        redis.del("session:$sessionId")
    }
}


