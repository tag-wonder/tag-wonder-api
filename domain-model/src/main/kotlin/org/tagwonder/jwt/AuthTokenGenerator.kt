package org.tagwonder.jwt

import org.tagwonder.oAuth.AuthToken
import java.util.*

class AuthTokenGenerator(
    private val jwtTokenProvider: JwtTokenProvider
) {
    private val BEARER_TYPE = "Bearer"
    private val ACCESS_TOKEN_EXPIRE_TIME = (1000 * 60 * 30).toLong()  // 30분
    private val REFRESH_TOKEN_EXPIRE_TIME = (1000 * 60 * 60 * 24 * 7).toLong() // 7일

    fun generate(memberId: Long): AuthToken {
        val now: Long = Date().time
        val accessTokenExpiredAt = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val refreshTokenExpiredAt = Date(now + REFRESH_TOKEN_EXPIRE_TIME)

        val subject: String = memberId.toString()
        val accessToken: String = jwtTokenProvider.generate(subject, accessTokenExpiredAt)
        val refreshToken: String = jwtTokenProvider.generate(subject, refreshTokenExpiredAt)

        return AuthToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
            grantType = BEARER_TYPE,
            expiresIn = ACCESS_TOKEN_EXPIRE_TIME / 1000L
        )
    }

    fun extractMemberId(accessToken: String?): Long {
        return jwtTokenProvider.extractSubject(accessToken!!).toLong()
    }
}
