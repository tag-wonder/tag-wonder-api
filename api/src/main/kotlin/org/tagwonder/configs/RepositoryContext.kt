package org.tagwonder.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.repositories.*

@Configuration
open class RepositoryContext {
    @Bean
    open fun tagRepository(
        tagJpaRepository: TagJpaRepository
    ): ITagRepository {
        return TagRepositoryImpl(tagJpaRepository)
    }

    @Bean
    open fun memberRepository(
        memberJpaRepository: MemberJpaRepository
    ): IMemberRepository {
        return MemberRepositoryImpl(memberJpaRepository)
    }
}
