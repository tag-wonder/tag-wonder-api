package org.tagwonder.usecases.queries

import org.tagwonder.gateways.OAuthApiClient
import org.tagwonder.oAuth.IOAuthInfoResponse
import org.tagwonder.oAuth.ISignInOAuth
import org.tagwonder.oAuth.OAuthProvider

class GetOAuthInfoQueryProcessor(
    private val clients: Map<OAuthProvider, OAuthApiClient>
) {

    fun process(query: ISignInOAuth): IOAuthInfoResponse {
        val client = clients[query.oAuthProvider()]!!
        val accessToken = client.requestAccessToken(query)
        return client.requestOauthInfo(accessToken)
    }
}
