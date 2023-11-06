package org.tagwonder.usecases.commands

import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.entities.Tag
import org.tagwonder.exceptions.InvalidCommandException
import org.tagwonder.exceptions.InvalidRequestException
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.utils.hasDuplicate

class CreateTagsCommandExecutor(
    private val tagRepository: ITagRepository
) {
    fun execute(memberId: Long, command: CreateTagsCommand) {
        validCommand(command)
        existsTitleInTag(memberId, command.titles)
        tagRepository.creates(
            command.titles.map {
                Tag(
                    title = it,
                    memberId = memberId
                )
            }
        )
    }

    private fun existsTitleInTag(memberId: Long, titles: List<String>) {
        titles.forEach {
            tagRepository.find(memberId, it)?.let {
                throw InvalidRequestException("title already exists")
            }
        }
    }

    private fun validCommand(command: CreateTagsCommand) {
        if(command.titles.size > 3) {
            throw InvalidCommandException("array of titles is too long")
        }

        if (command.titles.any { it.length > 15}) {
            throw InvalidCommandException("length of title is too long")
        }

        if(command.titles.hasDuplicate()) {
            throw InvalidCommandException("element of title is duplicated")
        }
    }
}
