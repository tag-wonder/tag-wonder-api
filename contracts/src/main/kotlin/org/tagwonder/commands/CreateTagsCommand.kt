package org.tagwonder.commands

data class CreateTagsCommand(
    val titles: List<String>,
    val memberId: Long
)
