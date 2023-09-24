package org.tagwonder.controllers

import com.healingpaper.solution.domain.exceptions.InvalidCommandException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.exceptions.toHttpException
import org.tagwonder.queries.GetTagsQuery
import org.tagwonder.queries.GetTagsQueryResponse
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor
import org.tagwonder.usecases.queries.GetTagsQueryProcessor

@RestController
class TagController(
    private val createTagsCommandExecutor: CreateTagsCommandExecutor,
    private val getTagsQueryProcessor: GetTagsQueryProcessor
) {
    @PostMapping("/tags")
    fun createTags(
        @RequestBody command: CreateTagsCommand
    ) {
        try {
            createTagsCommandExecutor.execute(
                command = command
            )
        } catch (e: InvalidCommandException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/members/{memberId}/tags")
    fun getTags(
        @PathVariable("memberId") memberId: Long
    ): GetTagsQueryResponse{
        return getTagsQueryProcessor.process(
            GetTagsQuery(
                memberId = memberId
            )
        )
    }
}
