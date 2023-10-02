package org.tagwonder.entities

import org.tagwonder.oAuth.OAuthProvider

data class Member(
    val id: Long,
    val email: String,
    val nickname: String,
    val oAuthProvider: OAuthProvider
)
