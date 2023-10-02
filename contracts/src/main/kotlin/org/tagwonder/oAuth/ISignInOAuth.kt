package org.tagwonder.oAuth


interface ISignInOAuth {
    fun oAuthProvider(): OAuthProvider
    fun makeBody(): Map<String, String>
}
