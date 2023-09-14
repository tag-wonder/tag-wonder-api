package org.tagwonder.entities

import lombok.Getter

data class Tag(
    private val id: Long,
    private val contents: String,
    private val memberId: Long
)
