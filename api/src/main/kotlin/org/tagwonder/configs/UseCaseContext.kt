package org.tagwonder.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor
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
}
