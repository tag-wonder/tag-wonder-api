package com.healingpaper.solution.domain.exceptions

data class InvalidCommandException(
    override val message: String,
    val className: String = "InvalidCommandException"
) : Error()
