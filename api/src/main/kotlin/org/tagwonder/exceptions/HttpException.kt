package org.tagwonder.exceptions

data class HttpException(
    val status: Int,
    override val message: String,
    val className: String
) : Error()
