package org.tagwonder.repositories

import org.tagwonder.entities.Member

interface IMemberRepository {
    fun create(member: Member): Member
    fun findByEmail(email: String): Member?
}
