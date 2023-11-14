package org.tagwonder.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.KakaoApiClient
import org.tagwonder.jwt.AuthTokenGenerator
import org.tagwonder.oAuth.OAuthProvider
import org.tagwonder.repositories.IMemberRepository
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor
import org.tagwonder.usecases.commands.SignInOAuthCommandExecutor
import org.tagwonder.usecases.queries.GetOAuthInfoQueryProcessor
import org.tagwonder.usecases.queries.GetTagSummariesQueryProcessor
import org.tagwonder.usecases.queries.GetTagsQueryProcessor

@Configuration
open class UseCaseContext {

    @Bean
    open fun createTagsCommandExecutor(
        tagRepository: ITagRepository
    ): CreateTagsCommandExecutor {
        return CreateTagsCommandExecutor(tagRepository)
    }

    @Bean
    open fun getTagsQueryProcessor(
        tagRepository: ITagRepository
    ): GetTagsQueryProcessor {
        return GetTagsQueryProcessor(tagRepository)
    }

    @Bean
    open fun signInOAuthCommandExecutor(
        memberRepository: IMemberRepository,
        authTokensGenerator: AuthTokenGenerator,
        getOAuthInfoQueryProcessor: GetOAuthInfoQueryProcessor
    ): SignInOAuthCommandExecutor {
        return SignInOAuthCommandExecutor(
            memberRepository,
            authTokensGenerator,
            getOAuthInfoQueryProcessor
        )
    }

    //TODO(refactor): processor에서 제거하기
    @Bean
    open fun getOAuthInfoQueryProcessor(
        tagRepository: ITagRepository,
        @Value("\${oauth.kakao.url.protocol}") protocol: String,
        @Value("\${oauth.kakao.url.auth-host}") authHost: String,
        @Value("\${oauth.kakao.url.api-host}") apiHost: String,
        @Value("\${oauth.kakao.client-id}") clientId: String,
        @Value("\${oauth.kakao.authorization-grant-type}") grantType: String,
    ): GetOAuthInfoQueryProcessor {
        return GetOAuthInfoQueryProcessor(
            mapOf(
                OAuthProvider.KAKAO to KakaoApiClient(protocol, authHost, apiHost, clientId, grantType)
            )
        )
    }

    @Bean
    open fun getTagSummariesQueryProcessor(
        tagRepository: ITagRepository
    ): GetTagSummariesQueryProcessor {
        return GetTagSummariesQueryProcessor(tagRepository)
    }
}
