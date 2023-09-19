package org.tagwonder.controllers

import com.healingpaper.solution.domain.exceptions.InvalidCommandException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.exceptions.toHttpException
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor

@RestController
class TagController(
    private val createTagsCommandExecutor: CreateTagsCommandExecutor
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
}
