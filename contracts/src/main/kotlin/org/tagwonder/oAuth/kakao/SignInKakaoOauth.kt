package org.tagwonder.oAuth.kakao

import org.tagwonder.oAuth.ISignInOAuth
import org.tagwonder.oAuth.OAuthProvider

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
