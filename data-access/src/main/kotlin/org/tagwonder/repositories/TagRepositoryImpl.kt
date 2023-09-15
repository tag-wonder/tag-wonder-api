package org.tagwonder.repositories

import org.tagwonder.entities.Tag
import org.tagwonder.mappers.TagDataMapper

class TagRepositoryImpl(
    private val database: TagJpaRepository
) : ITagRepository {
    private val mapper: TagDataMapper = TagDataMapper()

    override fun getList(memberId: Long): List<Tag> {
        return database.findAll()
            .map { mapper.toEntity(it) }
    }

    override fun creates(tags: List<Tag>) {
        database.saveAll(tags.map { tag -> mapper.toDataModel(tag) })
    }
}
