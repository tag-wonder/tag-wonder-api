package org.tagwonder.repositories

import org.tagwonder.entities.Tag
import org.tagwonder.mappers.TagDataMapper

class TagRepositoryImpl(
    private val database: TagJpaRepository,
    private val mapper: TagDataMapper
) : ITagRepository {
    override fun getList(memberId: Long): List<Tag> {
        return database.findAll()
            .map { mapper.toEntity(it) }
    }

    override fun creates(tags: List<Tag>) {
        database.saveAll(tags.map { tag -> mapper.toDataModel(tag) })
    }
}
