package org.tagwonder.controllers

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.tagwonder.commands.CreateTagsApiCommand
import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.exceptions.InvalidCommandException
import org.tagwonder.exceptions.toHttpException
import org.tagwonder.jwt.AuthTokenGenerator
import org.tagwonder.queries.GetTagSummariesQuery
import org.tagwonder.queries.GetTagSummariesQueryResponse
import org.tagwonder.queries.GetTagsQuery
import org.tagwonder.queries.GetTagsQueryResponse
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor
import org.tagwonder.usecases.queries.GetTagSummariesQueryProcessor
import org.tagwonder.usecases.queries.GetTagsQueryProcessor

@RestController
class TagController(
    private val createTagsCommandExecutor: CreateTagsCommandExecutor,
    private val getTagsQueryProcessor: GetTagsQueryProcessor,
    private val getTagSummariesQueryProcessor: GetTagSummariesQueryProcessor,
    private val authTokenGenerator: AuthTokenGenerator
) {

    @Operation(summary = "태그 복수 생성")
    @PostMapping("/tags")
    fun createTags(
        @RequestHeader(value = "Authorization", required = true) authToken: String,
        @RequestBody command: CreateTagsApiCommand
    ) {
        try {
            createTagsCommandExecutor.execute(
                CreateTagsCommand(
                    memberId = authTokenGenerator.extractMemberId(authToken),
                    titles = command.titles,
                    writer = command.writer
                )
            )
        } catch (e: InvalidCommandException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(summary = "태그 전체 조회")
    @GetMapping("/tags")
    fun getTags(
        @RequestHeader(value = "Authorization", required = true) authToken: String
    ): GetTagsQueryResponse {
        return getTagsQueryProcessor.process(
            GetTagsQuery(
                memberId = authTokenGenerator.extractMemberId(authToken)
            )
        )
    }

    @Operation(summary = "태그 결과 조회")
    @GetMapping("/tag-summaries")
    fun getTagSummaries(
        @RequestHeader(value = "Authorization", required = true) authToken: String
    ): GetTagSummariesQueryResponse {
        return getTagSummariesQueryProcessor.process(
            GetTagSummariesQuery(
                memberId = authTokenGenerator.extractMemberId(authToken)
            )
        )
    }
}
