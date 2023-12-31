package org.tagwonder.repositories

import org.tagwonder.entities.Tag
import org.tagwonder.mappers.TagDataMapper

class TagRepositoryImpl(
    private val database: TagJpaRepository
) : ITagRepository {
    private val mapper: TagDataMapper = TagDataMapper()

    override fun getList(memberId: Long): List<Tag> {
        return database.findAllByMemberId(memberId)
            .map { mapper.toEntity(it) }
    }

    override fun creates(tags: List<Tag>) {
        database.saveAll(tags.map { tag -> mapper.toDataModel(tag) })
    }

    override fun find(
        memberId: Long,
        title: String
    ): Tag? {
        return database.findByMemberIdAndTitle(
            memberId,
            title
        )?.let { mapper.toEntity(it) }
    }

    override fun deleteAll() {
        database.deleteAll()
    }
}
