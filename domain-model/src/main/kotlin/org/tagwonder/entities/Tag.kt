package org.tagwonder.entities

data class Tag(
    val id: Long? = null,
    val contents: String,
    val memberId: Long
)
