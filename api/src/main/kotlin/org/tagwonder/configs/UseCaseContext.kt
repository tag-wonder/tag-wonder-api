package org.tagwonder.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.jwt.AuthTokenGenerator
import org.tagwonder.repositories.IMemberRepository
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor
import org.tagwonder.usecases.commands.SignInOAuthCommandExecutor
import org.tagwonder.usecases.queries.GetOAuthInfoQueryProcessor
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
        tagRepository: ITagRepository
    ): GetOAuthInfoQueryProcessor {
        return GetOAuthInfoQueryProcessor(mapOf())
    }
}
