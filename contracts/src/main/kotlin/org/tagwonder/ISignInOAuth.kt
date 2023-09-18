package org.tagwonder


interface ISignInOAuth {
    fun oAuthProvider(): OAuthProvider
    fun makeBody(): Map<String, String>
}
