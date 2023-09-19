package org.tagwonder.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.repositories.TagJpaRepository
import org.tagwonder.repositories.TagRepositoryImpl

@Configuration
open class RepositoryContext {
    @Bean
    open fun tagRepository(
        tagJpaRepository: TagJpaRepository
    ): ITagRepository {
        return TagRepositoryImpl(tagJpaRepository)
    }
}
