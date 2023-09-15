package org.tagwonder

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.repositories.TagJpaRepository
import org.tagwonder.repositories.TagRepositoryImpl

@TestConfiguration
open class TestRepositoryContext {
    @Bean
    open fun tagRepository(
        database: TagJpaRepository
    ): ITagRepository {
        return TagRepositoryImpl(database)
    }
}
