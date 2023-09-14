package org.tagwonder.models

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.tagwonder.entities.Tag

@Table(name = "tags")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
data class TagDataModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    private val contents: String = "",

    @Column(name = "member_id")
    private val memberId: Long = 0L
) {
    fun toEntity(): Tag {
        return Tag(
          id = this.id!!,
          contents = this.contents,
          memberId = this.memberId
        )
    }
}
