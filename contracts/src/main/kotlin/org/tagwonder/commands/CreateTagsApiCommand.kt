package org.tagwonder.commands

data class CreateTagsApiCommand(
    val titles: List<String>,
) {
    constructor() : this(emptyList())
}
