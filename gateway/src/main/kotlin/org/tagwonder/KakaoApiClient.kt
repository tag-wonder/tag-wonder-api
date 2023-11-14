package org.tagwonder

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import org.tagwonder.gateways.OAuthApiClient
import org.tagwonder.oAuth.IOAuthInfoResponse
import org.tagwonder.oAuth.ISignInOAuth
import org.tagwonder.oAuth.OAuthProvider
import org.tagwonder.oAuth.kakao.KakaoInfoResponse
import org.tagwonder.oAuth.kakao.KakaoTokenResponse
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

data class KakaoApiClient(
    private val protocol: String,
    private val authHost: String,
    private val apiHost: String,
    private val clientId: String,
    private val grantType: String
): OAuthApiClient {
    private val webClient: WebClient

    init {
        this.webClient = WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(
                HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .responseTimeout(Duration.ofMillis(5000))
                    .doOnConnected {
                        it
                            .addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    }
            ))
            .baseUrl(makeBaseUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build()
    }

    private fun makeBaseUrl(): String {
        return UriComponentsBuilder.newInstance()
            .scheme(protocol)
            .build()
            .toUriString()
    }

    override fun oAuthProvider(): OAuthProvider {
        return OAuthProvider.KAKAO
    }

    override fun requestAccessToken(request: ISignInOAuth): String {
        return webClient.post()
            .uri("$authHost/oauth/token")
            .bodyValue(
                request.makeBody() + mapOf(
                    "grant_type" to grantType,
                    "client_id" to clientId
                )
            )
            .retrieve()
            .bodyToMono(KakaoTokenResponse::class.java)
            .retry(3)
            .block()
            ?.accessToken
            ?.let { it }
            ?: throw Error("request error with access token")
    }

    override fun requestOauthInfo(accessToken: String): IOAuthInfoResponse {
        return webClient.post()
            .uri("$apiHost/v2/user/me")
            .headers { headers ->  headers.setBearerAuth(accessToken) }
            .bodyValue(
                mapOf(
                    "property_keys" to "[\"kakao_account.email\", \"kakao_account.profile\"]"
                )
            )
            .retrieve()
            .bodyToMono(KakaoInfoResponse::class.java)
            .retry(3)
            .block()
            ?: throw Error("request error with oauth info")
    }
}

