package todolist.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import todolist.todolist.config.Config
import java.util.*

object JwtConfig {
    private const val ISSUER = "todolist-app"
    private const val VALIDITY_IN_MS = 36_000_00 * 2

    private val algorithm = Algorithm.HMAC512(Config.secretKey)

    fun makeToken(email: String): String =
        JWT.create()
            .withIssuer(ISSUER)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_IN_MS))
            .sign(algorithm)


    fun verify() = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()
}