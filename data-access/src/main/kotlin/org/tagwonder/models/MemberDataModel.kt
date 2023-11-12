package org.tagwonder.models

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.tagwonder.oAuth.OAuthProvider

@Table(name = "members")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
data class MemberDataModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val email: String = "",

    val nickname: String = "",

    @Enumerated(EnumType.STRING)
    val oAuthProvider: OAuthProvider = OAuthProvider.UNSPECIFIED
)
