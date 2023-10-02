package org.tagwonder.repositories

import org.tagwonder.entities.Member
import org.tagwonder.mappers.MemberDataMapper

class MemberRepositoryImpl(
    private val database: MemberJpaRepository
) : IMemberRepository {
    private val mapper: MemberDataMapper = MemberDataMapper()

    override fun create(member: Member): Member {
        return mapper.toEntity(
            database.save(mapper.toDataModel(member))
        )
    }

    override fun findByEmail(email: String): Member? {
        return database.findByEmail(email)
    }
}
