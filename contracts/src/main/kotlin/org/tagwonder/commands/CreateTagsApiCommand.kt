package org.tagwonder.commands

data class CreateTagsApiCommand(
    val titles: List<String>,
    val writer: String
) {
    constructor() : this(emptyList(), "")
}
