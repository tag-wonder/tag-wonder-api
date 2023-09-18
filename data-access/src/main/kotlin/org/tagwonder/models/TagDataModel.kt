package org.tagwonder.models

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Table(name = "tags")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
data class TagDataModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val contents: String = "",

    @Column(name = "member_id")
    val memberId: Long = 0L
)
