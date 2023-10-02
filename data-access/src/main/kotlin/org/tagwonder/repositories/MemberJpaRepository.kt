package org.tagwonder.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.tagwonder.entities.Member
import org.tagwonder.models.MemberDataModel

interface MemberJpaRepository: JpaRepository<MemberDataModel, Long> {
    fun findByEmail(email: String): Member?
}
