package org.tagwonder.queries

data class TagSummaryContract(
    val title: String,
    val writtenCount: Int,
    val writers: List<String>
)
