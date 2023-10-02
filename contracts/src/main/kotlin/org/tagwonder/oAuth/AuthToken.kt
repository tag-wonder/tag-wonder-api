package org.tagwonder.oAuth

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val grantType: String,
    val expiresIn: Long
)
