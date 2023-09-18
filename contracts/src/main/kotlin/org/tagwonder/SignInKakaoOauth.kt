package org.tagwonder

data class SignInKakaoOauth(
    val authorizationCode: String
): ISignInOAuth {

    override fun oAuthProvider(): OAuthProvider {
        return OAuthProvider.KAKAO
    }

    override fun makeBody(): Map<String, String> {
        val body = LinkedHashMap<String, String>()
        body["code"] = authorizationCode
        return body
    }
}
