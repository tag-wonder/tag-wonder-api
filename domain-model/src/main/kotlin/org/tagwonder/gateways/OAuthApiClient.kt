package org.tagwonder.gateways

import org.tagwonder.oAuth.IOAuthInfoResponse
import org.tagwonder.oAuth.ISignInOAuth
import org.tagwonder.oAuth.OAuthProvider

interface OAuthApiClient {
    fun oAuthProvider(): OAuthProvider
    fun requestAccessToken(request: ISignInOAuth): String
    fun requestOauthInfo(accessToken: String): IOAuthInfoResponse
}
