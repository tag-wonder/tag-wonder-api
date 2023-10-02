package org.tagwonder.oAuth

interface IOAuthInfoResponse {
    fun getEmail(): String
    fun getNickname(): String
    fun getOAuthProvider(): OAuthProvider
}
