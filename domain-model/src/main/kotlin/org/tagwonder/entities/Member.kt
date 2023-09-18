package org.tagwonder.entities

import org.tagwonder.OAuthProvider

data class Member(
    val id: Long,
    val email: String,
    val nickname: String,
    val oAuthProvider: OAuthProvider
)
