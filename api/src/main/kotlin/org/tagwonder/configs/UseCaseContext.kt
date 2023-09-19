package org.tagwonder.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor

@Configuration
open class UseCaseContext {

    @Bean
    open fun createTagsCommandExecutor(
        tagRepository: ITagRepository
    ): CreateTagsCommandExecutor {
        return CreateTagsCommandExecutor(tagRepository)
    }
}
