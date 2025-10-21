package todolist.todolist.database

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import todolist.todolist.config.Config

object RedisFactory {
    private val client: RedisClient = RedisClient.create(Config.Redis.url)
    private val connection: StatefulRedisConnection<String, String> = client.connect()
    val commands: RedisCommands<String, String> = connection.sync()

    fun close() {
        connection.close()
        client.shutdown()
    }
}