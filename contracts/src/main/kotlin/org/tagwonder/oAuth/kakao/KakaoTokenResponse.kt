package org.tagwonder.oAuth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenResponse(
    @JsonProperty("access_token")
    val accessToken : String,

    @JsonProperty("token_type")
    val tokenType: String,

    @JsonProperty("refresh_token")
    val refreshToken: String,

    @JsonProperty("expires_in")
    val expiresIn: String,

    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: String,

    @JsonProperty("scope")
    val scope : String
)
