package org.tagwonder.usecases.commands

import com.healingpaper.solution.domain.exceptions.InvalidCommandException
import com.healingpaper.solution.domain.exceptions.InvalidRequestException
import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.entities.Tag
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.utils.hasDuplicate

class CreateTagsCommandExecutor(
    private val tagRepository: ITagRepository
) {
    fun execute(command: CreateTagsCommand) {
        validCommand(command)
        existsTitleInTag(command)
        tagRepository.creates(
            command.titles.map {
                Tag(
                    title = it,
                    memberId = command.memberId
                )
            }
        )
    }

    private fun existsTitleInTag(command: CreateTagsCommand) {
        command.titles.forEach {
            tagRepository.findByTitle(it)?.let {
                throw InvalidRequestException("title already exists")
            }
        }
    }

    private fun validCommand(command: CreateTagsCommand) {
        if (command.titles.any { it.length > 15}) {
            throw InvalidCommandException("title is too long")
        }

        if(command.titles.hasDuplicate()) {
            throw InvalidCommandException("title is duplicated")
        }
    }
}
