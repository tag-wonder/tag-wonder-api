package org.tagwonder.entities

data class Tag(
    val id: Long,
    val contents: String,
    val memberId: Long
)
