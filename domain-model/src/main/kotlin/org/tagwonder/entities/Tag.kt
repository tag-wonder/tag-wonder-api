package org.tagwonder.entities

data class Tag(
    val id: Long? = null,
    val title: String,
    val memberId: Long,
    val writer: String? = null
)
