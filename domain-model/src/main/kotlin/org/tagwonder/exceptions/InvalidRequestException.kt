package com.healingpaper.solution.domain.exceptions

data class InvalidRequestException(
    override val message: String,
    val className: String = "InvalidRequestException"
) : Error()
