package org.tagwonder.oAuth.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.tagwonder.oAuth.IOAuthInfoResponse
import org.tagwonder.oAuth.OAuthProvider

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoInfoResponse(
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount

): IOAuthInfoResponse {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class KakaoAccount(
        val profile: KakaoProfile,
        val email: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class KakaoProfile(
        val nickname: String
    )

    override fun getEmail(): String {
        return kakaoAccount.email;
    }

    override fun getNickname(): String {
        return kakaoAccount.profile.nickname
    }

    override fun getOAuthProvider(): OAuthProvider {
        return OAuthProvider.KAKAO
    }
}
